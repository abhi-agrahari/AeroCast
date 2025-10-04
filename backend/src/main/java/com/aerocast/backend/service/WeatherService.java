package com.aerocast.backend.service;

import com.aerocast.backend.model.WeatherResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${openweather.api.key}")
    private String apikey;

    @Value("${openweather.api.url}")
    private String apiurl;

    public WeatherResponse getWeatherByCity(String city){
        String url = apiurl + "?q=" + city + "&appid=" + apikey + "&units=metric";

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        JSONObject json = new JSONObject(response);

        WeatherResponse weather = new WeatherResponse();

        weather.setCity(json.getString("name"));
        weather.setDescription(json.getJSONArray("weather")
                                    .getJSONObject(0)
                                    .getString("description"));
        weather.setTemperature(json.getJSONObject("main").getDouble("temp"));
        weather.setHumidity(json.getJSONObject("main").getDouble("humidity"));
        weather.setWindSpeed(json.getJSONObject("wind").getDouble("speed"));

        return weather;
    }
}
