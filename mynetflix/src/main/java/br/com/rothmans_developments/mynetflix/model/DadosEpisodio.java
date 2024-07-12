package br.com.rothmans_developments.mynetflix.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosEpisodio(
        @JsonAlias("Title")
        String tirulo,
        @JsonAlias("Episode")
        Integer numero,
        @JsonAlias("imdbRating")
        String avaliacao,
        @JsonAlias("Responsed")
        String dataLancamento
)
{
}
