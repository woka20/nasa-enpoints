package com.woka.nasa.controller;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.woka.nasa.entity.AsteroidEntity;
import com.woka.nasa.service.AsteroidService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class AsteroidControllerTest {

    @Test
    void testGetDataFromThirdParty() {
        // Mock dependencies
        AsteroidService asteroidService = mock(AsteroidService.class);
        AsteroidController asteroidController = new AsteroidController(asteroidService);

        // Sample input parameters
        String startDate = "2023-01-01";
        String endDate = "2023-01-07";
        int size = 10;

        // Mock service response
        List<AsteroidEntity> mockResult = Collections.singletonList(new AsteroidEntity(/* initialize fields */));
        when(asteroidService.fetchDataFromThirdParty(startDate, endDate, size)).thenReturn(mockResult);

        // Invoke the controller method
        ResponseEntity response = asteroidController.getDataFromThirdParty(startDate, endDate, size);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResult, response.getBody());
    }
}
