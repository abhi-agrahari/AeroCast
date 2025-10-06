package com.aerocast.backend.controller;

import com.aerocast.backend.model.WeatherResponse;
import com.aerocast.backend.service.WeatherService;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }


    @GetMapping("/{city}")
    public WeatherResponse getWeather(@PathVariable String city){
        return weatherService.getWeatherByCity(city);
    }

    @GetMapping("/test")
    public String check(){
        return "Test Completed";
    }

    @GetMapping("/coords")
    public WeatherResponse getWeatherByCoords(@RequestParam double lat, @RequestParam double lon) {
        return weatherService.getWeatherByCoords(lat, lon);
    }

    @GetMapping("/ip")
    public WeatherResponse getWeatherByIp(HttpServletRequest request) {
        String userip = request.getRemoteAddr();

        if ("127.0.0.1".equals(userip) || "0:0:0:0:0:0:0:1".equals(userip)) {
            userip = "8.8.8.8";
        }

        return weatherService.getWeatherByIp(userip);
    }

    @GetMapping("/forecast/{city}")
    public ResponseEntity<?> getForecast(@PathVariable String city) {
        JSONObject forecast = weatherService.getForecast(city);
        return ResponseEntity.ok(forecast.toMap());
    }

    @GetMapping("/forecast")
    public ResponseEntity<?> getForecast(@RequestParam double lat, @RequestParam double lon) {
        JSONObject forecast = weatherService.getForcastForUser(lat, lon);
        return ResponseEntity.ok(forecast.toMap());
    }
}
