package com.nursy.nursy.domain.vacation;

import com.nursy.nursy.domain.nurse.Nurse;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@AllArgsConstructor
public class Vacation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "nurse_id")
    private Nurse nurse;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
