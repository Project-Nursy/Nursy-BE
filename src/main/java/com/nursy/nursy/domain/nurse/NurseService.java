package com.nursy.nursy.domain.nurse;

import com.nursy.nursy.domain.nurse.dto.NurseAddRequestDto;
import com.nursy.nursy.domain.nurse.dto.NurseRemoveRequestDto;
import com.nursy.nursy.domain.nurse.dto.NurseResponseDto;
import com.nursy.nursy.domain.user.User;
import com.nursy.nursy.domain.ward.Ward;
import com.nursy.nursy.domain.user.UserRepository;
import com.nursy.nursy.domain.ward.WardRepository;
import com.nursy.nursy.global.exception.CustomException;
import com.nursy.nursy.global.exception.GlobalErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NurseService {
    private final NurseRepository nurseRepository;
    private final UserRepository userRepository;
    private final WardRepository wardRepository;

    public List<NurseResponseDto> getAllNursesByWardId(Authentication authentication, Long wardId) {
        String userId = authentication.getName();

        // 유효한 유저인지 확인
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new CustomException(GlobalErrorCode.USER_NOT_FOUND);  // 커스텀 예외 처리
        }

        User user = optionalUser.get();

        // 유저가 관리하는 병동 목록에서 wardId가 존재하는지 확인
        boolean isManagingWard = user.getWards().stream()
                .anyMatch(ward -> ward.getWardId().equals(wardId));

        if (!isManagingWard) {
            throw new CustomException(GlobalErrorCode.WARD_ACCESS_DENIED);  // 커스텀 예외 처리
        }

        Optional<List<Nurse>> optionalNurses = nurseRepository.findByWard_WardId(wardId);
        if (optionalNurses.isPresent()) {
            List<Nurse> nurseList = optionalNurses.get();

            // Nurse 엔티티 리스트를 NurseResponseDto 리스트로 변환
            return nurseList.stream()
                    .map(nurse -> new NurseResponseDto(
                            nurse.getNurseId(),
                            nurse.getName(),
                            nurse.getLevel(),
                            nurse.getTeam()
                    ))
                    .collect(Collectors.toList());
        } else {
            // 예외 처리하거나 빈 리스트 반환
            throw new CustomException(GlobalErrorCode.NURSE_NOT_FOUND);  // 간호사 없음 예외 처리
        }
    }

    public Nurse saveNurse(Authentication authentication, NurseAddRequestDto nurseAddRequestDto) {
        Ward ward = wardRepository.findById(nurseAddRequestDto.getWardId())
                .orElseThrow(() -> new CustomException(GlobalErrorCode.WARD_ACCESS_DENIED));  // 예외 처리

        Nurse nurse = Nurse.builder()
                .name(nurseAddRequestDto.getName())
                .level(nurseAddRequestDto.getLevel())
                .team(nurseAddRequestDto.getTeam())
                .ward(ward)
                .build();

        return nurseRepository.save(nurse);
    }

    public void deleteNurse(Authentication authentication, NurseRemoveRequestDto nurseRemoveRequestDto) {
        Long nurseId = nurseRemoveRequestDto.getNurseId();
        Long wardId = nurseRemoveRequestDto.getWardId();

        long rowsDeleted = nurseRepository.deleteByNurseIdAndWard_WardId(nurseId, wardId);
        if (rowsDeleted == 0) {
            throw new CustomException(GlobalErrorCode.NURSE_NOT_FOUND);  // 삭제된 간호사가 없을 때 예외 처리
        }
    }
}
