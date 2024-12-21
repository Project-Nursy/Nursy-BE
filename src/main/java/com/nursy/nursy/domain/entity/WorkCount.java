package com.nursy.nursy.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@AllArgsConstructor
public class WorkCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nurseId;

    @OneToOne
    @JoinColumn(name = "nurse_id")
    private Nurse nurse;

    private LocalDateTime referenceDate;//기준날짜
    private LocalDateTime updatedDate;// 업데이트 날짜
    private Long dayCount;      //상대daycount
    private Long eveningCount;  //상대eveningcount
    private Long nightCount;    //상대 nightcount
}
