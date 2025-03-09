package br.com.rothmans_developments.mynetflix;

import br.com.rothmans_developments.mynetflix.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MynetflixApplication {

	@Autowired
	private SerieRepository repositorioSerie;

	public static void main(String[] args) {
		SpringApplication.run(MynetflixApplication.class, args);
	}

}
