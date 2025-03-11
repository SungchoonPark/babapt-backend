package com.babpat.server.domain.member.service.auth;

import com.babpat.server.common.enums.CustomResponseStatus;
import com.babpat.server.common.exception.CustomException;
import com.babpat.server.config.jwt.enums.TokenType;
import com.babpat.server.domain.member.dto.request.SignInRequestDto;
import com.babpat.server.domain.member.dto.request.SignupRequestDto;
import com.babpat.server.domain.member.dto.response.SignInResponseDto;
import com.babpat.server.domain.member.entity.Member;
import com.babpat.server.domain.member.entity.enums.Track;
import com.babpat.server.domain.member.repository.MemberRepository;
import com.babpat.server.util.PasswordUtil;
import com.babpat.server.util.jwt.JwtUtil;
import com.babpat.server.util.jwt.TokenGenerator;
import com.babpat.server.util.redis.RedisUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {
    private static final String RT = "RT:";

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final TokenGenerator tokenGenerator;

    @Override
    public void register(SignupRequestDto requestDto) {
        if (memberRepository.existsByNicknameAndNameAndTrack(
                requestDto.names().nickname(),
                requestDto.names().name(),
                Track.fromString(requestDto.track()))
        ) {
            throw new CustomException(CustomResponseStatus.MEMBER_ALREADY_EXIST);
        }

        memberRepository.save(requestDto.toEntity());
    }

    @Override
    public SignInResponseDto login(SignInRequestDto signInRequestDto) {
        Member validMember = memberRepository.findByUsername(signInRequestDto.id())
                .orElseThrow(() -> new CustomException(CustomResponseStatus.MEMBER_NOT_EXIST));

        if (!PasswordUtil.isSamePassword(signInRequestDto.password(), validMember.getPassword())) {
            throw new CustomException(CustomResponseStatus.MEMBER_NOT_EXIST);
        }

        String refreshToken = redisUtil.getData(RT + validMember.getId());
        if (refreshToken == null) {
            refreshToken = jwtUtil.createToken(validMember.getId().toString(), TokenType.REFRESH_TOKEN, validMember.getRole());
            redisUtil.setData(RT + validMember.getId(), refreshToken, jwtUtil.getExpiration(TokenType.REFRESH_TOKEN));
        }

        return new SignInResponseDto(
                validMember.getId(),
                validMember.getName(),
                validMember.getNickname(),
                validMember.getTrack(),
                tokenGenerator.generateTokenWithRFToken(validMember.getId().toString(), refreshToken, validMember.getRole())
        );
    }
}
