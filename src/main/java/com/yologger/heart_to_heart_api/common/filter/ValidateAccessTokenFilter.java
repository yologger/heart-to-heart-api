package com.yologger.heart_to_heart_api.common.filter;

import com.yologger.heart_to_heart_api.common.exception.GlobalErrorCode;
import com.yologger.heart_to_heart_api.common.util.JwtUtil;
import com.yologger.heart_to_heart_api.repository.member.MemberEntity;
import com.yologger.heart_to_heart_api.repository.member.MemberRepository;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Validate access token
 */
@Slf4j
@AllArgsConstructor
public class ValidateAccessTokenFilter extends OncePerRequestFilter {

    private JwtUtil jwtUtil;
    private List<String> notFilteredUrls;
    private MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Will validate access token.");

        // Check if 'Authorization' header exists.
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !StringUtils.hasText(authHeader)) {

            // Header
            response.setStatus(GlobalErrorCode.MISSING_AUTHORIZATION_HEADER.getStatus());
            response.setContentType("application/json;charset=utf-8");

            // Body
            JSONObject body = new JSONObject();
            body.put("status", GlobalErrorCode.MISSING_AUTHORIZATION_HEADER.getStatus());
            body.put("code", GlobalErrorCode.MISSING_AUTHORIZATION_HEADER.getCode());
            body.put("message", GlobalErrorCode.MISSING_AUTHORIZATION_HEADER.getMessage());
            response.getWriter().print(body);
            log.info("INVALID REQUEST: " + GlobalErrorCode.MISSING_AUTHORIZATION_HEADER.getMessage());
            return;
        }

        // Check if 'Authorization' header starts with 'Bearer'
        if (!authHeader.startsWith("Bearer")) {
            // Header
            response.setStatus(GlobalErrorCode.BEARER_NOT_INCLUDED.getStatus());
            response.setContentType("application/json;charset=utf-8");

            // Body
            JSONObject body = new JSONObject();
            body.put("status", GlobalErrorCode.BEARER_NOT_INCLUDED.getStatus());
            body.put("code", GlobalErrorCode.BEARER_NOT_INCLUDED.getCode());
            body.put("message", GlobalErrorCode.BEARER_NOT_INCLUDED.getMessage());
            response.getWriter().print(body);
            log.info("INVALID REQUEST: " + GlobalErrorCode.BEARER_NOT_INCLUDED.getMessage());
            return;
        }

        // Check if access token exists.
        String accessToken = authHeader.substring(7);
        if (accessToken == null || accessToken.trim().isEmpty()) {
            // Header
            response.setStatus(GlobalErrorCode.ACCESS_TOKEN_EMPTY.getStatus());
            response.setContentType("application/json;charset=utf-8");

            // Body
            JSONObject body = new JSONObject();
            body.put("status", GlobalErrorCode.ACCESS_TOKEN_EMPTY.getStatus());
            body.put("code", GlobalErrorCode.ACCESS_TOKEN_EMPTY.getCode());
            body.put("message", GlobalErrorCode.ACCESS_TOKEN_EMPTY.getMessage());
            response.getWriter().print(body);
            log.info("INVALID REQUEST: " + GlobalErrorCode.ACCESS_TOKEN_EMPTY.getMessage());
            return;
        }

        try {
            // Compare with ex-access token

            Long memberId = jwtUtil.verifyAccessTokenAndGetMemberId(accessToken);
            Optional<MemberEntity> result = memberRepository.findById(memberId);
            if (!result.isPresent()) {
                // Header
                response.setStatus(GlobalErrorCode.INVALID_ACCESS_TOKEN.getStatus());
                response.setContentType("application/json;charset=utf-8");

                // Body
                JSONObject body = new JSONObject();
                body.put("status", GlobalErrorCode.INVALID_ACCESS_TOKEN.getStatus());
                body.put("code", GlobalErrorCode.INVALID_ACCESS_TOKEN.getCode());
                body.put("message", GlobalErrorCode.INVALID_ACCESS_TOKEN.getMessage());

                log.info("INVALID REQUEST: " + GlobalErrorCode.INVALID_ACCESS_TOKEN.getMessage());
                response.getWriter().print(body);
                return;
            }

            if (!(accessToken.equals(result.get().getAccessToken()))) {
                // Header
                response.setStatus(GlobalErrorCode.INVALID_ACCESS_TOKEN.getStatus());
                response.setContentType("application/json;charset=utf-8");

                // Body
                JSONObject body = new JSONObject();
                body.put("status", GlobalErrorCode.INVALID_ACCESS_TOKEN.getStatus());
                body.put("code", GlobalErrorCode.INVALID_ACCESS_TOKEN.getCode());
                body.put("message", GlobalErrorCode.INVALID_ACCESS_TOKEN.getMessage());

                log.info("INVALID REQUEST: " + GlobalErrorCode.INVALID_ACCESS_TOKEN.getMessage());
                response.getWriter().print(body);
                return;
            }

            // Validate Access token
            jwtUtil.verifyAccessToken(accessToken);
            log.info("VALID REQUEST: ");
            filterChain.doFilter(request, response);
            return;
        } catch (ExpiredJwtException e) {
            // Header
            response.setStatus(GlobalErrorCode.EXPIRED_ACCESS_TOKEN.getStatus());
            response.setContentType("application/json;charset=utf-8");

            // Body
            JSONObject body = new JSONObject();
            body.put("status", GlobalErrorCode.EXPIRED_ACCESS_TOKEN.getStatus());
            body.put("code", GlobalErrorCode.EXPIRED_ACCESS_TOKEN.getCode());
            body.put("message", GlobalErrorCode.EXPIRED_ACCESS_TOKEN.getMessage());

            log.info("INVALID REQUEST: " + GlobalErrorCode.EXPIRED_ACCESS_TOKEN.getMessage());
            response.getWriter().print(body);
            return;
        } catch (Exception e) {
            // Header
            response.setStatus(GlobalErrorCode.INVALID_ACCESS_TOKEN.getStatus());
            response.setContentType("application/json;charset=utf-8");

            // Body
            JSONObject body = new JSONObject();
            body.put("status", GlobalErrorCode.INVALID_ACCESS_TOKEN.getStatus());
            body.put("code", GlobalErrorCode.INVALID_ACCESS_TOKEN.getCode());
            body.put("message", GlobalErrorCode.INVALID_ACCESS_TOKEN.getMessage());
            response.getWriter().print(body);
            log.info("INVALID REQUEST: " + GlobalErrorCode.INVALID_ACCESS_TOKEN.getMessage());
            return;
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return notFilteredUrls.stream().anyMatch(exclude -> exclude.equalsIgnoreCase(request.getServletPath()));
    }
}
