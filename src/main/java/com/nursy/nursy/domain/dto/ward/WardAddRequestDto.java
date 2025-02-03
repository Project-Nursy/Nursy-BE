package com.nursy.nursy.domain.dto.ward;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WardAddRequestDto {
    private String hospitalName;
    private String location;
    private String wardName;
}
