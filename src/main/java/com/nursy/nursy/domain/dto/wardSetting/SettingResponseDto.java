package com.nursy.nursy.domain.dto.wardSetting;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SettingResponseDto {
    private Long wardId;
    private Long limitStreakCount;
    private Long weekdayDayShift;//평일 day근무 인원
    private Long weekdayEveningShift;
    private Long weekdayNightShift;
    private Long holidayDayShift;
    private Long holidayDayEveningShift;
    private Long holidayDayNightShift;
}
