package com.babpat.server.domain.settlement.service.impl;

import com.babpat.server.common.enums.CustomResponseStatus;
import com.babpat.server.common.exception.CustomException;
import com.babpat.server.domain.babpat.entity.Babpat;
import com.babpat.server.domain.babpat.repository.babpat.BabpatRepository;
import com.babpat.server.domain.babpat.repository.ParticipationRepository;
import com.babpat.server.domain.member.entity.Member;
import com.babpat.server.domain.member.repository.MemberRepository;
import com.babpat.server.domain.settlement.dto.request.PostSettlementRequest;
import com.babpat.server.domain.settlement.entity.Payer;
import com.babpat.server.domain.settlement.entity.Settlement;
import com.babpat.server.domain.settlement.entity.enums.PayerStatus;
import com.babpat.server.domain.settlement.repository.PayerRepository;
import com.babpat.server.domain.settlement.repository.SettlementRepository;
import com.babpat.server.domain.settlement.service.SettlementCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SettlementCommandServiceImpl implements SettlementCommandService {
    private final SettlementRepository settlementRepository;
    private final PayerRepository payerRepository;
    private final BabpatRepository babpatRepository;
    private final ParticipationRepository participationRepository;
    private final MemberRepository memberRepository;

    @Override
    public void postSettlement(PostSettlementRequest postSettlementRequest, String authUsername) {
        Babpat validBabpat = babpatRepository.findById(postSettlementRequest.babpatId())
                .orElseThrow(() -> new CustomException(CustomResponseStatus.BABPAT_NOT_EXIST));

        Member validMember = memberRepository.findByUsername(authUsername)
                .orElseThrow(() -> new CustomException(CustomResponseStatus.MEMBER_NOT_EXIST));

        if (!validBabpat.isValidMember(authUsername)) {
            throw new CustomException(CustomResponseStatus.ACCESS_DENIED);
        }

        validBabpat.updateFinish();

        Settlement savedSettlement = settlementRepository.save(postSettlementRequest.toEntity(validBabpat, validMember));
        payerRepository.saveAll(createPayers(validBabpat, validMember, savedSettlement));
    }


    private List<Payer> createPayers(Babpat validBabpat, Member validMember, Settlement savedSettlement) {
        List<Member> payers = participationRepository.getParticipationMembersWithoutLeader(validBabpat.getId(), validMember.getId());
        return payers.stream()
                .map(payer -> Payer.builder()
                        .settlement(savedSettlement)
                        .member(payer)
                        .payerStatus(PayerStatus.UNPAID)
                        .build())
                .toList();
    }
}
