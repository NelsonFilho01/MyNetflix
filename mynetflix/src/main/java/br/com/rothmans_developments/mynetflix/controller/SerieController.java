package br.com.rothmans_developments.mynetflix.controller;

import br.com.rothmans_developments.mynetflix.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SerieController {

    @Autowired
    private SerieRepository serieRepository;

   @GetMapping("/series")
   public String listaSeries() {
       return "Serie lista";
   }
}
