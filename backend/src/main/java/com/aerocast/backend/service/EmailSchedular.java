package com.aerocast.backend.service;

import com.aerocast.backend.Repository.PreferenceRepository;
import com.aerocast.backend.model.UserPreference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmailSchedular {
    @Autowired
    private PreferenceRepository userPreferenceRepository;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 0 7 * * ?")
    public void sendMorningEmails() {
        sendEmails("morning");
    }

    @Scheduled(cron = "0 0 12 * * ?")
    public void sendAfternoonEmails() {
        sendEmails("afternoon");
    }

    @Scheduled(cron = "0 0 18 * * ?")
    public void sendEveningEmails() {
        sendEmails("evening");
    }

    private void sendEmails(String timeOfDay) {
        List<UserPreference> users = userPreferenceRepository.findAll();

        for (UserPreference user : users) {
            if (user.isEnabled()) {
                String city = user.getCity();
                String email = user.getEmail();

                var weather = weatherService.getWeatherByCity(city);

                String subject = "AeroCast Weather Update - " + city + " (" + timeOfDay + ")";
                String body = String.format(
                        "Hello!\n\nHere’s your %s weather update for %s:\n\n" +
                                "Temperature: %.1f°C\n" +
                                "Humidity: %d%%\n" +
                                "Wind Speed: %.1f km/h\n\n" +
                                "Stay updated with AeroCast!",
                        timeOfDay, city,
                        weather.getTemperature(),
                        weather.getHumidity(),
                        weather.getWindSpeed()
                );

                emailService.sendEmail(email, subject, body);
            }
        }
    }
}
