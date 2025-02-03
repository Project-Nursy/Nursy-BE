package com.nursy.nursy.domain.conflictRelation.domain;

import com.nursy.nursy.domain.nurse.domain.Nurse;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@AllArgsConstructor
public class ConflictRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "nurse_id")
    private Nurse nurse;

    @ManyToOne
    @JoinColumn(name = "opposite_id")
    private Nurse oppositeNurse;

    private String reason;
    private LocalDateTime createdAt;
}
