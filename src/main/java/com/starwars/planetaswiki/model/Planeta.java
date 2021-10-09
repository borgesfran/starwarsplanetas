package com.starwars.planetaswiki.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Planeta {

    @Id
    private Long id;

    @Indexed(unique = true)
    private String nome;

    private String clima;

    private String terreno;

    @Transient
    private int quantidadeAparicoes;

    public Planeta quantidadeAparicoes(int quantidade){
        this.quantidadeAparicoes = quantidade;
        return this;
    }
}
