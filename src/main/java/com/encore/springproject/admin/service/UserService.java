package com.encore.springproject.admin.service;


import com.encore.springproject.admin.domain.Member;
import com.encore.springproject.admin.dto.MemberResponseDTO;
import com.encore.springproject.admin.dto.MemberSaveRequestDTO;
import com.encore.springproject.admin.dto.MemberUpdateRequestDTO;
import com.encore.springproject.admin.repository.UserRepository;
import com.encore.springproject.common.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
@Transactional
public class UserService {

    private final UserRepository repository;
    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void save(MemberSaveRequestDTO memberSaveRequestDto) {
        Member member = Member.builder()
                .role(Role.USER)
                .emailId(memberSaveRequestDto.getEmailId())
                .nickname(memberSaveRequestDto.getNickname())
                .password(memberSaveRequestDto.getPassword())
                .ranking(0L)
                .build();
        repository.save(member);
    }

    public void update(MemberUpdateRequestDTO memberUpdateRequestDTO) {
        Member member = repository.findById(memberUpdateRequestDTO.getId()).orElseThrow(()-> new NoSuchElementException("찾는 회원이 없습니다."));
        member.updateMember(member, memberUpdateRequestDTO);
        repository.save(member);

    }

    public List<MemberResponseDTO> findAll() {
        List<Member> members = repository.findAll();

        return members.stream().map(MemberResponseDTO::of).collect(Collectors.toList());
    }

    public MemberResponseDTO findById(Long id) {
        Member member = repository.findById(id).orElseThrow(()-> new NoSuchElementException("찾는 회원이 없습니다."));
        return MemberResponseDTO.of(member);
    }

    public void delete(Long id) {
        Member member = repository.findById(id).orElseThrow(()-> new NoSuchElementException("찾는 회원이 없습니다."));
        repository.delete(member);
    }

}
