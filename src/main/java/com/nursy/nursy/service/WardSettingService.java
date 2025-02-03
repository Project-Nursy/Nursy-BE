package com.nursy.nursy.service;

import com.nursy.nursy.domain.dto.ward.WardResponseDto;
import com.nursy.nursy.domain.dto.wardSetting.SettingResponseDto;
import com.nursy.nursy.domain.dto.wardSetting.SettingUpdateRequestDto;
import com.nursy.nursy.domain.entity.Ward;
import com.nursy.nursy.domain.entity.WardSetting;
import com.nursy.nursy.repository.WardRepository;
import com.nursy.nursy.repository.WardSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WardSettingService {

    private final WardSettingRepository wardSettingRepository;
    private final WardRepository wardRepository;


    @Transactional
    public void saveWardSetting(Authentication authentication, Ward ward) {//병동생성시 기본 생성 setting값

        if (ward.getUser().getId().equals(authentication.getName())) {
            System.out.println(ward.getWardId());
            WardSetting wardSetting = WardSetting.builder()
//                    .ward(ward)
                    .wardId(ward.getWardId())
                    .weekdayDayShift(3L)
                    .weekdayEveningShift(3L)
                    .weekdayNightShift(3L)
                    .holidayDayShift(3L)
                    .holidayEveningShift(3L)
                    .holidayNightShift(3L)
                    .limitStreakCount(5L)
                    .build();

            wardSettingRepository.save(wardSetting);
        }
    }


    public SettingResponseDto getWardSetting(Authentication authentication, Long wardId) {
        // Ward 조회
        Optional<Ward> wardOptional = wardRepository.findById(wardId);
        if (wardOptional.isPresent()) {
            Ward ward = wardOptional.get();

            // 현재 사용자와 Ward 소유자 확인
            if (ward.getUser().getId().equals(authentication.getName())) {
                // WardSetting 조회
                Optional<WardSetting> wardSettingOptional = wardSettingRepository.findById(wardId);
                if (wardSettingOptional.isPresent()) {
                    WardSetting wardSetting = wardSettingOptional.get();

                    // WardSetting 데이터를 SettingResponseDto로 변환하여 반환
                    return new SettingResponseDto(
                            wardSetting.getWardId(),
                            wardSetting.getLimitStreakCount(),
                            wardSetting.getWeekdayDayShift(),
                            wardSetting.getWeekdayEveningShift(),
                            wardSetting.getWeekdayNightShift(),
                            wardSetting.getHolidayDayShift(),
                            wardSetting.getHolidayEveningShift(),
                            wardSetting.getHolidayNightShift()
                    );
                } else {
                    throw new RuntimeException("WardSetting not found for wardId: " + wardId);
                }
            } else {
                throw new RuntimeException("User is not authorized to access this Ward.");
            }
        } else {
            throw new RuntimeException("Ward not found for wardId: " + wardId);
        }
    }

    @Transactional
    public void updateWardSetting(Authentication authentication, SettingUpdateRequestDto settingUpdateRequestDto) {

        System.out.println(settingUpdateRequestDto);
        // Ward 조회
        Optional<Ward> wardOptional = wardRepository.findById(settingUpdateRequestDto.getWardId());
        if (wardOptional.isPresent()) {
            Ward ward = wardOptional.get();

            // 현재 사용자와 Ward 소유자 확인
            if (!ward.getUser().getId().equals(authentication.getName())) {
                throw new RuntimeException("User is not authorized to update this Ward.");
            }

            // WardSetting 조회
            Optional<WardSetting> wardSettingOptional = wardSettingRepository.findById(settingUpdateRequestDto.getWardId());
            if (wardSettingOptional.isPresent()) {
                WardSetting wardSetting = wardSettingOptional.get();

                // WardSetting 값 업데이트
                wardSetting = WardSetting.builder()
                        .wardId(settingUpdateRequestDto.getWardId())
                        //.ward(ward) // 관계 설정
                        .limitStreakCount(settingUpdateRequestDto.getLimitStreakCount())
                        .weekdayDayShift(settingUpdateRequestDto.getWeekdayDayShift())
                        .weekdayEveningShift(settingUpdateRequestDto.getWeekdayEveningShift())
                        .weekdayNightShift(settingUpdateRequestDto.getWeekdayNightShift())
                        .holidayDayShift(settingUpdateRequestDto.getHolidayDayShift())
                        .holidayEveningShift(settingUpdateRequestDto.getHolidayDayEveningShift())
                        .holidayNightShift(settingUpdateRequestDto.getHolidayDayNightShift())
                        .build();

                // 업데이트된 엔티티 저장
                wardSettingRepository.save(wardSetting);
            } else {
                throw new RuntimeException("WardSetting not found for wardId: " + settingUpdateRequestDto.getWardId());
            }
        } else {
            throw new RuntimeException("Ward not found for wardId: " + settingUpdateRequestDto.getWardId());
        }
    }

    public void removeWardSetting(Authentication authentication, Long wardId) {
        wardSettingRepository.deleteById(wardId);
    }
}
