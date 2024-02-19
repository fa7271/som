package com.encore.admin.service;


import com.encore.admin.domain.Member;

import com.encore.admin.dto.*;
import com.encore.admin.repository.MemberRepository;

import com.encore.common.support.ResponseCode;
import com.encore.common.support.Role;
import com.encore.common.support.SomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Slf4j
@Transactional
@Service
public class MemberService {

    private final MemberRepository repository;

    @Autowired
    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    public void save(SignUpRequest signUpRequest) {
        Member member = Member.builder()
                .role(Role.USER)
                .email(signUpRequest.getEmail())
                .nickname(signUpRequest.getNickname())
                .password(signUpRequest.getPassword())
                .ranking(0L)
                .build();

        repository.save(member);
    }

    public void update(MemberUpdateRequest memberUpdateRequest) {
        Member member = repository.findById(memberUpdateRequest.getId()).orElseThrow(() -> new NoSuchElementException("찾는 회원이 없습니다."));
        member.updateMember(member, memberUpdateRequest);
        repository.save(member);

    }

    public List<SignInResponse> findAll(Pageable pageable) {
        List<Member> members = repository.findAll();

        return members.stream().map(SignInResponse::of).collect(Collectors.toList());
    }

    public SignInResponse findById(Long id) {
        Member member = repository.findById(id).orElseThrow(() -> new NoSuchElementException("찾는 회원이 없습니다."));
        return SignInResponse.of(member);
    }

    public void delete(Long id) {
        Member member = repository.findById(id).orElseThrow(() -> new NoSuchElementException("찾는 회원이 없습니다."));
        member.inactive();

    }

    public RankingListResponse loadRankingList(RankingListRequest request) {
        List<Member> members = repository.findAllByEmailIn(request.getEmailList());
        List<Ranking> rankingList = members.stream().map(Ranking::of).collect(Collectors.toList());
        return RankingListResponse.builder().rankingList(rankingList).build();
    }

    public RankingListResponse loadRankingListTop10() {

        System.out.println("1");
        List<Member> members = repository.findTop10ByOrderByRankingDesc();
        System.out.println(members.get(0).getRanking());
        List<Ranking> rankingList = members.stream().map(Ranking::of).collect(Collectors.toList());
        return RankingListResponse.builder().rankingList(rankingList).build();
    }


    public Page<Member> findAllPage(Pageable pageable) {
        Page<Member> members = repository.findAll(pageable);
        return members;
    }
    public MemberResponse findMyInfo()  {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Member findmember = repository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
        return MemberResponse.from(findmember);


    }


}

