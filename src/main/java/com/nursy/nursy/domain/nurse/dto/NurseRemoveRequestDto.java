package com.nursy.nursy.domain.nurse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NurseRemoveRequestDto {
    private Long nurseId;
    private Long wardId;
}
