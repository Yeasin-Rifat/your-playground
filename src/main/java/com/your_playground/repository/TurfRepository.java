package com.your_playground.repository;

import com.your_playground.entity.Turf;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TurfRepository extends JpaRepository<Turf, Long> {
}