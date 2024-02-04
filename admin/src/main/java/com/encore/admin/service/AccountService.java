package com.encore.admin.service;

import com.encore.admin.domain.Member;
import com.encore.admin.dto.SignInResponse;
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

    public void
    signup(SignUpRequest signUpRequest) {

        Optional<Member> findMember = repository.findByEmail(signUpRequest.getEmailId());
        if(findMember.isPresent()) {
            // 존재
        }

        Member member = Member.builder()
                .role(Role.USER)
                .emailId(signUpRequest.getEmailId())
                .nickname(signUpRequest.getNickname())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .ranking(0L)
                .build();

        repository.save(member);
    }

    public SignInResponse signin(SignUpRequest signUpRequest){
        return null;
    }
}
