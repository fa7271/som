package com.encore.admin.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private int expiration;

    //사용자 이메일과 롤
    public String createdToken(String email, String role){
        //claims : 클레임은 토큰 사용자에 대한 속성이나 데이터포함, 주로 페이로드를 의미.
        //기본
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role", role);
        Date now = new Date();

        log.debug("expiration {}",expiration);
        log.debug("role {}",role);

//        JwtBuilder jwtBuilder = Jwts.builder();
//        jwtBuilder.setClaims(claims);
//        jwtBuilder.setIssuedAt(now);
//        //30분
//        jwtBuilder.setExpiration(new Date(now.getTime() + 30*60*1000L));
//        jwtBuilder.signWith(SignatureAlgorithm.HS256,"mysecret");
//        return jwtBuilder.compact();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiration*60*1000L))//30분
                .signWith(SignatureAlgorithm.HS256, "mysecret")
                .compact();
    }
}
