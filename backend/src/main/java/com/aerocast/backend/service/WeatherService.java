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

    public WeatherResponse getWeatherByCity(String city) {
        RestTemplate restTemplate = new RestTemplate();
        String url = apiurl + "?q=" + city + "&appid=" + apikey + "&units=metric";

        try {
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

        } catch (Exception e) {
            WeatherResponse errorResponse = new WeatherResponse();
            errorResponse.setCity(city);
            errorResponse.setDescription("Error: Unable to fetch weather data");
            errorResponse.setTemperature(0.0);
            errorResponse.setHumidity(0.0);
            errorResponse.setWindSpeed(0.0);

            return errorResponse;
        }
    }


    public JSONObject getForecast(String city) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            String geoUrl = "https://api.openweathermap.org/geo/1.0/direct?q=" + city +
                    "&limit=1&appid=" + apikey;
            JSONArray geoData = new JSONArray(restTemplate.getForObject(geoUrl, String.class));

            if (geoData.isEmpty()) {
                return new JSONObject().put("error", "City not found");
            }

            JSONObject location = geoData.getJSONObject(0);
            double lat = location.getDouble("lat");
            double lon = location.getDouble("lon");

            String forecastUrl = "https://api.openweathermap.org/data/2.5/forecast?lat=" + lat +
                    "&lon=" + lon + "&units=metric&appid=" + apikey;

            JSONObject data = new JSONObject(restTemplate.getForObject(forecastUrl, String.class));
            JSONArray list = data.getJSONArray("list");

            JSONArray forecastArray = new JSONArray();
            for (int i = 0; i < list.length(); i += 8) {
                JSONObject item = list.getJSONObject(i);
                JSONObject main = item.getJSONObject("main");
                JSONObject weather = item.getJSONArray("weather").getJSONObject(0);

                JSONObject obj = new JSONObject();
                obj.put("date", item.getString("dt_txt").split(" ")[0]);
                obj.put("temp", main.getDouble("temp"));
                obj.put("description", weather.getString("description"));
                forecastArray.put(obj);
            }

            return new JSONObject().put("city", city).put("forecast", forecastArray);

        } catch (Exception e) {
            return new JSONObject().put("error", "Failed to fetch forecast");
        }
    }

    public boolean validateCity(String city) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q="
                + city + "&appid=" + apikey;
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getForObject(url, Object.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

//    public WeatherResponse getWeatherByCoords(double lat, double lon){
//        String url = apiurl + "?lat=" + lat + "&lon=" + lon + "&appid=" + apikey + "&units=metric";
//
//        RestTemplate restTemplate = new RestTemplate();
//        String response = restTemplate.getForObject(url, String.class);
//
//        JSONObject json = new JSONObject(response);
//
//        WeatherResponse weather = new WeatherResponse();
//
//        weather.setCity(json.getString("name"));
//        weather.setDescription(json.getJSONArray("weather").getJSONObject(0).getString("description"));
//        weather.setTemperature(json.getJSONObject("main").getDouble("temp"));
//        weather.setHumidity(json.getJSONObject("main").getDouble("humidity"));
//        weather.setWindSpeed(json.getJSONObject("wind").getDouble("speed"));
//
//        return weather;
//    }

//    public WeatherResponse getWeatherByIp(String ip){
//        String geoUrl = "http://ip-api.com/json/" + ip;
//        RestTemplate restTemplate = new RestTemplate();
//        String response = restTemplate.getForObject(geoUrl, String.class);
//
//        JSONObject geoJson = new JSONObject(response);
//
//        double lat = geoJson.getDouble("lat");
//        double lon = geoJson.getDouble("lon");
//
//        return getWeatherByCoords(lat, lon);
//    }

//    public JSONObject getForcastForUser(double lat, double lon) {
//        String url = "https://api.openweathermap.org/data/2.5/forecast"
//                + "?lat=" + lat
//                + "&lon=" + lon
//                + "&appid=" + apikey
//                + "&units=metric";
//
//        RestTemplate restTemplate = new RestTemplate();
//        String response = restTemplate.getForObject(url, String.class);
//
//        return new JSONObject(response);
//    }
