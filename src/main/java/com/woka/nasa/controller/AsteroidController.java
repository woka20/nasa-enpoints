package com.woka.nasa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woka.nasa.service.AsteroidService;

@RestController
public class AsteroidController {

    private AsteroidService asteroidService;

    public AsteroidController(AsteroidService asteroidService) {
        this.asteroidService = asteroidService;
    }

    @GetMapping("/data-nasa-asteroid")
    public String getDataFromThirdParty() {
        return asteroidService.fetchDataFromThirdParty();
    }
}