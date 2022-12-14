package com.example.dmzserver;

import com.asus.healthhub.asusutils.ASUSDecrypt;
import com.example.dmzserver.healthhub.model.UploadData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class HealthHubController {

    private final Logger                  logger       = LoggerFactory.getLogger(this.getClass());
    private final HashMap<String, String> vitalSignTypeMap;
    private final RestTemplate restTemplate = new RestTemplate();
    private final String       nisUrl       = "http://10.2.105.117:8180/NisServer/api/handover/healthHubUploadData";

    public HealthHubController() {
        vitalSignTypeMap = new HashMap<>();
        vitalSignTypeMap.put("BP", "血壓");
        vitalSignTypeMap.put("BS", "血糖");
        vitalSignTypeMap.put("OX", "血氧");
        vitalSignTypeMap.put("TP", "體溫");
        vitalSignTypeMap.put("RR", "呼吸");
        vitalSignTypeMap.put("PAIN", "疼痛");
    }

    @PostMapping("/FS/uploaddata")
    public ResponseEntity healthHubUploadData(@RequestBody Map inputData) {
        UUID uuid = UUID.randomUUID();
        logger.info("receive input data: {}, trace id: {}", inputData, uuid.toString());

        String decoded = ASUSDecrypt.DecrypeData((String) inputData.get("data"));
        logger.info("decoded data: {}", decoded);

        UploadData data = null;
        try {
            data = new ObjectMapper().readValue(decoded, UploadData.class);
        } catch (JsonProcessingException e) {
            logger.error("parse json fail: {}", e.getMessage());
            return ResponseEntity.badRequest().body("parse json fail: " + e.getMessage());
        }

        if (!vitalSignTypeMap.containsKey(data.vital_sign_type.toUpperCase())) {
            logger.warn("vital_sign_type incorrect: {}", data.vital_sign_type);
            return ResponseEntity.badRequest().body("vital_sign_type incorrect: " + data.vital_sign_type);
        }

        logger.info("收到正確的資料: {}", data.toString());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("request-id", uuid.toString());

        HttpEntity<Map>        requestEntity  = new HttpEntity<>(inputData, httpHeaders);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(nisUrl, requestEntity, String.class);
            logger.info("call nis status: {}, response: {}, trace id: {}", responseEntity.getStatusCode(), responseEntity.getBody(), uuid.toString());
        } catch (RestClientException e) {
            logger.error("trace id: {}, call nis fail: {}", uuid.toString(), e.getMessage());
            return ResponseEntity.badRequest().body("call nis fail, trace id: " + uuid.toString());
        }

        return responseEntity;
    }

    @PostMapping("/FS/demo")
    public ResponseEntity demo(@RequestBody String inputData) {
        logger.info("receive input data: {}", inputData);
//        String decoded = ASUSDecrypt.DecrypeData((String) inputData.get("data"));
        String decoded = inputData;
        logger.info("decoded data: {}", decoded);

        UploadData data = null;
        try {
            data = new ObjectMapper().readValue(decoded, UploadData.class);
        } catch (JsonProcessingException e) {
            logger.error("parse json fail: {}", e.getMessage());
            return ResponseEntity.badRequest().body("parse json fail: " + e.getMessage());
        }

        if (!vitalSignTypeMap.containsKey(data.vital_sign_type.toUpperCase())) {
            logger.warn("vital_sign_type incorrect: {}", data.vital_sign_type);
            return ResponseEntity.badRequest().body("vital_sign_type incorrect: " + data.vital_sign_type);
        }

        logger.info("收到正確的資料: {}", data.toString());

        return ResponseEntity.ok("done");
    }
}
