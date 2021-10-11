package com.starwars.planetaswiki.service;

import com.starwars.planetaswiki.client.SwapiClient;
import com.starwars.planetaswiki.exception.ServiceException;
import com.starwars.planetaswiki.model.Planeta;
import com.starwars.planetaswiki.model.Response;
import com.starwars.planetaswiki.model.dto.PlanetaDto;
import com.starwars.planetaswiki.repository.PlanetaRepository;
import com.starwars.planetaswiki.utils.ValidadorRequisicao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class PlanetaServiceTest {

    @Spy
    @InjectMocks
    private PlanetaService planetaService;

    @Mock
    private PlanetaRepository planetaRepository;

    @Mock
    private SwapiClient swapiClient;

    Planeta planeta;

    @BeforeEach
    public void init(){
        planeta = new Planeta("x1","Tatooine","Árido","Deserto",5);
    }

    @Test
    void aoListarTodos_RetornarResponseComSucessoContendoLista(){
        List<Planeta> planetas = new ArrayList<>(Collections.singletonList(planeta));

        Mockito.when(planetaRepository.findAll()).thenReturn(planetas);

        Response retorno = planetaService.listarTodos();

        Assertions.assertNotNull(retorno);
        Assertions.assertNotNull(retorno.getDados());

    }

    @Test
    void aoListarTodos_RetornarServiceException(){

        Mockito.doReturn(new ArrayList<>()).when(planetaRepository).findAll();

        Assertions.assertThrows(ServiceException.class, ()->planetaService.listarTodos()).getMessage();

    }

    @Test
    void aoBuscarPorNome_RetornarResponseComSucessoContendoPlaneta(){
        String nome = "Tatooine";
        Mockito.when(planetaRepository.findPlanetaByNome(nome)).thenReturn(Optional.of(planeta));

        Response retorno = planetaService.buscarPorNome(nome);

        Assertions.assertNotNull(retorno);
        Assertions.assertEquals(Planeta.class,retorno.getDados().getClass());

    }

    @Test
    void aoBuscarPorNome_RetornarServiceException(){

        Mockito.doThrow(ServiceException.class).when(planetaRepository).findPlanetaByNome(Mockito.anyString());

        Assertions.assertThrows(ServiceException.class, () -> planetaService.buscarPorNome("Tatooine"));

    }

    @Test
    void aoBuscarPorId_RetornarResponseComSucessoContendoPlaneta(){
        String id = "x1";
        Mockito.when(planetaRepository.findById(id)).thenReturn(Optional.of(planeta));

        Response retorno = planetaService.buscarPorId(id);

        Assertions.assertNotNull(retorno);
        Assertions.assertEquals(Planeta.class,retorno.getDados().getClass());
    }

    @Test
    void aoBuscarPorId_RetornarServiceException(){

        Mockito.doThrow(ServiceException.class).when(planetaRepository).findById(Mockito.anyString());

        Assertions.assertThrows(ServiceException.class, () -> planetaService.buscarPorId("x1"));

    }

    @Test
    void aoSalvar_RetornarPlanetaContendoQuantidadeAparicoes(){
        PlanetaDto planetaDto = new PlanetaDto("Tatooine",new ArrayList<>(Arrays.asList("Filme 1", "Filme 2")));

        Mockito.when(swapiClient.buscarFilmesAparicoes("Tatooine")).thenReturn(planetaDto);
        Mockito.when(planetaRepository.save(planeta)).thenAnswer(invocationOnMock -> {
            planeta.setQuantidadeAparicoes(planetaDto.getFilms().size());
            return planeta;
        }
        );

        Response response = planetaService.salvar(planeta);

        Mockito.verify(swapiClient,Mockito.atLeastOnce()).buscarFilmesAparicoes(Mockito.anyString());
        Assertions.assertNotNull(response);
        Assertions.assertEquals(Planeta.class,response.getDados().getClass());

    }

    @Test
    void aoAtualizar_RetornarPlanetaContendoQuantidadeAparicoes(){
        PlanetaDto planetaDto = new PlanetaDto("Tatooine",new ArrayList<>(Arrays.asList("Filme 1", "Filme 2")));

        Mockito.when(planetaRepository.findById(Mockito.anyString())).thenReturn(Optional.of(planeta));
        Mockito.when(swapiClient.buscarFilmesAparicoes("Tatooine")).thenReturn(planetaDto);
        Mockito.when(planetaRepository.save(planeta)).thenAnswer(invocationOnMock -> {
                    planeta.setQuantidadeAparicoes(planetaDto.getFilms().size());
                    return planeta;
                }
        );

        Response response = planetaService.atualizar(planeta);

        Mockito.verify(swapiClient,Mockito.atLeastOnce()).buscarFilmesAparicoes(Mockito.anyString());
        Mockito.verify(planetaRepository,Mockito.atLeastOnce()).findById(Mockito.anyString());
        Assertions.assertNotNull(response);
        Assertions.assertEquals(Planeta.class,response.getDados().getClass());

    }

    @Test
    void aoAtualizar_RetornarServiceException_IdNulo(){

        planeta.setId(null);

        Assertions.assertThrows(ServiceException.class, () -> planetaService.atualizar(planeta));

    }

    @Test
    void aoAtualizar_RetornarServiceException_PlanetaInexistenteNoBanco(){

        Mockito.when(planetaRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(ServiceException.class, () -> planetaService.atualizar(planeta));

    }

   @Test
    void aoRemover_RetornarResponseComSucessoSemDados(){
        Mockito.when(planetaRepository.findById(Mockito.anyString())).thenReturn(Optional.of(planeta));
        Mockito.doNothing().when(planetaRepository).delete(planeta);

        Response retorno = planetaService.remover("x1");

        Assertions.assertNotNull(retorno);
        Assertions.assertNull(retorno.getDados());

   }








}
