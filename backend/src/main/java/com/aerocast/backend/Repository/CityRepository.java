package com.aerocast.backend.Repository;

import com.aerocast.backend.model.CityModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CityRepository extends MongoRepository<CityModel, String> {
    boolean existsByCityName(String cityName);
    void deleteByCityName(String cityName);
}
