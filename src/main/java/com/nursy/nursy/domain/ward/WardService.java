package com.nursy.nursy.domain.ward;

import com.nursy.nursy.domain.ward.dto.WardAddRequestDto;
import com.nursy.nursy.domain.ward.dto.WardResponseDto;
import com.nursy.nursy.domain.ward.dto.WardUpdateRequestDto;
import com.nursy.nursy.domain.user.User;
import com.nursy.nursy.domain.user.UserRepository;
import com.nursy.nursy.domain.wardSetting.WardSettingRepository;
import com.nursy.nursy.global.exception.CustomException;
import com.nursy.nursy.global.exception.GlobalErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.nursy.nursy.global.exception.GlobalErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor

public class WardService {
    private final WardRepository wardRepository;
    private final WardSettingRepository wardSettingRepository;
    private final UserRepository userRepository;


    @Transactional
    public Ward saveWard(Authentication authentication, WardAddRequestDto wardAddRequestDto) {
        Optional<User> userOptional = userRepository.findById(authentication.getName());//uuid
        if (userOptional.isPresent()) {
            Ward ward = Ward.builder()
                    .wardName(wardAddRequestDto.getWardName())
                    .hospitalName(wardAddRequestDto.getHospitalName())
                    .location(wardAddRequestDto.getLocation())
                    .user(userOptional.get())
                    .build();
            wardRepository.save(ward);
            return ward;
        }
        else{
            throw new CustomException(GlobalErrorCode.USER_NOT_FOUND);
        }
    }
    @Transactional
    public void deleteWard(Authentication authentication, Long wardId) {
        Optional<User> userOptional = userRepository.findById(authentication.getName());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            //유효성 검증 삭제 권한이 있는지 확인
            boolean isManagingWard = user.getWards().stream()
                    .anyMatch(ward -> ward.getWardId().equals(wardId));//wardId로 user찾기 vs userId로 ward찾기
            if (!isManagingWard) {
                throw new CustomException(GlobalErrorCode.WARD_ACCESS_DENIED);  // 커스텀 예외 처리
            }
            wardRepository.deleteByUserIdAndWardId(user.getId(), wardId);
        }
        else{
            throw new CustomException(GlobalErrorCode.USER_NOT_FOUND);
        }
    }
    @Transactional
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
