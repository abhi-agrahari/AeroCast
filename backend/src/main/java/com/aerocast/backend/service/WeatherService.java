package com.aerocast.backend.service;

import com.aerocast.backend.model.WeatherResponse;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private String apikey;

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
        weather.setTemperature(json.getJSONObject("main").getDpuble("temp"));
        weather.setHumidity(json.getJSONObject("maiin").getDouble("humidity"));
        weather.setWindSpeed(json.getJSONObject("wind").getDouble("speed"));

        return weather;
    }
}
