package com.yologger.heart_to_heart_api.jwt;

import com.yologger.heart_to_heart_api.common.exception.GlobalErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(GlobalErrorCode.UNAUTHORIZED.getStatus());
        response.setContentType("application/json;charset=utf-8");
        JSONObject body = new JSONObject();
        body.put("status", GlobalErrorCode.UNAUTHORIZED.getStatus());
        body.put("code", GlobalErrorCode.UNAUTHORIZED.getCode());
        body.put("message", GlobalErrorCode.UNAUTHORIZED.getMessage());
        response.getWriter().print(body);
    }
}
