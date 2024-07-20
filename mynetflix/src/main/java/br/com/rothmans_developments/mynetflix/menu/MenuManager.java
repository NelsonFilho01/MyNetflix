package br.com.rothmans_developments.mynetflix.menu;

import br.com.rothmans_developments.mynetflix.model.DadosEpisodio;
import br.com.rothmans_developments.mynetflix.model.DadosSerie;
import br.com.rothmans_developments.mynetflix.model.DadosTemporada;
import br.com.rothmans_developments.mynetflix.model.Episodio;
import br.com.rothmans_developments.mynetflix.service.ConsumoApi;
import br.com.rothmans_developments.mynetflix.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class MenuManager {

    private Scanner leitura = new Scanner(System.in);

    private ConsumoApi consumoApi = new ConsumoApi();

    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";

    //SIM TO BOTANDO A CHAVE DIRETAMENTE NO CODICO, SIM SEI QUE NÃO É BACANA
    private final String API_KEY = "&apikey=6585022c";

    public void exibeMenu(){
        System.out.println("Digite o nome da serie");

        var nomeSerie = leitura.nextLine();

        var json = consumoApi.obterDados(
                ENDERECO + nomeSerie.replace(" ",  "+")  + API_KEY);
        DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);


        //System.out.println(dadosSerie);


        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= dadosSerie.totalTemporadas(); i++){
                    json = consumoApi.obterDados( ENDERECO + nomeSerie.replace(" ",  "+") + "&Season=" + i+ API_KEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}

		//temporadas.forEach(System.out::println);

//
//        for (int i = 0; i < dadosSerie.totalTemporadas(); i++){
//
//            System.out.println("Temporada " + i );
//            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
//
//            for(int j = 0; j < episodiosTemporada.size(); j++){
//                System.out.println(  episodiosTemporada.get(j).numero()
//                        + " " +episodiosTemporada.get(j).titulo());
//            }
//        }
//
//
//
//        temporadas.forEach(t -> System.out.println(t.numero()) t.episodios().forEach(
//                e -> System.out.println(e.numero() + " " + e.titulo())
//        ));
//        temporadas.forEach(
//                t -> {System.out.println("Temporada " + t.numeroTemporada());
//                t.episodios().forEach(e -> System.out.println(e.numeroEpisodio() + " " + e.titulo()));
//            }
//        );
//        temporadas.forEach(System.out::println);

        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());
//
//        System.out.println("\n Top 5 episodios");
//        dadosEpisodios.stream()
//                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
//                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
//                .map(e -> e.titulo().toUpperCase())
//                .limit(5)
//                .forEach(System.out::println);

        //System.out.println("Acabou o Top 5");
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numeroTemporada(), d))
                ).collect(Collectors.toList());

        episodios.forEach(System.out::println);

//
//        System.out.println("Digite um trecho do titulo do epsodio que deseja procurar");
//        var trechoTitulo = leitura.nextLine();
//        Optional<Episodio> episodioBuscado =  episodios.stream()
//                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
//                .findFirst();
//        if (episodioBuscado.isPresent()){
//            System.out.println("Episodio encontrado!");
//            System.out.println("Temporada" + episodioBuscado.get().getTemporada());
//        }else {
//            System.out.println("Epsodio não encontrado");
//        }


        // Filtrando
//        System.out.println("A partir de que ano você deseja ver os epsidios");
//        var ano = leitura.nextInt();
//        leitura.nextLine();
//
//        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
//
//        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        episodios.stream()
//                .filter(e -> e.getDataLancamento() != null &&   e.getDataLancamento().isAfter(dataBusca))
//                .forEach(e -> System.out.println(
//                        " Temporada " + e.getTemporada() +
//                        " Episodio: " + e.getTitulo() +
//                        " Data Lançamento: " +
//                         e.getDataLancamento().format(df)
//                ));

        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getAvaliacao)));
        System.out.println("As avalição por temporada é: " + avaliacoesPorTemporada);

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
        System.out.println("Media: " + est);
        System.out.println("Melhor: " + est.getMax());
        System.out.println("Pior: " + est.getMin());
        System.out.println("Quantidade: " + est.getCount());
    }
}
