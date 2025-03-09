package br.com.rothmans_developments.mynetflix.dto;

import br.com.rothmans_developments.mynetflix.model.Categoria;


public record SerieDTO(
        Long id,
        String titulo,
        Integer totalTemporadas,
        Double avaliacao,
        Categoria genero,
        String atores,
        String poster
) {
}
