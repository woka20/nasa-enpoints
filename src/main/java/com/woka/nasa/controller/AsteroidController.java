package com.woka.nasa.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.woka.nasa.entity.AsteroidEntity;
import com.woka.nasa.service.AsteroidService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AsteroidController {

    private AsteroidService asteroidService;

    public AsteroidController(AsteroidService asteroidService) {
        this.asteroidService = asteroidService;
    }

    @GetMapping("/data-nasa-asteroid")
    public ResponseEntity getDataFromThirdParty( @RequestParam(required = true) String startDate,  @RequestParam(required = true) String endDate ) {
        String message="";
         LocalDate date1 = LocalDate.parse(startDate);
        LocalDate date2 = LocalDate.parse(endDate);
        if (ChronoUnit.DAYS.between(date1, date2)>7){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Max Data 7 days differences");

        }
        if ( date1.compareTo(date2)>0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("End Date cannot be lower than  Start Date");
        }
        List<AsteroidEntity> result=asteroidService.fetchDataFromThirdParty(startDate, endDate);
     
    
        if (result ==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }else{
            return ResponseEntity.ok(result);
        }
    }
}