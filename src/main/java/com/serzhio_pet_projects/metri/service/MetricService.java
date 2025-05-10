package com.serzhio_pet_projects.metri.service;

import com.serzhio_pet_projects.metri.dto.MetricRequestDto;
import com.serzhio_pet_projects.metri.dto.MetricResponseDto;
import com.serzhio_pet_projects.metri.model.MetricModel;
import com.serzhio_pet_projects.metri.model.UserModel;
import com.serzhio_pet_projects.metri.repository.MetricRepository;
import com.serzhio_pet_projects.metri.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MetricService {

    private final MetricRepository metricRepository;
    private final UserRepository userRepository;

    public MetricModel addMetric(MetricRequestDto metricRequestDto, String userEmail) {
        UserModel user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        MetricModel metric = new MetricModel();
        metric.setUser(user);
        metric.setDate(metricRequestDto.date());
        metric.setScore(metricRequestDto.score());
        metric.setComment(metricRequestDto.comment());

        return metricRepository.save(metric);
    }

    public List<MetricResponseDto> getUserMetrics(String userEmail) {
        UserModel user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return  metricRepository.findAllMetricsByUser(user).stream()
                .map(m -> new MetricResponseDto(m.getDate(), m.getScore(), m.getComment()))
                .toList();
    }
}
