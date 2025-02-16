package com.nursy.nursy.domain.wardSetting;

import com.nursy.nursy.domain.wardSetting.dto.SettingResponseDto;
import com.nursy.nursy.domain.wardSetting.dto.SettingUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
