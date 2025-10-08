package com.aerocast.backend.service;

import com.aerocast.backend.Repository.PreferenceRepository;
import com.aerocast.backend.model.UserPreference;
import com.aerocast.backend.model.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailSchedular {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PreferenceRepository userRepo;

    private Map<String, Long> lastSent = new HashMap<>();

    public void checkAndSendEmail(){
        List<UserPreference> users = userRepo.findAll();

        for(UserPreference user : users){
            long now = System.currentTimeMillis();
            long lastTime = lastSent.getOrDefault(user.getEmail(), 0L);
            long hourPassed = (now - lastTime) / (1000*60*60);

            if(hourPassed >= user.getIntervalHours()){
                WeatherResponse weather = weatherService.getWeatherByCity(user.getDefaultCity());
                String body = "Weather in" + weather.getCity() + "\n" +
                                "Temperature : " + weather.getTemperature() + "deg C\n" +
                                "Humidity : " + weather.getHumidity() + "% \n" +
                                "Wind Speed : " + weather.getWindSpeed() + "km/h";

                emailService.sendEmail(user.getEmail(), "Weather Update - " + weather.getCity(), body);
                lastSent.put(user.getEmail(), now);
            }
        }
    }
}
