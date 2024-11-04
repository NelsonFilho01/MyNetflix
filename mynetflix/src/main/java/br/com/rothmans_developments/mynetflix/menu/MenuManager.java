package br.com.rothmans_developments.mynetflix.menu;

import br.com.rothmans_developments.mynetflix.model.*;
import br.com.rothmans_developments.mynetflix.repository.SerieRepository;
import br.com.rothmans_developments.mynetflix.service.ConsumoApi;
import br.com.rothmans_developments.mynetflix.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class MenuManager {

    private Scanner leitura = new Scanner(System.in);

    private ConsumoApi consumoApi = new ConsumoApi();

    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";

    //Essa chave nem minha é
    private final String API_KEY = "&apikey=6585022c";

    private List<DadosSerie> dadosSeries = new ArrayList<>();

    private SerieRepository repositorioSerie;

    private List<Serie> series = new ArrayList<>();

    public MenuManager(SerieRepository repositorioSerie) {
        this.repositorioSerie = repositorioSerie;
    }


    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {

            var menu = """
                    |---------------------------------|
                    | 1 - Buscar Serie                |
                    | 2 - Buscar Episodio             |
                    | 3-  Listar Series               |
                    | 4 - Buscar Seria por titulo     |
                    | 5 - Buscar Serie por Ator       |
                    | 6 - Buscar por Categoria        |
                    | 7 - Serie tem e Av              |
                    |                                 |
                    |                                 |
                    |                                 |
                    | 0 - Sair ...                    |
                    |---------------------------------|
                    """;


            buscarTop5Series();
            System.out.println(menu);

            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerie();
                    break;
                case 2:
                    buscarEpisodio();
                    break;
                 case 3:
                    listarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarSeriePorAtor();
                    break;
                case 6:
                    buscarEpisodioPorCategoria();
                    break;
                case 7:
                    filtrarSeriesPorTemporadaEAvaliacao();
                    break;
                case 0:
                    System.out.println("Saindo ...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }


    private void buscarEpisodio() {
        listarSeriesBuscadas();
        System.out.println("Escolha uma série pelo nome: ");
        var nomeSerie = leitura.nextLine();


        Optional<Serie> serie = series.stream()
                .filter(s -> s.getTitulo().toLowerCase().contains(nomeSerie.toLowerCase()))
                .findFirst();

        if(serie.isPresent()) {

        var serieEncontrada = serie.get();
        List<DadosTemporada> temporadas = new ArrayList<>();


        for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++){
            var json = consumoApi.obterDados(
                    ENDERECO + serieEncontrada.getTitulo().replace(" ", "+")
                            + "&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);

        }

       List<Episodio> episodios = temporadas.stream()
                        .flatMap(d -> d.episodios().stream()
                                .map(e -> new Episodio(d.numeroTemporada(), e)))
                                .collect(Collectors.toList());

        serieEncontrada.setEpisodios(episodios);

        repositorioSerie.save(serieEncontrada);

        temporadas.forEach(System.out::println);
    }else {
            System.out.println("serie não encontrada");
        }
}

    private void buscarSerie(){
        DadosSerie dados = getDadosSerie();
        Serie serie = new Serie(dados);
        //dadosSeries.add(dados);
        repositorioSerie.save(serie);
        System.out.println(dados);
    }

    private DadosSerie getDadosSerie(){
        System.out.println("Qual o nome da serie? ");
        var nomeSerie = leitura.nextLine();

        var json = consumoApi.obterDados(
                ENDERECO + nomeSerie.replace(" ", "+") +API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void listarSeriesBuscadas() {
        series = repositorioSerie.findAll();
        series.stream().sorted(Comparator.comparing(Serie::getTitulo))
                .forEach(System.out::println);
    }

    private void buscarSeriePorTitulo() {
        System.out.println("Escolha uma série pelo nome: ");
        var nomeSerie = leitura.nextLine();
        Optional<Serie> serieBuscada = repositorioSerie.findByTituloContainsIgnoreCase(nomeSerie);
        if(serieBuscada.isPresent()) {
            System.out.println("Dados serie: " + serieBuscada.get());
        }else{
            System.out.println("Serie não encontrada");
        }
    }

    private void buscarSeriePorAtor() {
        System.out.println("Qual serie para buscar por ator? ");
        var nomeAtor = leitura.nextLine();
        System.out.println("Minimo de avaliacões? ");
        var avalicao = leitura.nextDouble();
        List<Serie> serieEncontrada = repositorioSerie.findByAtoresContainsIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor, avalicao);
//        System.out.println(serieEncontrada);

        System.out.println("Séries e que " + nomeAtor + " trabalhador ");
        serieEncontrada.forEach(s ->
                System.out.println(s.getTitulo() + "avalicao: " + s.getAvaliacao()));
    }

    private void buscarTop5Series() {
        List<Serie> serieTop = repositorioSerie.findTop5ByOrderByAvaliacao();
        serieTop.forEach(System.out::println);
    }

    private void buscarEpisodioPorCategoria() {
        System.out.println("Serie que categoria? ");

        var nomeGenero = leitura.nextLine();
        Categoria categoria = Categoria.fromPortugues(nomeGenero);
        List<Serie> seriesPorCategoria = repositorioSerie.findByGenero(categoria);
        System.out.println("Series por categorias " + nomeGenero);
        seriesPorCategoria.forEach(System.out::println);

    }

    private void filtrarSeriesPorTemporadaEAvaliacao(){
        System.out.println("Filtrar séries até quantas temporadas? ");
        var totalTemporadas = leitura.nextInt();
        leitura.nextLine();
        System.out.println("Com avaliação a partir de que valor? ");
        var avaliacao = leitura.nextDouble();
        leitura.nextLine();
        //List<Serie> filtroSeries = repositorioSerie.findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(totalTemporadas, avaliacao);
        List<Serie> filtroSeries = repositorioSerie.seriesPorTemporadaEAvaliacao();
        System.out.println("*** Séries filtradas ***");
        filtroSeries.forEach(s ->
                System.out.println(s.getTitulo() + "  - avaliação: " + s.getAvaliacao()));
    }

}
