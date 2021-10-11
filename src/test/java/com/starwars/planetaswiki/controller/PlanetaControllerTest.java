package com.starwars.planetaswiki.controller;

import com.starwars.planetaswiki.model.Planeta;
import com.starwars.planetaswiki.model.Response;
import com.starwars.planetaswiki.service.PlanetaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class PlanetaControllerTest {

    @Spy
    @InjectMocks
    private PlanetaController planetaController;

    @Mock
    private PlanetaService planetaService;

    Planeta planeta;

    @BeforeEach
    public void init(){
        planeta = new Planeta("x1","Tatooine","Árido","Deserto",5);
    }

    @Test
    public void aoAdicionarPlaneta_RetornarHttpStatusCreated(){
        Response response = Response.comSucesso(planeta);

        Mockito.when(planetaService.salvar(planeta)).thenReturn(response);

        ResponseEntity<Response> retorno = planetaController.adicionarPlaneta(planeta);

        Assertions.assertNotNull(retorno);
        Assertions.assertEquals(HttpStatus.CREATED,retorno.getStatusCode());
        Assertions.assertEquals(response,retorno.getBody());
    }

    @Test
    public void aoAtualizarPlaneta_RetornarSucessoNaRequisicao(){
        Response response = Response.comSucesso(planeta);

        Mockito.when(planetaService.atualizar(planeta)).thenReturn(response);

        ResponseEntity<Response> retorno = planetaController.atualizarPlaneta(planeta);

        Assertions.assertNotNull(retorno);
        Assertions.assertEquals(HttpStatus.OK,retorno.getStatusCode());
        Assertions.assertEquals(response,retorno.getBody());
    }

    @Test
    public void aoListarPlanetasSalvos_RetornarSucessoNaRequisicao(){
        List<Planeta> planetas = new ArrayList<>(Collections.singletonList(planeta));
        Response response =Response.comSucesso(planetas);

        Mockito.when(planetaService.listarTodos()).thenReturn(response);

        ResponseEntity<Response> retorno = planetaController.listarPlanetasSalvos();

        Assertions.assertNotNull(retorno);
        Assertions.assertEquals(HttpStatus.OK,retorno.getStatusCode());
        Assertions.assertEquals(response,retorno.getBody());
    }

    @Test
    public void aoBuscarPlanetaPorNome_RetornarSucessoNaRequisicao(){
        Response response = Response.comSucesso(planeta);
        String nome = "Tatooine";

        Mockito.when(planetaService.buscarPorNome(nome)).thenReturn(response);

        ResponseEntity<Response> retorno = planetaController.buscarPlanetaPorNome(nome);

        Assertions.assertNotNull(retorno);
        Assertions.assertEquals(HttpStatus.OK,retorno.getStatusCode());
        Assertions.assertEquals(response,retorno.getBody());
    }

    @Test
    public void aoBuscarPlanetaPorId_RetornarSucessoNaRequisicao(){
        Response response = Response.comSucesso(planeta);
        String id = "x1";

        Mockito.when(planetaService.buscarPorId(id)).thenReturn(response);

        ResponseEntity<Response> retorno = planetaController.buscarPlanetaPorId(id);

        Assertions.assertNotNull(retorno);
        Assertions.assertEquals(HttpStatus.OK,retorno.getStatusCode());
        Assertions.assertEquals(response,retorno.getBody());
    }

    @Test
    public void aoRemoverPlaneta_RetornarSucessoNaRequisição(){
        Response response = Response.comSucesso(null);
        String id = "x1";

        Mockito.when(planetaService.remover(id)).thenReturn(response);

        ResponseEntity<Response> retorno = planetaController.removerPlaneta(id);

        Assertions.assertNotNull(retorno);
        Assertions.assertEquals(HttpStatus.OK,retorno.getStatusCode());
        Assertions.assertNull(retorno.getBody().getDados());

    }
}
