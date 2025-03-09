//package br.com.rothmans_developments.mynetflix.util;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import okhttp3.*;
//
//import java.io.IOException;
//
//public class ConsomeDeepSeek {
//
//    private static final String API_URL = "https://api.deepseek.com/chat/completions";
//    private static final ObjectMapper mapper = new ObjectMapper();
//
//    public static String obterTraducaoDeepSeek(String texto) {
//        String token = System.getenv("DEEPSEEK_TOKEN");
//
//        if (token == null || token.isEmpty()) {
//            throw new IllegalArgumentException("DeepSeek token não encontrado. Certifique-se de que a variável de ambiente DEEPSEEK_TOKEN está definida.");
//        }
//
//        OkHttpClient client = new OkHttpClient();
//
//        String jsonBody = """
//        {
//          "model": "deepseek-chat",
//          "messages": [
//            {"role": "system", "content": "Você é um assistente útil que traduz textos para o português."},
//            {"role": "user", "content": "Traduza para o português: %s"}
//          ],
//          "stream": false
//        }
//        """.formatted(texto);
//
//        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));
//        Request request = new Request.Builder()
//                .url(API_URL)
//                .addHeader("Content-Type", "application/json")
//                .addHeader("Authorization", "Bearer " + token)
//                .post(body)
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            if (!response.isSuccessful()) {
//                throw new IOException("Erro na resposta da API DeepSeek: " + response.code() + " - " + response.message());
//            }
//
//            String responseBody = response.body().string();
//            JsonNode jsonNode = mapper.readTree(responseBody);
//            return jsonNode.get("choices").get(0).get("message").get("content").asText().trim();
//
//        } catch (Exception e) {
//            System.err.println("Erro ao chamar o DeepSeek: " + e.getMessage());
//            return "Erro ao obter tradução: " + e.getMessage();
//        }
//    }
//}
