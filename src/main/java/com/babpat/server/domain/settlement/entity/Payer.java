package com.babpat.server.domain.settlement.entity;

import com.babpat.server.common.model.BaseEntity;
import com.babpat.server.domain.member.entity.Member;
import com.babpat.server.domain.settlement.entity.enums.PayerStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  @NotNull
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
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
