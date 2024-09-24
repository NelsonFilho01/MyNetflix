package br.com.rothmans_developments.mynetflix.repository;

import br.com.rothmans_developments.mynetflix.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SerieRepository  extends JpaRepository<Serie, Long> {
}
