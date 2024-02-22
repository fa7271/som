package com.encore.admin.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthFilter extends GenericFilter {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String bearerToken = ((HttpServletRequest)request).getHeader("Authorization");
            if(bearerToken != null) {
                //bearer token에서 token값만 추출
                if(!bearerToken.startsWith("Bearer ")) {
                    throw new AuthenticationServiceException("token의 형식에 맞지 않습니다.");
                }
                String token= bearerToken.substring(7);



                //추출된 토큰을 검증 후 Authentication객체 생성
                //token과 body를 꺼내는 과정에서 암호과 같은지 확인, 다르면 에러가 나서 try catch로 잡음
                //body는 Claims
                //토큰 검증 및 claims 추출
                Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
                //Authentication 객체를 생성하기 위한 UserDetails 생성
                List<GrantedAuthority> authorities= new ArrayList<>();
                //도메인 단위 분류가 여러개
                authorities.add(new SimpleGrantedAuthority("ROLE_"+claims.get("role")));
                UserDetails userDetails = new User(claims.getSubject(),"",authorities);

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, "",
                        userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);


            }
            // 다시 filter 체인 타라.
            chain.doFilter(request, response);

        } catch (AuthenticationServiceException e) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpServletResponse.setContentType("application/json");
            //httpServletResponse.getWriter().write(ErrorResponseDTO.makeMessage(HttpStatus.UNAUTHORIZED,e.getMessage()).toString());
        }
    }
}