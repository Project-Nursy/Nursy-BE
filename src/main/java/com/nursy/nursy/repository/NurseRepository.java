package com.nursy.nursy.repository;

import com.nursy.nursy.domain.entity.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NurseRepository extends JpaRepository<Nurse, Long> {

    Optional<List<Nurse>> findByWard_WardId(Long wardId);

    void deleteByNurseIdAndWard_WardId(Long nurseId, Long wardWardId);
}
