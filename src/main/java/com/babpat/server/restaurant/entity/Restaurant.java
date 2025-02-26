package com.babpat.server.restaurant.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@Table(name = "restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long id;

    @NotNull
    private String name;

    private String thumbnail;

    @Type(JsonType.class)
    @Column(name = "menus", columnDefinition = "longtext")
    private List<Map<String, String>> menus = new ArrayList<>();

    private String category1;

    private String category2;

    @NotNull
    private boolean diet;

    @NotNull
    private boolean cheap;

    @NotNull
    @Column(precision = 18, scale = 15)
    private BigDecimal latitude;

    @NotNull
    @Column(precision = 18, scale = 15)
    private BigDecimal longitude;

    @NotNull
    private String kakaoLink;

}
