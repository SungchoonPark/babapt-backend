package com.babpat.server.config.jwt.filter;

import com.babpat.server.common.enums.CustomResponseStatus;
import com.babpat.server.common.exception.CustomException;
import com.babpat.server.util.jwt.JwtUtil;
import com.babpat.server.util.redis.RedisUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private static final String LOGOUT = "LOGOUT";

  private final JwtUtil jwtUtil;
  private final RedisUtil redisUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String resolveToken = extractTokenFromCookies(request, "accessToken");

    if (Objects.equals(resolveToken, "")) {
      request.getRequestDispatcher("/exception/entrypoint/nullToken").forward(request, response);
      return;
    }

    try {
      handleBlacklistedToken(resolveToken);
      Authentication authentication = jwtUtil.getAuthentication(resolveToken);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } catch (CustomException e) {
      request.getRequestDispatcher("/exception/entrypoint/logout").forward(request, response);
    } catch (ExpiredJwtException e) {
      request.getRequestDispatcher("/exception/entrypoint/expiredToken").forward(request, response);
    } catch (JwtException | IllegalArgumentException e) {
      request.getRequestDispatcher("/exception/entrypoint/badToken").forward(request, response);
    }

    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    String[] excludePath = {
        "/api/v1/auth/reissue",
        "/api/v1/auth/register",
        "/api/v1/auth/login",
        "/api/v1/auth/duplicate",
        "/api/v1/babpat/post",
        "/api/v1/restaurants",
        "/favicon.ico"
    };
    String path = request.getRequestURI();
    String method = request.getMethod(); // GET, POST, PUT, DELETE 등

    if (path.startsWith("/api/v1/babpat/post") && method.equalsIgnoreCase("POST")) {
      return false;
    }

    if (path.equals("/api/v1/babpat/post/participation")) {
      return false;
    }

    return Arrays.stream(excludePath).anyMatch(path::startsWith);
  }

  // 로그아웃한 사용자가 접근하는지 파악. -> 접근할경우 예외발생
  private void handleBlacklistedToken(String resolveToken) throws CustomException {
    String redisLogoutValue = redisUtil.getData(resolveToken);
    if (redisLogoutValue != null && redisLogoutValue.equals(LOGOUT)) {
      throw new CustomException(CustomResponseStatus.LOGOUT_MEMBER);
    }
  }

  private String extractTokenFromCookies(HttpServletRequest request, String cookieName) {
    if (request.getCookies() == null) {
      return null;
    }

    return Arrays.stream(request.getCookies())
        .filter(cookie -> cookie.getName().equals(cookieName))
        .map(Cookie::getValue)
        .findFirst()
        .orElse(null);
  }
}
