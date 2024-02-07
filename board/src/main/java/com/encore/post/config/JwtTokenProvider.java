package com.encore.post.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public class JwtTokenProvider {
    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private int expiration;


    public String createdToken(String email, String role){
        //claims : 클레임은 토큰 사용자에 대한 속성이나 데이터포함, 주로 페이로드를 의미.
        //기본
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role", role);
        Date now = new Date();


        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiration*60*1000L))//30분
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
