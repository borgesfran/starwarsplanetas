package com.starwars.planetaswiki.service;

import com.starwars.planetaswiki.client.SwapiClient;
import com.starwars.planetaswiki.exception.ServiceException;
import com.starwars.planetaswiki.model.Planeta;
import com.starwars.planetaswiki.model.dto.PlanetaDto;
import com.starwars.planetaswiki.repository.PlanetaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PlanetaService {

    //todo ajustar arquivos q subiram pro repositório: remover o .gitignore, help.md,mvnw,mvnw.cmd
    private final PlanetaRepository planetaRepository;
    private final SwapiClient swapiClient;

    public List<Planeta> listarTodos(){
        return planetaRepository.findAll();
    }

    public Planeta buscarPorNome(String nome){
        return planetaRepository.findPlanetaByNome(nome).orElseThrow(
                ()->new ServiceException("Planeta com nome " + nome + "inexistente no banco"));
    }

    public Planeta buscarPorId(Long id){
        return planetaRepository.findById(id).orElseThrow(
                ()->new ServiceException("Planeta com o id " + id + "inexistente no banco"));
    }

    public Planeta salvar(Planeta planeta){
        return planetaRepository.save(planeta);
    }

    public Planeta atualizar(Planeta planeta){
        if(planeta.getId() == null)
            throw new ServiceException("Id do Planeta a ser atualizado não foi informado.");

        return planetaRepository.findById(planeta.getId())
                .map(planetaSalvo-> {return planetaRepository.save(planeta);})
                .orElseThrow(()->new ServiceException("Planeta com o id " + planeta.getId() + "inexistente no banco"));
    }

    public Planeta buscarQuantidadeAparicoesEmFilmes(Planeta planeta){
        PlanetaDto planetaDto = swapiClient.buscarFilmesAparicoes(planeta.getNome());
        if(!planetaDto.getFilms().isEmpty())
            planeta.setQuantidadeAparicoes(planetaDto.getFilms().size());
        return planeta;
    }

}
