package com.nursy.nursy.domain.nurse;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NurseRepository extends JpaRepository<Nurse, Long> {

    Optional<List<Nurse>> findByWard_WardId(Long wardId);

    long deleteByNurseIdAndWard_WardId(Long nurseId, Long wardWardId);
}
