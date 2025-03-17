package com.babpat.server.domain.settlement.service.impl;

import com.babpat.server.common.enums.CustomResponseStatus;
import com.babpat.server.common.exception.CustomException;
import com.babpat.server.domain.member.entity.Member;
import com.babpat.server.domain.member.repository.MemberRepository;
import com.babpat.server.domain.settlement.dto.response.AlarmResponse;
import com.babpat.server.domain.settlement.dto.response.SettlementInfo;
import com.babpat.server.domain.settlement.repository.PayerRepository;
import com.babpat.server.domain.settlement.repository.SettlementRepository;
import com.babpat.server.domain.settlement.service.SettlementQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SettlementQueryServiceImpl implements SettlementQueryService {
    private final SettlementRepository settlementRepository;
    private final PayerRepository payerRepository;
    private final MemberRepository memberRepository;

    @Override
    public Page<SettlementInfo> getSettlementStates(String authUsername, Pageable pageable) {
        // Todo : 기능개발
        Member validMember = memberRepository.findByUsername(authUsername)
                .orElseThrow(() -> new CustomException(CustomResponseStatus.MEMBER_NOT_EXIST));

        return settlementRepository.getSettlementStates(validMember.getId(), pageable);
    }

    @Override
    public List<AlarmResponse> getSettlementAlarms(String authUsername) {
        return payerRepository.getRecentAlarms(authUsername);
    }
}
