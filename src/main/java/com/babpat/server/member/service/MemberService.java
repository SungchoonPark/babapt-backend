package com.babpat.server.member.service;

import com.babpat.server.common.enums.CustomResponseStatus;
import com.babpat.server.common.exception.CustomException;
import com.babpat.server.member.dto.request.SignInRequestDto;
import com.babpat.server.member.dto.request.SignupRequestDto;
import com.babpat.server.member.dto.response.SignInResponseDto;
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
    public void register(SignupRequestDto requestDto) {
        Member member = requestDto.toEntity();
        memberRepository.save(member);
    }

    @Transactional
    public SignInResponseDto login(SignInRequestDto signInRequestDto) {
        Member member = memberRepository.findByUsernameAndPassword(signInRequestDto.id(), signInRequestDto.password())
                .orElseThrow(() -> new CustomException(CustomResponseStatus.MEMBER_NOT_EXIST));

        return new SignInResponseDto(member.getName(), member.getNickname(), member.getTrack());
    }
}
