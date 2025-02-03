package com.nursy.nursy.domain.ward.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WardAddRequestDto {
    private String hospitalName;
    private String location;
    private String wardName;
}
