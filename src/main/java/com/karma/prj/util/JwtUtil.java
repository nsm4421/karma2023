package com.karma.prj.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtUtil {
    /**
     * JWT 생성
     * @param username 인코딩할 문자열에 유저명 사용
     * @param secretKey 보안키 값
     * @param duration 토큰 유효시간 (Milli Second)
     * @return jwt 토큰값
     */
    public static String generateToken(String username, String secretKey, long duration){
        Claims claims = Jwts.claims();
        claims.put("username", username);
        long currentTimeMillis = System.currentTimeMillis();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(new Date(currentTimeMillis+duration))
                .signWith(
                        Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)),
                        SignatureAlgorithm.HS256
                ).compact();
    }
}
