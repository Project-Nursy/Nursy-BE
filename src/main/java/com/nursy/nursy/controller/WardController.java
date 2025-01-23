package com.nursy.nursy.controller;

import com.nursy.nursy.domain.dto.ward.WardAddRequestDto;
import com.nursy.nursy.domain.dto.ward.WardResponseDto;
import com.nursy.nursy.service.WardService;
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
    @GetMapping("/aa")
    public ResponseEntity<?> aa(){
        return ResponseEntity.ok("aa");
    }

    @GetMapping("/ward-list")
    public ResponseEntity<?> getWardList(Authentication authentication){
        String userId = authentication.getName();
        List<WardResponseDto> wardList = wardService.getWardByUserId(userId);
        return ResponseEntity.ok(wardList);
    }
    @PostMapping("/ward-add")
    public void addWard(Authentication authentication, @RequestBody WardAddRequestDto wardAddRequestDto){

        wardService.saveWard(authentication,wardAddRequestDto);

    }

}
