package com.starwars.planetaswiki.repository;

import com.starwars.planetaswiki.model.Planeta;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PlanetaRepository extends MongoRepository<Planeta,Long> {

    Optional<Planeta> findPlanetaByNome(String nome);

}
