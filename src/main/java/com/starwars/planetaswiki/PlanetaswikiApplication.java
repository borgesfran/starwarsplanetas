package com.starwars.planetaswiki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PlanetaswikiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlanetaswikiApplication.class, args);
	}

}
