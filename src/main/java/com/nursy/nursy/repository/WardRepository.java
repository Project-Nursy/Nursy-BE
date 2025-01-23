package com.nursy.nursy.repository;


import com.nursy.nursy.domain.entity.Ward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WardRepository extends JpaRepository<Ward,Long> {
    Optional<List<Ward>> findByUserId(String userId);
}
