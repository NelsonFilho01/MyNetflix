package br.com.rothmans_developments.mynetflix.service;

import br.com.rothmans_developments.mynetflix.model.DadosSerie;

public class TestandoDeepSeek {
    public static void main(String[] args) {
        DadosSerie dadosSerie = new DadosSerie(
                "The Boys",
                5,
                "8.4",
                "Action, Comedy, Crime",
                "Karl Urban, Jack Quaid, Antony Starr",
                "https://m.media-amazon.com/images/M/MV5BYTY2ZjYyNGUtZGVkZS00MDNhLWIwMjMtZDk4MmQ5ZWI0NTY4XkEyXkFqcGdeQXVyMTY3MDE5MDY1._V1_SX300.jpg",
                "A group of vigilantes set out to take down corrupt superheroes who abuse their superpowers."
        );

        String enredoTraduzido = ConsomeDeepSeek.obterTraducaoDeepSeek(dadosSerie.enredo().trim());
        System.out.println("Enredo Traduzido pelo DeepSeek: " + enredoTraduzido);
    }
}
