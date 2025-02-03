package com.nursy.nursy.domain.dto.nurse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NurseResponseDto {
    private Long nurseId;
    private String name;
    private Long level;
    private String team;
}
