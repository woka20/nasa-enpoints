package com.woka.nasa.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woka.nasa.entity.AsteroidEntity;

import net.minidev.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class AsteroidServiceTest {

    @Test
    void testFetchDataFromThirdParty() throws Exception {
        // Mock RestTemplate
        RestTemplate restTemplate = mock(RestTemplate.class);
        AsteroidService asteroidService = new AsteroidService(restTemplate);

        // Sample input parameters
        String start = "2015-09-08";
        String end = "2015-09-09";
        int size = 2;

        // Sample JSON response from the third-party API
        String jsonResponse = new String(Files.readAllBytes(Paths.get("src/test/resources/test.json")));
        // ObjectMapper objectMapper = new ObjectMapper();
        // JsonNode jsonNode = objectMapper.readTree(jsonResponse);

        // Mock ResponseEntity
        ResponseEntity<String> responseEntity = new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        when(restTemplate.getForEntity(any(String.class), eq(String.class))).thenReturn(responseEntity);

        // Invoke the service method
        List<AsteroidEntity> result = asteroidService.fetchDataFromThirdParty(start, end, size);

        // Verify the result
        assertEquals(HttpStatus.valueOf(200),responseEntity.getStatusCode());// Assuming your response 200
      
    }
}

