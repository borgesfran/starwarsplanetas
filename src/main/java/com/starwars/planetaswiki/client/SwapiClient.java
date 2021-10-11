package com.starwars.planetaswiki.client;

import com.starwars.planetaswiki.model.dto.PlanetaDto;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Headers("Content-Type: application/json")
@FeignClient(name = "swapi", url = "https://swapi.dev/api")
public interface SwapiClient {

    @GetMapping(value = "/planets/")
    PlanetaDto buscarFilmesAparicoes(@RequestParam("search") String search);

}
