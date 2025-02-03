package com.nursy.nursy.repository;

import com.nursy.nursy.domain.entity.WorkCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkCountRepository extends JpaRepository<WorkCount, Long> {
}
