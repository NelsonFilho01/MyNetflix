package br.com.rothmans_developments.mynetflix.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ConverteDados implements ConverterDadosInterface {
    private ObjectMapper mapper = new ObjectMapper();


    @Override
    public <T> T obterDados(String Json, Class<T> classe) {
        try {
            return mapper.readValue(Json, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
