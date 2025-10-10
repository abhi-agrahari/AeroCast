package com.aerocast.backend.controller;

import com.aerocast.backend.model.UserPreference;
import com.aerocast.backend.service.EmailPreferenceService;
import com.aerocast.backend.service.EmailVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/preferences")
public class EmailController {
    private final EmailPreferenceService service;

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private EmailVerificationService emailVerificationService;

    public EmailController(EmailPreferenceService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<?> savePreference(@RequestBody UserPreference preference) {
        if (preference.getEmail() == null || preference.getEmail().isBlank()) {
            return ResponseEntity.badRequest().body("Email is required");
        }
        if (preference.getCity() == null || preference.getCity().isBlank()) {
            return ResponseEntity.badRequest().body("City is required");
        }
        if (!preference.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            return ResponseEntity.badRequest().body("Invalid email format");
        }

        if (!emailVerificationService.isValidEmail(preference.getEmail())) {
            return ResponseEntity.badRequest().body("Invalid or disposable email address");
        }

        UserPreference saved = service.saveOrUpdate(preference);
        return ResponseEntity.ok(saved);
    }



    @GetMapping("/{email}")
    public ResponseEntity<?> getPreference(@PathVariable String email) {
        Optional<UserPreference> pref = service.getByEmail(email);

        if (pref.isPresent()) {
            return ResponseEntity.ok(pref.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No preferences found for email: " + email);
        }
    }

    @GetMapping("/validate/{city}")
    public ResponseEntity<?> validateCity(@PathVariable String city) {
        try {
            String apiKey = "YOUR_OPENWEATHER_API_KEY"; // replace with your key
            String url = "https://api.openweathermap.org/data/2.5/weather?q="
                    + city + "&appid=" + apiKey;

            restTemplate.getForObject(url, String.class);
            return ResponseEntity.ok("City is valid");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid city");
        }
    }
}
