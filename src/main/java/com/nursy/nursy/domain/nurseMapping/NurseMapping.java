package com.nursy.nursy.domain.nurseMapping;

import com.nursy.nursy.domain.nurse.Nurse;
import com.nursy.nursy.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@AllArgsConstructor
public class NurseMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cno")
    private User user;

    @ManyToOne
    @JoinColumn(name = "nurse_id")
    private Nurse nurse;
}
