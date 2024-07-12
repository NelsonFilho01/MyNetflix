package br.com.rothmans_developments.mynetflix.main;

import br.com.rothmans_developments.mynetflix.model.DadosEpisodio;
import br.com.rothmans_developments.mynetflix.model.DadosSerie;
import br.com.rothmans_developments.mynetflix.model.DadosTemporada;
import br.com.rothmans_developments.mynetflix.service.ConsumoApi;
import br.com.rothmans_developments.mynetflix.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class Principal {

    private Scanner leitura = new Scanner(System.in);

    private ConsumoApi consumoApi = new ConsumoApi();

    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";

    private final String API_KEY = "&apikey=6585022c";

    public void exibeMenu(){
        System.out.println("Digite o nome da serie");

        var nomeSerie = leitura.nextLine();

        var json = consumoApi.obterDados(
                ENDERECO + nomeSerie.replace(" ",  "+")  + API_KEY);
        DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);


        System.out.println(dadosSerie);


        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= dadosSerie.totalTemporadas(); i++){
			json = consumoApi.obterDados( ENDERECO + nomeSerie.replace(" ",  "+") + "&Season=" + i+ API_KEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}

		temporadas.forEach(System.out::println);

        for (int i = 0; i < dadosSerie.totalTemporadas(); i++){

            System.out.println("Temporada " + i );
            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();

            for(int j = 0; j < episodiosTemporada.size(); j++){
                System.out.println(  episodiosTemporada.get(j).numero()
                        + " " +episodiosTemporada.get(j).titulo());
            }
        }
    }
}
