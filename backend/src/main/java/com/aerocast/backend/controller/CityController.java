package com.aerocast.backend.controller;

import com.aerocast.backend.model.CityModel;
import com.aerocast.backend.service.CityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService){
        this.cityService = cityService;
    }

    @PutMapping("/{city}")
    public ResponseEntity<?> addCity(@PathVariable String city){
        CityModel added = cityService.addCity(city);
        if(added == null){
            return ResponseEntity.badRequest().body("City Already Exist");
        }
        return ResponseEntity.ok(added);
    }

    @GetMapping
    public ResponseEntity<List<CityModel>> getAllCity(){
        return ResponseEntity.ok(cityService.getAllCity());
    }
}
