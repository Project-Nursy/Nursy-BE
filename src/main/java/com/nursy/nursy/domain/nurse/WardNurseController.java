package com.nursy.nursy.domain.nurse;

import com.nursy.nursy.domain.nurse.domain.dto.NurseAddRequestDto;
import com.nursy.nursy.domain.nurse.domain.dto.NurseRemoveRequestDto;
import com.nursy.nursy.domain.nurse.domain.dto.NurseResponseDto;
import com.nursy.nursy.domain.nurse.domain.Nurse;
import com.nursy.nursy.domain.workCount.WorkCountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ward-nurse")
public class WardNurseController {

    private final NurseService nurseService;
    private final WorkCountService workCountService;

    @GetMapping("/list/{wardId}")
    public ResponseEntity<?> getWardNurseList(Authentication authentication, @PathVariable Long wardId) {
        List<NurseResponseDto> nurses = nurseService.getAllNursesByWardId(authentication, wardId);
        return ResponseEntity.ok(nurses);
    }

    @PostMapping("/add")
    public void addWardNurse(Authentication authentication, @RequestBody NurseAddRequestDto nurseAddRequestDto) {
        System.out.println(nurseAddRequestDto);
        Nurse nurse = nurseService.saveNurse(authentication, nurseAddRequestDto);
        workCountService.saveWorkCount(authentication, nurse.getNurseId());
    }
    @DeleteMapping("/remove")
    public void removeWardNurse(Authentication authentication, @RequestBody NurseRemoveRequestDto nurseRemoveRequestDto){
        nurseService.deleteNurse(authentication, nurseRemoveRequestDto);
        workCountService.removeWorkCount(authentication, nurseRemoveRequestDto.getNurseId());

    }


}
