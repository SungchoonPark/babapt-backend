package com.babpat.server.domain.member.dto.response;

import com.babpat.server.domain.member.entity.enums.Track;

public record MemberInfoResponse(
        Long userId,
        String name,
        String nickname,
        Track track
) {
}
