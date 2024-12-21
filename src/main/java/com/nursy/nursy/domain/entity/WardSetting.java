package com.nursy.nursy.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@AllArgsConstructor
public class WardSetting {
    @Id
    private Long wardId;
    @OneToOne
    @JoinColumn(name = "ward_id")
    private Ward ward;

    private Long weekdayDayShift;
    private Long holidayDayShift;
    private Long weekdayEveningShift;
    private Long holidayEveningShift;
    private Long weekdayNightShift;
    private Long holidayNightShift;
    private Long limitStreakCount;
}
