package com.babpat.server.member.dto.request;

import com.babpat.server.member.entity.Member;
import com.babpat.server.member.entity.enums.Track;

public record SignupRequestDto(
        NamesDto names,
        String id,
        String password,
        String track
) {
    public record NamesDto(
            String nickname,
            String name
    ) {}

    public Member toEntity() {
        return Member.builder()
                .name(names.name)
                .nickname(names.nickname)
                .username(id)
                .password(password) // Todo : 비밀번호 암호화 진행해야함
                .track(Track.fromString(track))
                .build();
    }
}