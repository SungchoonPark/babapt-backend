package com.babpat.server.domain.babpat.service.babpat.impl;

import com.babpat.server.common.enums.CustomResponseStatus;
import com.babpat.server.common.exception.CustomException;
import com.babpat.server.domain.babpat.dto.request.SearchCond;
import com.babpat.server.domain.babpat.dto.response.BabpatInfoRespDto;
import com.babpat.server.domain.babpat.dto.response.PartBabpatId;
import com.babpat.server.domain.babpat.entity.Babpat;
import com.babpat.server.domain.babpat.repository.babpat.BabpatRepository;
import com.babpat.server.domain.babpat.repository.ParticipationRepository;
import com.babpat.server.domain.babpat.service.babpat.BabpatQueryService;
import com.babpat.server.domain.member.repository.MemberRepository;
import com.babpat.server.domain.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BabpatQueryServiceImpl implements BabpatQueryService {
    private final BabpatRepository babpatRepository;

    @Override
    public Page<BabpatInfoRespDto> getBabpatWithPaging(SearchCond searchCond, Pageable pageable) {
        return babpatRepository.getBabpats(searchCond, pageable);
    }

    @Override
    public PartBabpatId getParticipatingBabpats(String authUsername) {
        return babpatRepository.getParticipatingBabpat(authUsername);
    }

    @Override
    public Babpat getBabpatDetail(Long babpatId) {
        return babpatRepository.findById(babpatId)
                .orElseThrow(() -> new CustomException(CustomResponseStatus.BABPAT_NOT_EXIST));
    }
}
