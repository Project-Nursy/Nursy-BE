package com.nursy.nursy.domain.workSchedule;

import com.nursy.nursy.domain.nurse.Nurse;
import com.nursy.nursy.domain.ward.Ward;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@AllArgsConstructor
public class WorkSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ward_id")
    private Ward ward;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nurse_id")
    private Nurse nurse;

    private LocalDateTime date;
    private String duty;

    @Column(columnDefinition = "varchar(255) default '0'")
    private String team;

}
