package com.babpat.server.domain.babpat.entity;

import com.babpat.server.common.model.BaseEntity;
import com.babpat.server.domain.babpat.entity.enums.BabpatStatus;
import com.babpat.server.domain.babpat.entity.enums.MealSpeed;
import com.babpat.server.domain.babpat.entity.enums.PatType;
import com.babpat.server.domain.member.entity.Member;
import com.babpat.server.domain.restaurant.entity.Restaurant;
import jakarta.persistence.Column;
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
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@Table(name = "babpat")
public class Babpat extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "leader_id")
  @NotNull
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "restaurant_id")
  @NotNull
  private Restaurant restaurant;

  @NotNull
  private String comment;

  @NotNull
  private Integer headCount;

  @NotNull
  private LocalDate patDate;

  @NotNull
  private LocalTime patTime;

  @Enumerated(EnumType.STRING)
  @NotNull
  private MealSpeed mealSpeed;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  @NotNull
  private BabpatStatus babpatStatus;

  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  @NotNull
  private PatType patType;

  @NotNull
  private String meetPlace;

  public void updateFull() {
    this.babpatStatus = BabpatStatus.FULL;
  }

  public void updateOngoing() {
    this.babpatStatus = BabpatStatus.ONGOING;
  }

  public void updateDelete() {
    this.babpatStatus = BabpatStatus.DELETED;
  }

  public void updateFinish() {
    this.babpatStatus = BabpatStatus.FINISHED;
  }

  public boolean isValidMember(String username) {
    return this.member.isSameUsername(username);
  }

}
