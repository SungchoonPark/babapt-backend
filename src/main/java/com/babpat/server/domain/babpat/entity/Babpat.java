package com.babpat.server.domain.babpat.entity;

import com.babpat.server.domain.babpat.entity.enums.BabpatStatus;
import com.babpat.server.domain.babpat.entity.enums.MealSpeed;
import com.babpat.server.common.model.BaseEntity;
import com.babpat.server.domain.member.entity.Member;
import com.babpat.server.domain.restaurant.entity.Restaurant;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDate;
import java.time.LocalTime;

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
    @Column(name = "babpat_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "leader_id")
    @NotNull
    private Member member;

    @ManyToOne
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
    private MealSpeed mealSpeed;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BabpatStatus babpatStatus;
}
