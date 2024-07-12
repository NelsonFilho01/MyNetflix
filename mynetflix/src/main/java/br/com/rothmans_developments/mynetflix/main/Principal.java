package br.com.rothmans_developments.mynetflix.main;

import br.com.rothmans_developments.mynetflix.service.ConsumoApi;

import java.util.Scanner;

public class Principal {

    private Scanner leitura = new Scanner(System.in);

    private ConsumoApi consumoApi = new ConsumoApi();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";

    private final String API_KEY = "&apikey=6585022c";

    public void exibeMenu(){
        System.out.println("Digite o nome da serie");
        var nomeSerie = leitura.nextLine();


        var json = consumoApi.obterDados(
        ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
    }
}
