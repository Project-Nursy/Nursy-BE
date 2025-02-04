package com.nursy.nursy.domain.nurse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NurseAddRequestDto {
    private Long wardId;
    private String name;
    private Long level;
    private String team;
}
