package com.nursy.nursy.domain.nurse;

import com.nursy.nursy.domain.conflictRelation.ConflictRelation;
import com.nursy.nursy.domain.dayOff.DayOff;
import com.nursy.nursy.domain.shiftRequest.ShiftRequest;
import com.nursy.nursy.domain.vacation.Vacation;
import com.nursy.nursy.domain.ward.Ward;
import com.nursy.nursy.domain.workSchedule.WorkSchedule;
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
    @ManyToOne(fetch = FetchType.LAZY)
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
