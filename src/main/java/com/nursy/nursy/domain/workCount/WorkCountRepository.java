package com.nursy.nursy.domain.workCount;

import com.nursy.nursy.domain.workCount.domain.WorkCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkCountRepository extends JpaRepository<WorkCount, Long> {
}
