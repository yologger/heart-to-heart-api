package com.yologger.heart_to_heart_api.jwt;

import com.yologger.heart_to_heart_api.common.exception.GlobalErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(GlobalErrorCode.INVALID_ACCESS_TOKEN.getStatus());
        response.setContentType("application/json;charset=utf-8");
        JSONObject body = new JSONObject();
        body.put("status", GlobalErrorCode.INVALID_ACCESS_TOKEN.getStatus());
        body.put("code", GlobalErrorCode.INVALID_ACCESS_TOKEN.getCode());
        body.put("message", GlobalErrorCode.INVALID_ACCESS_TOKEN.getMessage());
        response.getWriter().print(body);
    }
}
