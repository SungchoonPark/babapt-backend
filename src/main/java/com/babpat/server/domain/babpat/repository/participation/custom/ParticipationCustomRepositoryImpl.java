package com.babpat.server.domain.babpat.repository.participation.custom;

import com.babpat.server.domain.member.entity.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.babpat.server.domain.babpat.entity.QParticipation.participation;
import static com.babpat.server.domain.member.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class ParticipationCustomRepositoryImpl implements ParticipationCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Member> getParticipationMembersWithoutRequester(Long babpatId, Long requesterId) {
        return jpaQueryFactory
                .select(participation.member)
                .from(participation)
                .where(
                        participation.babpat.id.eq(babpatId),
                        participation.member.id.ne(requesterId)
                )
                .join(participation.member, member)
                .fetch();
    }
}
