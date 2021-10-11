package com.starwars.planetaswiki.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private String id;

    @Indexed(unique = true)
    private String nome;

    private String clima;

    private String terreno;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Transient
    private int quantidadeAparicoes;
}
