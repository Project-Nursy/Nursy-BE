package com.nursy.nursy.domain.wardSetting;

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

    private Long weekdayDayShift;
    private Long holidayDayShift;
    private Long weekdayEveningShift;
    private Long holidayEveningShift;
    private Long weekdayNightShift;
    private Long holidayNightShift;
    private Long limitStreakCount;
}
