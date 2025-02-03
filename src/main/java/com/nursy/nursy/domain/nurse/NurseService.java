package com.nursy.nursy.domain.nurse;

import com.nursy.nursy.domain.nurse.domain.dto.NurseAddRequestDto;
import com.nursy.nursy.domain.nurse.domain.dto.NurseRemoveRequestDto;
import com.nursy.nursy.domain.nurse.domain.dto.NurseResponseDto;
import com.nursy.nursy.domain.nurse.domain.Nurse;
import com.nursy.nursy.domain.user.domain.User;
import com.nursy.nursy.domain.ward.domain.Ward;
import com.nursy.nursy.domain.user.UserRepository;
import com.nursy.nursy.domain.ward.WardService;
import com.nursy.nursy.domain.ward.WardRepository;
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
    private final WardService wardService;
    private final WardRepository wardRepository;

    public List<NurseResponseDto> getAllNursesByWardId(Authentication authentication, Long wardId) {

        String userId = authentication.getName();

       //유효한 유저인지 확인
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 사용자입니다.");
        }

        User user = optionalUser.get();

        // 유저가 관리하는 병동 목록에서 wardId가 존재하는지 확인
        boolean isManagingWard = user.getWards().stream()
                .anyMatch(ward -> ward.getWardId().equals(wardId));

        if (!isManagingWard) {
            throw new IllegalArgumentException("해당 병동에 대한 접근 권한이 없습니다.");
        }
        Optional<List<Nurse>> optionalNurses = nurseRepository.findByWard_WardId(wardId);
        if (optionalNurses.isPresent()) {
            List<Nurse> nurseList = optionalNurses.get();

            // Ward 엔티티 리스트를 WardResponseDto 리스트로 변환
            return nurseList.stream()
                    .map(nurse -> new NurseResponseDto(
                            nurse.getNurseId(),
                            nurse.getName(),
                            nurse.getLevel(),
                            nurse.getTeam()
                    ))
                    .collect(Collectors.toList());
        } else {
            // 예외나 빈 리스트 반환 가능
            return List.of();
        }
    }
    public Nurse saveNurse(Authentication authentication, NurseAddRequestDto nurseAddRequestDto) {
        Ward ward = wardRepository.findById(nurseAddRequestDto.getWardId()).get();
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

        nurseRepository.deleteByNurseIdAndWard_WardId(nurseId,wardId);

    }
}
