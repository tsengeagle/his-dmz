package com.example.dmzserver;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class OtherController {
    @GetMapping("/hello")
    public ResponseEntity hello() {
        return ResponseEntity.ok(LocalDateTime.now().toString());
    }
}
