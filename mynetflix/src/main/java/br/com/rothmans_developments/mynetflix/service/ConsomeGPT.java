package br.com.rothmans_developments.mynetflix.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ConsomeGPT {
    public static String obterTraducao(String texto) {

        OpenAiService service = new OpenAiService("Segredo");

        CompletionRequest requisicao = CompletionRequest.builder()
                .model("gpt-3.5-turbo-instruct")
                .prompt("traduza para o português o texto: " + texto)
                .maxTokens(1000)
                .temperature(0.7)
                .build();


        try {
            var resposta = service.createCompletion(requisicao);
            return resposta.getChoices().get(0).getText().trim();
        } catch (Exception e) {
            System.err.println("Erro ao chamar o GPT: " + e.getMessage());
            return "Erro ao obter tradução: " + e.getMessage();
        }
    }
}
