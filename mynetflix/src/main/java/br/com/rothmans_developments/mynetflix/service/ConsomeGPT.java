package br.com.rothmans_developments.mynetflix.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ConsomeGPT {
    public static String obterTraducao(String texto) {
        String token = System.getenv("GPT_TOKEN");

        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("OpenAI token não encontrado. Certifique-se de que a variável de ambiente GPT_TOKEN está definida.");
        }

        OpenAiService service = new OpenAiService(token);

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
