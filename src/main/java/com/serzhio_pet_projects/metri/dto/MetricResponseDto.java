package com.serzhio_pet_projects.metri.dto;

import java.time.LocalDateTime;


public record MetricResponseDto(LocalDateTime date, int score, String comment) {}
