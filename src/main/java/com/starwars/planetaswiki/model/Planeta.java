package com.starwars.planetaswiki.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Representação abstrata de um planeta do Universo Star Wars")
@Document
public class Planeta {

    @Id
    private String id;

    @Schema(description = "Nome do planeta")
    @Indexed(unique = true)
    private String nome;

    @Schema(description = "Clima do planeta")
    private String clima;

    @Schema(description = "Terreno do planeta")
    private String terreno;

    @Schema(description = "Quantidade de aparições do planeta nos filmes da saga. " +
            "Esta informação é preenchida sempre ao se cadastrar um novo planeta ou ao atualizar um já existente, " +
            "com base nas informações disponíveis em https://swapi.dev/about.")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Transient
    private int quantidadeAparicoes;
}
