package com.encore.admin.service;

import com.encore.admin.domain.Member;
import com.encore.admin.dto.SignInRequest;
import com.encore.admin.dto.SignUpRequest;
import com.encore.admin.repository.MemberRepository;
import com.encore.common.support.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Transactional
@Service
public class AccountService {

    private final MemberRepository repository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public AccountService(MemberRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public void signup(SignUpRequest signUpRequest) {

        Optional<Member> findMember = repository.findByEmail(signUpRequest.getEmail());
        if(findMember.isPresent()) {
            // 존재
        }

        Member member = Member.builder()
                .role(Role.USER)
                .email(signUpRequest.getEmail())
                .nickname(signUpRequest.getNickname())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .ranking(0L)
                .build();

        repository.save(member);
    }

    public Member signin(SignInRequest signInRequest){


        Member member = repository.findByEmail(signInRequest.getEmail()).orElseThrow(
                ()->new IllegalArgumentException("존재하지 않는 이메일입니다."));

        System.out.println(member.getEmail());
        if(!passwordEncoder.matches(signInRequest.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호 불일치");
        }

        return member;
    }
}
