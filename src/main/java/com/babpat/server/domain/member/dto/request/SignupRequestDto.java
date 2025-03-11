package com.babpat.server.domain.member.dto.request;

import com.babpat.server.domain.member.entity.Member;
import com.babpat.server.domain.member.entity.enums.Track;
import com.babpat.server.util.PasswordUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record SignupRequestDto(
        @Valid NamesDto names,
        @NotBlank String id,
        @NotBlank String password,
        @NotBlank String track
) {
    public record NamesDto(
            @NotBlank String nickname,
            @NotBlank String name
    ) {
    }

    public Member toEntity() {
        return Member.builder()
                .name(names.name)
                .nickname(names.nickname)
                .username(id)
                .password(PasswordUtil.hashPassword(password))
                .track(Track.fromString(track))
                .build();
    }
}