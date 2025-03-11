package com.babpat.server.domain.member.service.auth;

import com.babpat.server.common.enums.CustomResponseStatus;
import com.babpat.server.common.exception.CustomException;
import com.babpat.server.config.jwt.dto.AuthTokens;
import com.babpat.server.config.jwt.dto.TokenInfo;
import com.babpat.server.config.jwt.enums.TokenType;
import com.babpat.server.domain.member.dto.request.SignInRequestDto;
import com.babpat.server.domain.member.dto.request.SignupRequestDto;
import com.babpat.server.domain.member.dto.response.SignInResponseDto;
import com.babpat.server.domain.member.entity.Member;
import com.babpat.server.domain.member.entity.enums.RoleType;
import com.babpat.server.domain.member.entity.enums.Track;
import com.babpat.server.domain.member.repository.MemberRepository;
import com.babpat.server.util.PasswordUtil;
import com.babpat.server.util.jwt.JwtUtil;
import com.babpat.server.util.jwt.TokenGenerator;
import com.babpat.server.util.redis.RedisUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {
    private static final String RT = "RT:";
    private static final String LOGOUT = "LOGOUT";

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

    @Override
    public AuthTokens reissue(String refreshToken) {
        TokenInfo tokenInfo = jwtUtil.getTokenClaims(refreshToken);

        String refreshTokenInRedis = redisUtil.getData(RT + tokenInfo.id());
        if (refreshTokenInRedis == null) {
            throw new CustomException(CustomResponseStatus.REFRESH_TOKEN_EXPIRED);
        }
        if (!Objects.equals(refreshToken, refreshTokenInRedis)) {
            throw new CustomException(CustomResponseStatus.REFRESH_TOKEN_NOT_MATCH);
        }

        Member findMember = memberRepository.findById(tokenInfo.id()).orElseThrow(
                () -> new CustomException(CustomResponseStatus.MEMBER_NOT_EXIST)
        );

        AuthTokens generateToken = tokenGenerator.generateTokenWithoutRFToken(findMember.getId().toString(), RoleType.fromString(tokenInfo.role()));
        redisUtil.setData(RT + tokenInfo.id(), generateToken.refreshToken(), jwtUtil.getExpiration(TokenType.REFRESH_TOKEN));

        return generateToken;
    }

    @Override
    public void logout(String accessToken) {
        String resolveAccessToken = jwtUtil.resolveToken(accessToken);
        TokenInfo infoInToken = jwtUtil.getTokenClaims(resolveAccessToken);
        String refreshTokenInRedis = redisUtil.getData(RT + infoInToken.id());

        if (refreshTokenInRedis == null) throw new CustomException(CustomResponseStatus.REFRESH_TOKEN_NOT_FOUND);

        redisUtil.deleteDate(RT + infoInToken.id());
        redisUtil.setData(resolveAccessToken, LOGOUT, jwtUtil.getExpiration(resolveAccessToken));
    }
}
