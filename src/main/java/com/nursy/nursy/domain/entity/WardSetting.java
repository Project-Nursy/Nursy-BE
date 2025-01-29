package com.nursy.nursy.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@AllArgsConstructor
public class WardSetting {
    @Id
    private Long wardId;

//    @OneToOne
//    @MapsId // wardId를 Ward의 기본 키로 설정
//    @JoinColumn(name = "ward_id")
//    private Ward ward;

    private Long weekdayDayShift;
    private Long holidayDayShift;
    private Long weekdayEveningShift;
    private Long holidayEveningShift;
    private Long weekdayNightShift;
    private Long holidayNightShift;
    private Long limitStreakCount;
}
