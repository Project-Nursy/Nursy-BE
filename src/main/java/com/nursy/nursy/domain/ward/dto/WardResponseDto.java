package com.nursy.nursy.domain.ward.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WardResponseDto {
    private Long wardId;
    private String hospitalName;
    private String location;
    private String wardName;
}
