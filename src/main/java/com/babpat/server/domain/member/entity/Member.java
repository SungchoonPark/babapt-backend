package com.babpat.server.domain.member.entity;

import com.babpat.server.common.enums.BaseStatus;
import com.babpat.server.common.model.BaseEntity;
import com.babpat.server.domain.member.entity.enums.RoleType;
import com.babpat.server.domain.member.entity.enums.Track;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@Table(name = "member", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name", "nickname", "track"})
})
public class Member extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private String name;

  @NotNull
  private String nickname;

  @NotNull
  private String username;

  @NotNull
  private String password;

  @NotNull
  @Enumerated(EnumType.STRING)
  private Track track;

  @Enumerated(EnumType.STRING)
  @ColumnDefault("'ACTIVATE'")
  private BaseStatus baseStatus;

  @Enumerated(EnumType.STRING)
  @Column(name = "role")
  private RoleType role;

  public boolean isSameUsername(String username) {
    return this.username.equals(username);
  }
}
