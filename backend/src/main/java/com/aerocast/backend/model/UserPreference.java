package com.aerocast.backend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class UserPreference {
    @Id
    private String id;
    private String email;
    private String defaultCity;
    private int intervalHours;
}
