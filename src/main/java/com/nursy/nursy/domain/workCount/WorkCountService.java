package com.nursy.nursy.domain.workCount;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkCountService {
    private final WorkCountRepository workCountRepository;

    @Transactional
    public void saveWorkCount(Authentication authentication, Long nurseId) {
        WorkCount workCount = WorkCount.builder()
                .nurseId(nurseId)
                .dayCount(0L)
                .eveningCount(0L)
                .nightCount(0L)
                .build();
        workCountRepository.save(workCount);
    }
    @Transactional
    public void removeWorkCount(Authentication authentication, Long nurseId) {
        workCountRepository.deleteById(nurseId);
    }

}
