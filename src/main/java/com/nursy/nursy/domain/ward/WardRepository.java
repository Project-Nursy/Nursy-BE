package com.nursy.nursy.domain.ward;


import com.nursy.nursy.domain.ward.domain.Ward;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;
import java.util.Optional;

public interface WardRepository extends JpaRepository<Ward,Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Ward save(Ward ward);

    Optional<List<Ward>> findByUserId(String userId);

    void deleteByUserIdAndWardId(String userId, Long wardId);
}
