package com.aerocast.backend.controller;

import com.aerocast.backend.model.WeatherResponse;
import com.aerocast.backend.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
