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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class PlanetaService {

    private final PlanetaRepository planetaRepository;
    private final SwapiClient swapiClient;

    public Response listarTodos(){
        List<Planeta> planetas = planetaRepository.findAll();
        
        if(planetas.isEmpty())
            throw new ServiceException("Não existem dados de planetas salvos no banco.");
        
        return Response.comSucesso(planetas);
    }

    public Response buscarPorNome(String nome){
        return planetaRepository.findPlanetaByNome(nome).map(Response::comSucesso).orElseThrow(
                ()->new ServiceException("Planeta com nome " + nome + " inexistente no banco"));
    }

    public Response buscarPorId(String id){
        return planetaRepository.findById(id).map(Response::comSucesso).orElseThrow(
                ()->new ServiceException("Planeta com o id " + id + " inexistente no banco"));
    }

    @Transactional
    public Response salvar(Planeta planeta){
        ValidadorRequisicao.validarPlaneta(planeta);
        planeta = buscarQuantidadeAparicoesEmFilmes(planeta);
        return Response.comSucesso(planetaRepository.save(planeta));
    }

    @Transactional
    public Response atualizar(Planeta planeta){
        if(planeta.getId() == null)
            throw new ServiceException("Id do Planeta a ser atualizado não foi informado.");

        if(planetaRepository.findById(planeta.getId()).isPresent()){
            planeta = buscarQuantidadeAparicoesEmFilmes(planeta);
            return Response.comSucesso(planetaRepository.save(planeta));
        }

        throw  new ServiceException("Planeta com o id " + planeta.getId() + "inexistente no banco");
    }

    @Transactional
    public Response remover(String id){
        try{
            Optional<Planeta> planeta = planetaRepository.findById(id);
            if(!planeta.isPresent()){
                throw new ServiceException("Planeta com o id " + id + " inexistente no banco");
            }
            planetaRepository.delete(planeta.get());
            return Response.comSucesso(null);
        }catch (Exception e){
            return Response.comErro("Erro ao remover planeta. Detalhes: " + e.getMessage());
        }
    }

    private Planeta buscarQuantidadeAparicoesEmFilmes(Planeta planeta){
        try {
            PlanetaDto planetaDto = swapiClient.buscarFilmesAparicoes(planeta.getNome());

            if(!planetaDto.getFilms().isEmpty())
                planeta.setQuantidadeAparicoes(planetaDto.getFilms().size());
        }catch (FeignException feignException){
            log.error("Erro ao capturar a quantidade de aparições do planeta nos filmes. Tente atualizá-lo depois.");
        }

        return planeta;
    }

}
