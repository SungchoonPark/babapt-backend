package com.babpat.server.domain.member.repository;

import com.babpat.server.domain.member.dto.response.MemberInfoResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.babpat.server.domain.member.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public MemberInfoResponse getMemberInfoByUsername(String username) {
        return jpaQueryFactory.select(
                Projections.constructor(
                        MemberInfoResponse.class,
                        member.id,
                        member.name,
                        member.nickname,
                        member.track
                ))
                .from(member)
                .where(member.username.eq(username))
                .fetchOne();
    }

}
