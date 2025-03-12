package br.com.rothmans_developments.mynetflix.service;


import br.com.rothmans_developments.mynetflix.dto.EpisodioDTO;
import br.com.rothmans_developments.mynetflix.dto.SerieDTO;
import br.com.rothmans_developments.mynetflix.model.Categoria;
import br.com.rothmans_developments.mynetflix.model.Serie;
import br.com.rothmans_developments.mynetflix.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {

//    private ConsumoApi consumoApi = new ConsumoApi();
//
//    private ConverteDados conversor = new ConverteDados();
//
//    private final String ENDERECO = "https://www.omdbapi.com/?t=";O
//
//    //Essa chave nem minha é
//    private final String API_KEY = "&apikey=6585022c";

    @Autowired
    private SerieRepository serieRepository;

    public List<SerieDTO> obterSeries() {
        return converteDados(serieRepository.findAll());
    }

    public List<SerieDTO> obterTop5() {
        return converteDados(serieRepository.findTop5ByOrderByAvaliacao());
    }

    public List<SerieDTO> obterLancamentos() {
        return converteDados(serieRepository.lancamentosMaisRecentes());
    }



    public SerieDTO obterPorId(Long id) {
        Optional<Serie> serie = serieRepository.findById(id);

        if (serie.isPresent()) {
            Serie s = serie.get();
            return new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(), s.getGenero(), s.getAtores(), s.getPoster());
        }
        return null;
    }

    public List<EpisodioDTO> obterTodasTemporadas(Long id) {
        Optional<Serie> serie = serieRepository.findById(id);

        if (serie.isPresent()) {
            Serie s = serie.get();
            return s.getEpisodios().stream()
                    .map(e -> new EpisodioDTO(e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public List<EpisodioDTO> obterTemporadasPorNumero(Long id, Integer numero) {
        return serieRepository.obterEpisodiosPorTemporada(id, numero)
                .stream()
                .map(e -> new EpisodioDTO(e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo()))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> obterSeriesPorCategoria(String categoria) {
        Categoria categoriaEnum = Categoria.fromPortugues(categoria);
        return converteDados(serieRepository.findByGenero(categoriaEnum));



    }



















    public static List<SerieDTO> converteDados(List<Serie> serie) {
        return serie.stream()
                .map(s -> new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(), s.getGenero(),
                        s.getAtores(), s.getPoster())).toList();
    }



}


