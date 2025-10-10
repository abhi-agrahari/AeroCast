package com.aerocast.backend.Repository;

import com.aerocast.backend.model.UserPreference;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PreferenceRepository extends MongoRepository<UserPreference, String> {
    Optional<UserPreference> findByEmail(String email);
}
