package br.com.rothmans_developments.mynetflix.service;

public interface ConverterDadosInterface {
    <T> T obterDados(String Json, Class<T> classe);
}
