package com.aerocast.backend.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmailVerificationService {
    @Value("${emaildetective.api.key}")
    private String apiKey;

    private static final String API_URL = "https://api.emaildetective.io/emails/";

    public JSONObject verifyEmail(String email) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", apiKey);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    API_URL + email,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            return new JSONObject(response.getBody());
        } catch (Exception e) {
            JSONObject error = new JSONObject();
            error.put("error", "Failed to verify email");
            error.put("message", e.getMessage());
            return error;
        }
    }

    public boolean isValidEmail(String email) {
        JSONObject result = verifyEmail(email);
        return result.optBoolean("valid_email", false)
                && !result.optBoolean("disposable", false)
                && !result.optBoolean("nonsense", false);
    }
}
