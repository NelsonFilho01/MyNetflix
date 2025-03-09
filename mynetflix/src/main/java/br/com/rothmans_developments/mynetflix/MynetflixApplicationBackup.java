package br.com.rothmans_developments.mynetflix;

import br.com.rothmans_developments.mynetflix.menu.MenuManager;
import br.com.rothmans_developments.mynetflix.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//@SpringBootApplication
//public class MynetflixApplicationBackup implements CommandLineRunner {
//
//	@Autowired
//	private SerieRepository repositorioSerie;
//
//	public static void main(String[] args) {
//		SpringApplication.run(MynetflixApplicationBackup.class, args);
//	}
//
//	@Override
//	public void run(String... args) throws Exception {
//		MenuManager principal = new MenuManager(repositorioSerie);
//		principal.exibeMenu();
//	}
//}
