package com.babpat.server.member.dto.response;

import com.babpat.server.member.entity.enums.Track;

public record SignInResponseDto(
        Long id,
        String name,
        String nickname,
        Track track
) {
}
