package com.aerocast.backend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "email_preferences")
@Data
public class UserPreference {
    @Id
    private String id;
    private String email;
    private String city;
    private boolean enabled;
}
