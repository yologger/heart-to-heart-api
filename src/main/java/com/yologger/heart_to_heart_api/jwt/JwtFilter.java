package com.yologger.heart_to_heart_api.jwt;

import com.yologger.heart_to_heart_api.common.exception.GlobalErrorCode;
import com.yologger.heart_to_heart_api.common.util.AccessTokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private AccessTokenProvider accessTokenProvider;

    public JwtFilter(AccessTokenProvider accessTokenProvider) {
        this.accessTokenProvider = accessTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = resolveToken(request);
        String requestURI = request.getRequestURI();

        if (StringUtils.hasText(jwt)) {
            try {
                accessTokenProvider.validateToken(jwt);
                Authentication authentication = accessTokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("유효한 ACCESS TOKEN 입니다. SecurityContext에 '{}' 인증 정보를 저장합니다, uri: {}", authentication.getName(), requestURI);
            } catch (ExpiredJwtException e) {
                log.info("만료된 ACCESS TOKEN 토큰입니다.");
                response.setStatus(GlobalErrorCode.EXPIRED_ACCESS_TOKEN.getStatus());
                response.setContentType("application/json;charset=utf-8");
                JSONObject body = new JSONObject();
                body.put("status", GlobalErrorCode.EXPIRED_ACCESS_TOKEN.getStatus());
                body.put("code", GlobalErrorCode.EXPIRED_ACCESS_TOKEN.getCode());
                body.put("message", GlobalErrorCode.EXPIRED_ACCESS_TOKEN.getMessage());;
                response.getWriter().print(body);
                return;
            } catch (Exception e) {
                log.info("잘못된 ACCESS TOKEN 토큰입니다.");
            }
        } else {
            log.info("'Authorization' 헤더에 유효한 ACCESS TOKEN 토큰이 없습니다, uri: {}", requestURI);
        }
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
