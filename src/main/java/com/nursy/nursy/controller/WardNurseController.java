package com.nursy.nursy.controller;

import com.nursy.nursy.domain.dto.nurse.NurseAddRequestDto;
import com.nursy.nursy.domain.dto.nurse.NurseRemoveRequestDto;
import com.nursy.nursy.domain.dto.nurse.NurseResponseDto;
import com.nursy.nursy.domain.entity.Nurse;
import com.nursy.nursy.service.NurseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ward-nurse")
public class WardNurseController {

    private final NurseService nurseService;

    @GetMapping("/list/{wardId}")
    public ResponseEntity<?> getWardNurseList(Authentication authentication, @PathVariable Long wardId) {
        List<NurseResponseDto> nurses = nurseService.getAllNursesByWardId(authentication, wardId);
        return ResponseEntity.ok(nurses);
    }

    @PostMapping("/add")
    public void addWardNurse(Authentication authentication, @RequestBody NurseAddRequestDto nurseAddRequestDto) {
        System.out.println(nurseAddRequestDto);
        nurseService.saveNurse(authentication, nurseAddRequestDto);
    }
    @DeleteMapping("/remove")
    public void removeWardNurse(Authentication authentication, @RequestBody NurseRemoveRequestDto nurseRemoveRequestDto){
        nurseService.deleteNurse(authentication, nurseRemoveRequestDto);

    }


}
