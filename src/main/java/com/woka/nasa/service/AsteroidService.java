package com.woka.nasa.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.woka.nasa.entity.AsteroidEntity;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class AsteroidService {



    private final RestTemplate restTemplate ;

    public AsteroidService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<AsteroidEntity> fetchDataFromThirdParty(String start, String end, int size)  {
        // Make GET request to third-party API
        String API_URL = String.format("https://api.nasa.gov/neo/rest/v1/feed?start_date=%s&end_date=%s&api_key=DEMO_KEY", start, end);
        List<AsteroidEntity> listAtt= new ArrayList<AsteroidEntity>();
        try{
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(API_URL, String.class);
            String response = responseEntity.getBody();

            if (responseEntity.getStatusCode() != HttpStatusCode.valueOf(200)){
                throw new NullPointerException();
            }
           
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response);

            // Convert JsonNode to JSONObject if needed
            JSONObject jsonObject = new JSONObject(jsonNode.toString());
          
           
            JSONObject near=jsonObject.getJSONObject("near_earth_objects");
            for (String date: near.keySet()){
                
                JSONArray obj= near.getJSONArray(date);
              
                for (int i =0; i < obj.length(); i++){
                    Float minDistance= Float.MAX_VALUE;
                    AsteroidEntity att= new AsteroidEntity();
                // log.info("Parsed JSON: {}", date);
                    att.setDate(date);  
                    att.setId(obj.getJSONObject(i).getString("id"));
                    JSONArray obj2=obj.getJSONObject(i).getJSONArray("close_approach_data");
                    for (int j = 0; j < obj2.length();j++){
                        String dist=obj2.getJSONObject(j).getJSONObject("miss_distance").getString("kilometers");
                        Float check=Float.parseFloat(dist);
                        if (minDistance > check){
                            minDistance = check;
                        }
                    }
                    att.setDistanceToEarth(minDistance);
                    listAtt.add(att);
                };
              
            }
            Collections.sort(listAtt, Comparator.comparingDouble(AsteroidEntity::getDistanceToEarth));
            return listAtt.subList(0, Math.min(size, listAtt.size()));
        }catch(Exception e){
             e.printStackTrace();
             return null;
        }
       

       
    }

    
}

