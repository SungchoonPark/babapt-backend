package com.babpat.server.domain.settlement.entity;

import com.babpat.server.common.model.BaseEntity;
import com.babpat.server.domain.babpat.entity.Babpat;
import com.babpat.server.domain.member.entity.Member;
import com.babpat.server.domain.settlement.entity.enums.SettlementStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "settlement")
public class Settlement extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "leader_id")
    @NotNull
    private Member member;

    @ManyToOne
    @JoinColumn(name = "babpat_id")
    @NotNull
    private Babpat babpat;

    @NotNull
    private Integer memberCount;

    @NotNull
    private Integer totalPrice;

    @NotNull
    private Integer perPrice;

    @NotNull
    private String accountNumber;

    @NotNull
    private String bankName;

    @NotNull
    private String accountHolder;

    @Enumerated(EnumType.STRING)
    @NotNull
    private SettlementStatus settlementStatus;

    public void completeSettlement() {
        this.settlementStatus = SettlementStatus.COMPLETED;
    }
}
