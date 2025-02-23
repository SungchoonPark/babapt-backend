package com.babpat.server.member.service;

import com.babpat.server.member.dto.request.SignupRequestDto;
import com.babpat.server.member.entity.Member;
import com.babpat.server.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public void signup(SignupRequestDto requestDto) {
        Member member = requestDto.toEntity();
        memberRepository.save(member);
    }
}
