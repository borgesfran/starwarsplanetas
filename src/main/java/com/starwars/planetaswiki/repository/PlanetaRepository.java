package com.starwars.planetaswiki.repository;

import com.starwars.planetaswiki.model.Planeta;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PlanetaRepository extends MongoRepository<Planeta,String> {

    Optional<Planeta> findPlanetaByNome(String nome);

}
