package com.nursy.nursy.service;

import com.nursy.nursy.domain.dto.ward.WardAddRequestDto;
import com.nursy.nursy.domain.dto.ward.WardResponseDto;
import com.nursy.nursy.domain.dto.ward.WardUpdateRequestDto;
import com.nursy.nursy.domain.entity.User;
import com.nursy.nursy.domain.entity.Ward;
import com.nursy.nursy.repository.UserRepository;
import com.nursy.nursy.repository.WardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WardService {
    private final WardRepository wardRepository;
    private final UserRepository userRepository;

    public void saveWard(Authentication authentication,WardAddRequestDto wardAddRequestDto) {
        Optional<User> userOptional = userRepository.findById(authentication.getName());//uuid
        Ward ward = Ward.builder()
                .wardName(wardAddRequestDto.getWardName())
                .hospitalName(wardAddRequestDto.getHospitalName())
                .location(wardAddRequestDto.getLocation())
                .user(userOptional.get())
                .build();
        wardRepository.save(ward);

    }
    public void deleteWard(Authentication authentication, Long wardId) {
        Optional<User> userOptional = userRepository.findById(authentication.getName());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            wardRepository.deleteByUserIdAndWardId(user.getId(), wardId);
        }
    }
    public void updateWard(Authentication authentication, WardUpdateRequestDto wardUpdateRequestDto) {
        Optional<User> userOptional = userRepository.findById(authentication.getName());//uuid
        Ward ward = Ward.builder()
                .wardId(wardUpdateRequestDto.getWardId())
                .wardName(wardUpdateRequestDto.getWardName())
                .hospitalName(wardUpdateRequestDto.getHospitalName())
                .location(wardUpdateRequestDto.getLocation())
                .user(userOptional.get())
                .build();
        wardRepository.save(ward);
    }

    public List<WardResponseDto> getWardByUserId(String userId) {
        Optional<List<Ward>> wardListOptional = wardRepository.findByUserId(userId);

        if (wardListOptional.isPresent()) {
            List<Ward> wardList = wardListOptional.get();

            // Ward 엔티티 리스트를 WardResponseDto 리스트로 변환
            return wardList.stream()
                    .map(ward -> new WardResponseDto(
                            ward.getWardId(),
                            ward.getHospitalName(),
                            ward.getLocation(),
                            ward.getWardName()
                    ))
                    .collect(Collectors.toList());
        } else {
            // 예외나 빈 리스트 반환 가능
            return List.of();
        }
    }
}
