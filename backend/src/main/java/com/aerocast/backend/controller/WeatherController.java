package com.aerocast.backend.controller;

import com.aerocast.backend.model.WeatherResponse;
import com.aerocast.backend.service.WeatherService;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }


    @GetMapping("/{city}")
    public ResponseEntity<?> getWeather(@PathVariable String city) {
        try {
            WeatherResponse weather = weatherService.getWeatherByCity(city);

            if (weather == null || weather.getDescription().startsWith("Error")) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Weather data not found for city: " + city);
            }

            return ResponseEntity.ok(weather);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching weather data.");
        }
    }

    @GetMapping("/test")
    public String check(){
        return "Test Completed";
    }

    @GetMapping("/forecast/{city}")
    public ResponseEntity<?> getForecast(@PathVariable String city) {
        JSONObject forecast = weatherService.getForecast(city);
        return ResponseEntity.ok(forecast.toMap());
    }

    @GetMapping("/validate")
    public ResponseEntity<Map<String, Boolean>> validateCity(@RequestParam String city) {
        boolean isValid = weatherService.validateCity(city);
        return ResponseEntity.ok(Map.of("valid", isValid));
    }

//    @GetMapping("/coords")
//    public WeatherResponse getWeatherByCoords(@RequestParam double lat, @RequestParam double lon) {
//        return weatherService.getWeatherByCoords(lat, lon);
//    }

//    @GetMapping("/ip")
//    public WeatherResponse getWeatherByIp(HttpServletRequest request) {
//        String userip = request.getRemoteAddr();
//
//        if ("127.0.0.1".equals(userip) || "0:0:0:0:0:0:0:1".equals(userip)) {
//            userip = "8.8.8.8";
//        }
//
//        return weatherService.getWeatherByIp(userip);
//    }

//    @GetMapping("/forecast")
//    public ResponseEntity<?> getForecast(@RequestParam double lat, @RequestParam double lon) {
//        JSONObject forecast = weatherService.getForcastForUser(lat, lon);
//        return ResponseEntity.ok(forecast.toMap());
//    }
}
