package com.serzhio_pet_projects.metri.controller;


import com.serzhio_pet_projects.metri.dto.MetricRequestDto;
import com.serzhio_pet_projects.metri.dto.MetricResponseDto;
import com.serzhio_pet_projects.metri.model.MetricModel;
import com.serzhio_pet_projects.metri.service.JwtService;
import com.serzhio_pet_projects.metri.service.MetricService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/metrics")
@RequiredArgsConstructor
public class MetricController {

    private static final int BEARER_PREFIX_LENGTH = 7;

    private final MetricService metricService;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<MetricModel> addMetric(
            @RequestBody @Valid MetricRequestDto request,
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(BEARER_PREFIX_LENGTH);
        String email = jwtService.extractEmailFromJwtToken(token);
        MetricModel metric = metricService.addMetric(request, email);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(metric);
    }

    @GetMapping
    public ResponseEntity<List<MetricResponseDto>> getMetrics(
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(BEARER_PREFIX_LENGTH);
        String email = jwtService.extractEmailFromJwtToken(token);
        return ResponseEntity.ok(metricService.getUserMetrics(email));
    }
}
