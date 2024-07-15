package br.com.rothmans_developments.mynetflix.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(@JsonAlias("Title") String titulo,

                         @JsonAlias("totalSeasons") Integer totalTemporadas,

                         @JsonAlias("imdbRating") String avaliacao
)
{
}




