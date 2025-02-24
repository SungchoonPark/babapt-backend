package com.babpat.server.babpat.service;

import com.babpat.server.babpat.dto.request.BabpatPostReqDto;
import com.babpat.server.babpat.entity.Participation;
import com.babpat.server.babpat.repository.ParticipationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParticipationService {
    private final ParticipationRepository participationRepository;

    public void registerParticipation(Long babpatId, BabpatPostReqDto babpatPostReqDto) {
        Participation participation = Participation.builder()
                .memberId(babpatPostReqDto.leader())
                .babpatId(babpatId)
                .build();

        participationRepository.save(participation);
    }
}
