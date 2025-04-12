package com.babpat.server.domain.babpat.service.participation.impl;

import com.babpat.server.common.enums.CustomResponseStatus;
import com.babpat.server.common.exception.CustomException;
import com.babpat.server.domain.babpat.dto.request.BabpatApplyRequest;
import com.babpat.server.domain.babpat.entity.Babpat;
import com.babpat.server.domain.babpat.entity.Participation;
import com.babpat.server.domain.babpat.entity.enums.ParticipationStatus;
import com.babpat.server.domain.babpat.repository.ParticipationRepository;
import com.babpat.server.domain.babpat.repository.babpat.BabpatRepository;
import com.babpat.server.domain.babpat.service.participation.ParticipationCommandService;
import com.babpat.server.domain.member.entity.Member;
import com.babpat.server.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ParticipationCommandServiceImpl implements ParticipationCommandService {

  private final ParticipationRepository participationRepository;
  private final MemberRepository memberRepository;
  private final BabpatRepository babpatRepository;

  @Override
  public void registerParticipation(Babpat babpat, Member leader) {
    Participation participation = Participation.builder()
        .member(leader)
        .babpat(babpat)
        .participationStatus(ParticipationStatus.JOINED)
        .build();

    participationRepository.save(participation);
  }

  @Override
  public void applyBabpat(Integer headCount, String applyUsername,
      BabpatApplyRequest applyRequest) {
    Babpat babpat = babpatRepository.findById(applyRequest.babpatId())
        .orElseThrow(() -> new CustomException(CustomResponseStatus.BABPAT_NOT_EXIST));
    Member applyMember = memberRepository.findByUsername(applyUsername)
        .orElseThrow(() -> new CustomException(CustomResponseStatus.MEMBER_NOT_EXIST));

    // Todo : 코드 최적화 가능
    if (participationRepository.existsByBabpatIdAndMemberId(applyRequest.babpatId(),
        applyMember.getId())) {
      throw new CustomException(CustomResponseStatus.ALREADY_PARTICIPATION);
    }

    Long filledSlots = participationRepository.countByBabpatId(applyRequest.babpatId());

    if (headCount - filledSlots <= 0) {
      throw new CustomException(CustomResponseStatus.BABPAT_CLOSED);
    } else if (headCount - filledSlots == 1) {
      babpat.updateFull();
    }

    participationRepository.save(applyRequest.toEntity(babpat, applyMember));
  }
}
