package com.babpat.server.domain.member.repository;

import com.babpat.server.domain.member.entity.Member;
import com.babpat.server.domain.member.entity.enums.Track;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByNicknameAndNameAndTrack(String nickname, String name, Track track);

    Optional<Member> findByUsername(String username);

    boolean existsByUsername(String username);
}
