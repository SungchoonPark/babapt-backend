package com.babpat.server.domain.babpat.entity;

import com.babpat.server.common.model.BaseEntity;
import com.babpat.server.domain.babpat.entity.enums.ParticipationStatus;
import com.babpat.server.domain.member.entity.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@Table(name = "participation")
public class Participation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @NotNull
    private Member member;

    @ManyToOne
    @JoinColumn(name = "babpat_id")
    @NotNull
    private Babpat babpat;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ParticipationStatus participationStatus;
}
