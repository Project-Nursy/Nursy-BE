package com.nursy.nursy.domain.holiday;

import com.nursy.nursy.domain.ward.Ward;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@AllArgsConstructor
public class Holiday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long holidayId;
    @ManyToOne
    @JoinColumn(name = "ward_id")
    private Ward ward;

    private LocalDateTime date;
}
