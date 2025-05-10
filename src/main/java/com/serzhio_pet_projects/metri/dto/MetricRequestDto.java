package com.serzhio_pet_projects.metri.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;


public record MetricRequestDto(@NotNull LocalDateTime date, @Min(1) @Max(10) int score, String comment) {}
