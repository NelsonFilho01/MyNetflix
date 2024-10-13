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

    public MenuManager(SerieRepository repositorioSerie) {
        this.repositorioSerie = repositorioSerie;
    }


    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    |-----------------------|
                    | 1 - Buscar Serie      |
                    | 2 - Buscar Episodio   |
                    | 3-  Listar Series     |
                    |                       |
                    |                       |
                    | 0 - Sair ...          |
                    |-----------------------|
                    """;

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
                case 0:
                    System.out.println("Saindo ...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

        }
    }



    private void buscarEpisodio() {
        DadosSerie dadosSerie = getDadosSerie();
        List<DadosTemporada> temporadas = new ArrayList<>();


        for (int i = 1; i <= dadosSerie.totalTemporadas(); i++){
            var json = consumoApi.obterDados(
                    ENDERECO + dadosSerie.titulo().replace(" ", "+")
                            + "&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);



        }
        dadosSeries.add(dadosSerie);
        temporadas.forEach(System.out::println);
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
        List<Serie> series = repositorioSerie.findAll();
        series.stream().sorted(Comparator.comparing(Serie::getTitulo))
                .forEach(System.out::println);
    }



}
