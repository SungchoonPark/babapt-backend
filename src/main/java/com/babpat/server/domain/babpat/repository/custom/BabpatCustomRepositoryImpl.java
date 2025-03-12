package com.babpat.server.domain.babpat.repository.custom;

import com.babpat.server.domain.babpat.dto.request.SearchCond;
import com.babpat.server.domain.babpat.dto.response.BabpatInfoRespDto;
import com.babpat.server.domain.babpat.entity.QBabpat;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BabpatCustomRepositoryImpl implements BabpatCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<BabpatInfoRespDto> getBabpats(SearchCond searchCond, Pageable pageable) {
        return null;
    }
}
