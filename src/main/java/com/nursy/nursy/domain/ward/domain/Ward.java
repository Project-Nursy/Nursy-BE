package com.nursy.nursy.domain.ward.domain;

import com.nursy.nursy.domain.holiday.domain.Holiday;
import com.nursy.nursy.domain.workSchedule.domain.WorkSchedule;
import com.nursy.nursy.domain.nurse.domain.Nurse;
import com.nursy.nursy.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@AllArgsConstructor
public class Ward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wardId;
    private String hospitalName;
    private String location;
    private String wardName;

    @OneToMany(mappedBy = "ward")
    private List<Nurse> nurses;

    @OneToMany(mappedBy = "ward")
    private List<WorkSchedule> workSchedules;

//    @OneToOne(mappedBy = "ward", cascade = CascadeType.ALL)
//    private WardSetting settings;

    @OneToMany(mappedBy = "ward")
    private List<Holiday> holidays;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;



}
