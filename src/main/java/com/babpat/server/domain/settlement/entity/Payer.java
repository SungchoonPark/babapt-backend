package com.babpat.server.domain.settlement.entity;

import com.babpat.server.common.model.BaseEntity;
import com.babpat.server.domain.member.entity.Member;
import com.babpat.server.domain.settlement.entity.enums.PayerStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "payer")
public class Payer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @NotNull
    private Member member;

    @ManyToOne
    @JoinColumn(name = "settlement_id")
    @NotNull
    private Settlement settlement;

    @Enumerated(EnumType.STRING)
    @NotNull
    private PayerStatus payerStatus;

    public void payComplete() {
        this.payerStatus = PayerStatus.PAID;
    }
}
