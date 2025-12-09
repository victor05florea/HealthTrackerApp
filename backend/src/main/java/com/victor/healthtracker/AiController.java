package com.victor.healthtracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin("*")
public class AiController {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.model}")
    private String model;

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping
    public Map<String, String> chat(@RequestBody Map<String, String> userMessage) {
        String messageContent = userMessage.get("message");

        // DEBUG: Vedem daca ajunge mesajul la server
        System.out.println("--- CERERE PRIMITA ---");
        System.out.println("Mesaj de la telefon: " + messageContent);
        System.out.println("Folosesc cheia API: " + apiKey.substring(0, 5) + "..."); // Vedem doar inceputul (siguranta)

        String url = "https://api.openai.com/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("model", model);

        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> systemMessage = new HashMap<>();
        systemMessage.put("role", "system");
        systemMessage.put("content", "Ești un antrenor personal expert. Răspunde scurt în română.");
        messages.add(systemMessage);

        Map<String, String> userMsg = new HashMap<>();
        userMsg.put("role", "user");
        userMsg.put("content", messageContent);
        messages.add(userMsg);

        body.put("messages", messages);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

            // DEBUG: Vedem raspunsul de la OpenAI
            System.out.println("Raspuns primit de la OpenAI: " + response.getStatusCode());

            Map<String, Object> responseBody = response.getBody();
            List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
            Map<String, Object> firstChoice = choices.get(0);
            Map<String, String> message = (Map<String, String>) firstChoice.get("message");
            String aiReply = message.get("content");

            Map<String, String> result = new HashMap<>();
            result.put("reply", aiReply);
            return result;

        } catch (HttpClientErrorException e) {
            // AICI PRINDEM EROAREA DE LA OPENAI
            System.out.println("!!! EROARE OPENAI !!!");
            System.out.println("Status Code: " + e.getStatusCode());
            System.out.println("Mesaj Eroare: " + e.getResponseBodyAsString());

            Map<String, String> errorResult = new HashMap<>();
            errorResult.put("reply", "Eroare server: " + e.getStatusCode());
            return errorResult;
        } catch (Exception e) {
            System.out.println("!!! ALTA EROARE !!!");
            e.printStackTrace();
            Map<String, String> errorResult = new HashMap<>();
            errorResult.put("reply", "Eroare interna.");
            return errorResult;
        }
    }
}