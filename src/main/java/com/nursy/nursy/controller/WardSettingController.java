package com.nursy.nursy.controller;

import com.nursy.nursy.domain.dto.ward.WardUpdateRequestDto;
import com.nursy.nursy.domain.dto.wardSetting.SettingResponseDto;
import com.nursy.nursy.domain.dto.wardSetting.SettingUpdateRequestDto;
import com.nursy.nursy.service.WardSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/setting")
public class WardSettingController {
    private final WardSettingService wardSettingService;

    @GetMapping("/{wardId}")
    public ResponseEntity<?> getWardSettings(Authentication authentication,@PathVariable Long wardId) {
        SettingResponseDto settingResponseDto =  wardSettingService.getWardSetting(authentication, wardId);
        return ResponseEntity.ok(settingResponseDto);
    }

    @PutMapping("/update")
    public void updateSettings(Authentication authentication, @RequestBody SettingUpdateRequestDto settingUpdateRequestDto) {
        wardSettingService.updateWardSetting(authentication,settingUpdateRequestDto);
    }


}
