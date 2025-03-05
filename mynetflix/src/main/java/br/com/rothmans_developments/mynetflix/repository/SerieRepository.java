package br.com.rothmans_developments.mynetflix.repository;

import br.com.rothmans_developments.mynetflix.model.Categoria;
import br.com.rothmans_developments.mynetflix.model.Episodio;
import br.com.rothmans_developments.mynetflix.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository  extends JpaRepository<Serie, Long> {
        Optional<Serie>findByTituloContainsIgnoreCase(String nomeSerie);

        List<Serie> findByAtoresContainsIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeAtor, double avalicao);

        List<Serie> findTop5ByOrderByAvaliacao();

        List<Serie> findByGenero(Categoria categoria);

        List<Serie> findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(int totalTemporadas, double avaliacao);

        @Query("select s from Serie s WHERE s.totalTemporadas <= :totalTemporadas AND s.avaliacao >= :avaliacao")
        List<Serie> seriesPorTemporadaEAvaliacao(int totalTemporadas, double avaliacao);

        @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:nomeTrecho%")
        List<Episodio> episodioPorTrecho(String nomeTrecho);

        @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie AND YEAR(e.dataLancamento) >= :anoLancamento")
        List<Episodio> episodiosPorSerieEAno(Serie serie, int anoLancamento);

        @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.avaliacao DESC LIMIT 5")
        List<Episodio> topEpisodiosPorSerie(Serie serie);

}
