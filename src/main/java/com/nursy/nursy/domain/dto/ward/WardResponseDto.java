package com.nursy.nursy.domain.dto.ward;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class WardResponseDto {
    private Long wardId;
    private String hospitalName;
    private String location;
    private String wardName;
}
