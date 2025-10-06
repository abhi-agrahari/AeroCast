package com.aerocast.backend.service;

import com.aerocast.backend.model.WeatherResponse;
import org.apache.catalina.valves.JsonAccessLogValve;
import org.json.JSONArray;
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

    public WeatherResponse getWeatherByCoords(double lat, double lon){
        String url = apiurl + "?lat=" + lat + "&lon=" + lon + "&appid=" + apikey + "&units=metric";

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        JSONObject json = new JSONObject(response);

        WeatherResponse weather = new WeatherResponse();

        weather.setCity(json.getString("name"));
        weather.setDescription(json.getJSONArray("weather").getJSONObject(0).getString("description"));
        weather.setTemperature(json.getJSONObject("main").getDouble("temp"));
        weather.setHumidity(json.getJSONObject("main").getDouble("humidity"));
        weather.setWindSpeed(json.getJSONObject("wind").getDouble("speed"));

        return weather;
    }

    public WeatherResponse getWeatherByIp(String ip){
        String geoUrl = "http://ip-api.com/json/" + ip;
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(geoUrl, String.class);

        JSONObject geoJson = new JSONObject(response);

        double lat = geoJson.getDouble("lat");
        double lon = geoJson.getDouble("lon");

        return getWeatherByCoords(lat, lon);
    }

    public JSONObject getForecast(String city) {
        String url = "https://api.openweathermap.org/data/2.5/forecast?q="
                + city + "&appid=" + apikey + "&units=metric";

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        return new JSONObject(response);
    }

    public JSONObject getForcastForUser(double lat, double lon) {
        String url = "https://api.openweathermap.org/data/2.5/forecast"
                + "?lat=" + lat
                + "&lon=" + lon
                + "&appid=" + apikey
                + "&units=metric";

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        return new JSONObject(response);
    }
}
