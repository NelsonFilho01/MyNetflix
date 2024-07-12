package br.com.rothmans_developments.mynetflix;

import br.com.rothmans_developments.mynetflix.model.DadosEpisodio;
import br.com.rothmans_developments.mynetflix.model.DadosSerie;
import br.com.rothmans_developments.mynetflix.model.DadosTemporada;
import br.com.rothmans_developments.mynetflix.service.ConsumoApi;
import br.com.rothmans_developments.mynetflix.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MynetflixApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MynetflixApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		var consumoApi = new ConsumoApi();
		var json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=6585022c");

//		System.out.println(json);
//		json = consumoApi.obterDados("https://coffee.alexflipnote.dev/random.json");
		System.out.println(json);

		ConverteDados conversor = new ConverteDados();
		DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dadosSerie);


		json = consumoApi.obterDados( "https://www.omdbapi.com/?t=gilmore+girls&season=1&episode=2&apikey=6585022c");

		DadosEpisodio dadosEpisodio = conversor.obterDados(json, DadosEpisodio.class);
		System.out.println(dadosEpisodio);

		List<DadosTemporada> temporadas = new ArrayList<>();

		for (int i = 1; i <= dadosSerie.totalTemporadas(); i++){
			json = consumoApi.obterDados( "https://www.omdbapi.com/?t=gilmore+girls&season=" + i + "&apikey=6585022c");
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);

	}
}
