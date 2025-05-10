package com.serzhio_pet_projects.metri.repository;

import com.serzhio_pet_projects.metri.model.MetricModel;
import com.serzhio_pet_projects.metri.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MetricRepository extends JpaRepository<MetricModel, Long> {
    List<MetricModel> findAllMetricsByUser(UserModel user);
}
