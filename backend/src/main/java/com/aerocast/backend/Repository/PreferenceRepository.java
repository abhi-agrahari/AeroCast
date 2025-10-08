package com.aerocast.backend.Repository;

import com.aerocast.backend.model.UserPreference;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PreferenceRepository extends MongoRepository<UserPreference, String> {
}
