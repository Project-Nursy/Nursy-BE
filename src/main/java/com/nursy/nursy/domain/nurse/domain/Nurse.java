package com.nursy.nursy.domain.nurse.domain;

import com.nursy.nursy.domain.conflictRelation.domain.ConflictRelation;
import com.nursy.nursy.domain.dayOff.domain.DayOff;
import com.nursy.nursy.domain.shiftRequest.domain.ShiftRequest;
import com.nursy.nursy.domain.vacation.domain.Vacation;
import com.nursy.nursy.domain.ward.domain.Ward;
import com.nursy.nursy.domain.workSchedule.domain.WorkSchedule;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@AllArgsConstructor
public class Nurse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nurseId;
    @ManyToOne
    @JoinColumn(name = "ward_id")
    private Ward ward;

    private String name;
    private LocalDateTime createdAt;//생성시간
    private Long level;

    @Column(columnDefinition = "varchar(255) default '0'")
    private String team;

    @OneToMany(mappedBy = "nurse")
    private List<WorkSchedule> workSchedules;

    @OneToMany(mappedBy = "nurse")
    private List<ShiftRequest> shiftRequests;

    @OneToMany(mappedBy = "nurse")
    private List<ConflictRelation> conflictRelations;

    @OneToMany(mappedBy = "oppositeNurse")
    private List<ConflictRelation> oppositeConflictRelations;

    @OneToMany(mappedBy = "nurse")
    private List<Vacation> vacations;

    @OneToMany(mappedBy = "nurse")
    private List<DayOff> dayOffs;
//
//    @OneToOne(mappedBy = "nurse")
//    private WorkCount workCount;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
