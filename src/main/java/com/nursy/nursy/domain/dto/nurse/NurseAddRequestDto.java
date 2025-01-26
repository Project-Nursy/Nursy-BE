package com.nursy.nursy.domain.dto.nurse;

import com.nursy.nursy.domain.entity.Ward;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class NurseAddRequestDto {
    private Long wardId;
    private String name;
    private Long level;
    private String team;
}
