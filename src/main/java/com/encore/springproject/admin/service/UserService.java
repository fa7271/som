package com.encore.springproject.admin.service;


import com.encore.springproject.common.Role;
import com.encore.springproject.admin.domain.Member;
import com.encore.springproject.admin.dto.MemberRequestDto;
import com.encore.springproject.admin.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@AllArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository repository;

    public void save(MemberRequestDto memberRequestDto) {
        Member member = Member.builder()
                .role(Role.user)
                .emailId(memberRequestDto.getEmailId())
                .nickname(memberRequestDto.getNickname())
                .password(memberRequestDto.getPassword())
                .ranking(0L)
                .build();
        repository.save(member);
    }

}
