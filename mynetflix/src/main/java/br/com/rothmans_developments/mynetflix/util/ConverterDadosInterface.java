package br.com.rothmans_developments.mynetflix.util;

public interface ConverterDadosInterface {
    <T> T obterDados(String Json, Class<T> classe);
}
