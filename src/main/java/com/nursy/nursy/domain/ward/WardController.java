package com.nursy.nursy.domain.ward;

import com.nursy.nursy.domain.ward.domain.Ward;
import com.nursy.nursy.domain.ward.domain.dto.WardAddRequestDto;
import com.nursy.nursy.domain.ward.domain.dto.WardResponseDto;
import com.nursy.nursy.domain.ward.domain.dto.WardUpdateRequestDto;
import com.nursy.nursy.domain.wardSetting.WardSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ward")
public class WardController {

    private final WardService wardService;
    private final WardSettingService wardSettingService;

    @GetMapping("/list")
    public ResponseEntity<?> getWardList(Authentication authentication){
        String userId = authentication.getName();
        List<WardResponseDto> wardList = wardService.getWardByUserId(userId);
        return ResponseEntity.ok(wardList);
    }
    @PostMapping("/add")
    public void addWard(Authentication authentication, @RequestBody WardAddRequestDto wardAddRequestDto){
        Ward ward = wardService.saveWard(authentication,wardAddRequestDto);
        wardSettingService.saveWardSetting(authentication,ward);
    }
    @PatchMapping("/update")
    public void updateWard(Authentication authentication, @RequestBody WardUpdateRequestDto wardUpdateRequestDto){
        wardService.updateWard(authentication,wardUpdateRequestDto);
    }

    @DeleteMapping("/delete/{wardId}")
    public void deleteWard(Authentication authentication, @PathVariable Long wardId){
        wardService.deleteWard(authentication,wardId);
        wardSettingService.removeWardSetting(authentication,wardId);
    }

}
