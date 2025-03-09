package br.com.rothmans_developments.mynetflix.service;


import br.com.rothmans_developments.mynetflix.dto.SerieDTO;
import br.com.rothmans_developments.mynetflix.model.Serie;
import br.com.rothmans_developments.mynetflix.repository.SerieRepository;
import br.com.rothmans_developments.mynetflix.util.ConsumoApi;
import br.com.rothmans_developments.mynetflix.util.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SerieService {

//    private ConsumoApi consumoApi = new ConsumoApi();
//
//    private ConverteDados conversor = new ConverteDados();
//
//    private final String ENDERECO = "https://www.omdbapi.com/?t=";
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
        return converteDados(serieRepository.findTop5ByOrderByEpisodiosDataLancamentoDesc());
    }


    public static List<SerieDTO> converteDados(List<Serie> serie) {
        return serie.stream()
                .map(s -> new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(), s.getGenero(),
                        s.getAtores(), s.getPoster())).toList();
    }

    public SerieDTO obterPorId(Long id) {
        Optional<Serie> serie = serieRepository.findById(id);

        if (serie.isPresent()) {
            Serie s = serie.get();
            SerieDTO sDTO = new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(), s.getGenero(), s.getAtores(), s.getPoster());
            return sDTO;
        }
        return null;
    }

}

