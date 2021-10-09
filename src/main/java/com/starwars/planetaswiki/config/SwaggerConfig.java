package com.starwars.planetaswiki.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customizacaoOpenApi(){
        return new OpenAPI()
                .info(new Info().title("Wiki Planetas Star Wars")
                .version("v0.0.1")
                .description("Serviço que permite obter, editar, inserir e deletar informações sobre os planetas " +
                        "que aparecem em Star Wars.")
                .contact(new Contact().name("Francinette Borges").email("francinette.borge@gmail.com")));

    }
}
