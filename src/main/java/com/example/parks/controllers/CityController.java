package com.example.parks.controllers;

import com.example.parks.models.City;
import com.example.parks.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/cities")
public class CityController {
    private  CityRepository cityRepository;
    public CityController(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @GetMapping("/")
    public ResponseEntity<List<City>> getAllCities() {
        try {
            return ResponseEntity.ok(cityRepository.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> getCity(@PathVariable long id) {
        Optional<City> city = cityRepository.findById(id);
        if (city.isPresent()) {
            return ResponseEntity.ok(city.get());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public ResponseEntity<City> createCity(@RequestBody City city) {
        try {
            City newCity = cityRepository.save(city);
            return ResponseEntity.status(HttpStatus.CREATED).body(newCity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<City> updateCity(@PathVariable long id, @RequestBody City city) {
        try {
            Optional<City> updateCity = cityRepository.findById(id);
            if (updateCity.isPresent()) {
                City foundCity = updateCity.get();
                foundCity.setName(city.getName());
                foundCity.setParks(city.getParks());
                cityRepository.save(foundCity);
                return ResponseEntity.status(HttpStatus.OK).body(foundCity);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCity(@PathVariable long id) {
        Optional<City> city = cityRepository.findById(id);
        try {
            if (city.isPresent()) {
                City foundCity = city.get();
                cityRepository.delete(foundCity);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/name/{name}")
    public ResponseEntity<HttpStatus> deleteCityByName(@PathVariable String name) {
        Optional<City> city = cityRepository.findByName(name);
        try {
            if (city.isPresent()) {
                City foundCity = city.get();
                cityRepository.delete(foundCity);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping("/")
    public ResponseEntity<HttpStatus> deleteAllCities() {
        try {
            cityRepository.deleteAll();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
