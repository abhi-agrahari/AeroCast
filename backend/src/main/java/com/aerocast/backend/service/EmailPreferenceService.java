package com.aerocast.backend.service;

import com.aerocast.backend.Repository.PreferenceRepository;
import com.aerocast.backend.model.UserPreference;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmailPreferenceService {
    private final PreferenceRepository repository;

    public EmailPreferenceService(PreferenceRepository repository) {
        this.repository = repository;
    }

    public UserPreference saveOrUpdate(UserPreference pref) {
        Optional<UserPreference> existing = repository.findByEmail(pref.getEmail());
        if (existing.isPresent()) {
            UserPreference p = existing.get();
            p.setCity(pref.getCity());
            p.setEnabled(pref.isEnabled());
            return repository.save(p);
        } else {
            return repository.save(pref);
        }
    }

    public Optional<UserPreference> getByEmail(String email) {
        return repository.findByEmail(email);
    }
}
