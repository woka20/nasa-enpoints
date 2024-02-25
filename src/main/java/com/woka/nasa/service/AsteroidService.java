package com.woka.nasa.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AsteroidService {

    private final String API_URL = "https://api.nasa.gov/neo/rest/v1/feed?start_date=2015-09-07&end_date=2015-09-08&api_key=DEMO_KEY";

    private final RestTemplate restTemplate ;

    public AsteroidService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String fetchDataFromThirdParty() {
        // Make GET request to third-party API
        String response = restTemplate.getForObject(API_URL, String.class);
        return response;
    }
}

