package com.nursy.nursy.domain.conflictRelation;

import com.nursy.nursy.domain.nurse.Nurse;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nurse_id")
    private Nurse nurse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "opposite_id")
    private Nurse oppositeNurse;

    private String reason;
    private LocalDateTime createdAt;
}
