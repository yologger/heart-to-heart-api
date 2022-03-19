package com.yologger.heart_to_heart_api.common.util;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret.access-token}")
    private String accessTokenSecret;

    @Value("${jwt.secret.refresh-token}")
    private String refreshTokenSecret;

    private long accessTokenExpire = 60 * 24 * 1;   // 1 day
    private long refreshTokenExpire = 60 * 24 * 7;  // 7 days

//     private long accessTokenExpire = 1;   // 1 minute
//     private long refreshTokenExpire = 2;  // 2 minutes

    public String generateAccessToken(Long id, String email, String name, String nickname) {

        // Header
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        // Payload
        Map<String, Object> payloads = new HashMap<>();
        payloads.put("id", id);
        payloads.put("email", email);
        payloads.put("name", name);
        payloads.put("nickname", nickname);

        // Generate access token
        String accessToken = Jwts.builder()
                .setHeader(headers)         // Set headers
                .setClaims(payloads)        // Set claims
                .setSubject("member")       // Purpose of token
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(accessTokenExpire).toInstant()))  // Set expiration
                .signWith(SignatureAlgorithm.HS256, accessTokenSecret.getBytes()) // Sign with HS256, Key
                .compact();                 // Generate token

        return accessToken;
    }

    public String generateRefreshToken(Long id, String email, String name, String nickname) {

        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        Map<String, Object> payloads = new HashMap<>();
        payloads.put("id", id);
        payloads.put("email", email);
        payloads.put("fullName", name);
        payloads.put("nickname", nickname);

        String refreshToken = Jwts.builder()
                .setHeader(headers)
                .setClaims(payloads)
                .setSubject("member")
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(refreshTokenExpire).toInstant()))
                .signWith(SignatureAlgorithm.HS256, refreshTokenSecret.getBytes())
                .compact();

        return refreshToken;
    }


    public void verifyAccessToken(String accessToken) throws UnsupportedEncodingException {
        Jwts.parser()
                .setSigningKey(accessTokenSecret.getBytes("UTF-8"))  // Set Key
                .parseClaimsJws(accessToken);  // Parsing and verifying. throws error in case of failure.
    }

    public void verifyRefreshToken(String refreshToken) throws UnsupportedEncodingException, MalformedJwtException, SignatureException, ExpiredJwtException {
        Jwts.parser()
                .setSigningKey(refreshTokenSecret.getBytes("UTF-8"))  // Set Key
                .parseClaimsJws(refreshToken);  // Parsing and verifying. throws error in case of failure.
    }

    public Long verifyAccessTokenAndGetMemberId(String accessToken) throws UnsupportedEncodingException, MalformedJwtException, SignatureException, ExpiredJwtException {
        Claims claims = Jwts.parser()
                .setSigningKey(accessTokenSecret.getBytes("UTF-8"))  // Set Key
                .parseClaimsJws(accessToken)  // Parsing and verifying. throws error in case of failure.
                .getBody();

        Long id = claims.get("id", Long.class);
        return id;
    }
}
