package com.example.dmzserver;

import com.asus.healthhub.asusutils.ASUSDecrypt;
import com.example.dmzserver.healthhub.model.UploadData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HealthHubController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final HashMap<String, String> vitalSignTypeMap;

    public HealthHubController() {
        vitalSignTypeMap = new HashMap();
        vitalSignTypeMap.put("BP", "血壓");
        vitalSignTypeMap.put("BS", "血糖");
        vitalSignTypeMap.put("OX", "血氧");
        vitalSignTypeMap.put("TP", "體溫");
        vitalSignTypeMap.put("RR", "呼吸");
        vitalSignTypeMap.put("PAIN", "疼痛");
    }

    @PostMapping("/FS/uploaddata")
    public ResponseEntity healthHubUploadData(@RequestBody Map inputData) {
        logger.info("receive input data: {}", inputData);
        String decoded = ASUSDecrypt.DecrypeData((String) inputData.get("data"));
        logger.info("decoded data: {}", decoded);

        UploadData data = null;
        try {
            data = new ObjectMapper().readValue(decoded, UploadData.class);
        } catch (JsonProcessingException e) {
            logger.error("parse json fail: {}", e.getMessage());
            return ResponseEntity.badRequest().body("parse json fail: " + e.getMessage());
        }

        if (!vitalSignTypeMap.containsKey(data.vital_sign_type)) {
            return ResponseEntity.badRequest().body("vital_sign_type incorrect");
        }

        logger.info("收到正確的資料: {}", data.toString());

        return ResponseEntity.ok("done");
    }

}
