package com.starwars.planetaswiki.service;

import com.starwars.planetaswiki.client.SwapiClient;
import com.starwars.planetaswiki.exception.ServiceException;
import com.starwars.planetaswiki.model.Planeta;
import com.starwars.planetaswiki.model.Response;
import com.starwars.planetaswiki.model.dto.PlanetaDto;
import com.starwars.planetaswiki.repository.PlanetaRepository;
import com.starwars.planetaswiki.utils.ValidadorRequisicao;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PlanetaService {

    //todo ajustar arquivos q subiram pro repositório: remover o .gitignore, help.md,mvnw,mvnw.cmd
    private final PlanetaRepository planetaRepository;
    private final ObjectProvider<SwapiClient> swapiClient;

    public Response listarTodos(){
        List<Planeta> planetas = planetaRepository.findAll();
        
        if(planetas.isEmpty())
            throw new ServiceException("Não existem dados de planetas salvos no banco.");
        
        return Response.comSucesso(planetas);
    }

    public Response buscarPorNome(String nome){
        return planetaRepository.findPlanetaByNome(nome).map(Response::comSucesso).orElseThrow(
                ()->new ServiceException("Planeta com nome " + nome + "inexistente no banco"));
    }

    public Response buscarPorId(String id){
        return planetaRepository.findById(id).map(Response::comSucesso).orElseThrow(
                ()->new ServiceException("Planeta com o id " + id + "inexistente no banco"));
    }

    public Response salvar(Planeta planeta){
        ValidadorRequisicao.validarPlaneta(planeta);
        planeta = buscarQuantidadeAparicoesEmFilmes(planeta);
        return Response.comSucesso(planetaRepository.save(planeta));
    }

    public Response atualizar(Planeta planeta){
        if(planeta.getId() == null)
            throw new ServiceException("Id do Planeta a ser atualizado não foi informado.");

        if(planetaRepository.findById(planeta.getId()).isPresent()){
            planeta = buscarQuantidadeAparicoesEmFilmes(planeta);
            return Response.comSucesso(planetaRepository.save(planeta));
        }

        throw  new ServiceException("Planeta com o id " + planeta.getId() + "inexistente no banco");
    }

    public Response remover(String id){
        Planeta planeta = (Planeta) this.buscarPorId(id).getDados();
        planetaRepository.delete(planeta);
        return Response.comSucesso(null);
    }

    public Planeta buscarQuantidadeAparicoesEmFilmes(Planeta planeta){
        try {
            PlanetaDto planetaDto = swapiClient.getObject().buscarFilmesAparicoes(planeta.getNome());

            if(!planetaDto.getFilms().isEmpty())
                planeta.setQuantidadeAparicoes(planetaDto.getFilms().size());
        }catch (FeignException feignException){
            log.error("Erro ao capturar a quantidade de aparições do planeta nos filmes. Tente atualizá-lo depois.");
        }

        return planeta;
    }

}
