package com.aerocast.backend.service;

import com.aerocast.backend.Repository.CityRepository;
import com.aerocast.backend.model.CityModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    private final CityRepository cityRepo;

    public CityService(CityRepository cityRepo){
        this.cityRepo = cityRepo;
    }

    public CityModel addCity(String cityName) {
        if (!cityRepo.existsByCityName(cityName)) {
            return cityRepo.save(new CityModel(null, cityName));
        }
        return null;
    }

    public List<CityModel> getAllCity(){
        return cityRepo.findAll();
    }
}
