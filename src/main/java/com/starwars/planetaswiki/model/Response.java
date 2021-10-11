package com.starwars.planetaswiki.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {

    private String mensagem;
    private Object dados;

    public static Response comSucesso(Object dados){
       return Response.builder().dados(dados).mensagem("Requisição realizada com sucesso").build();
    }

    public static Response comErro(String mensagem){
        return Response.builder().dados(null).mensagem(mensagem).build();
    }
}
