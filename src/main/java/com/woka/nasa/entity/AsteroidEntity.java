package com.woka.nasa.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AsteroidEntity {

    private String asteroidId;
    private String date;
    private Float distanceToEarthInKm;    
}
