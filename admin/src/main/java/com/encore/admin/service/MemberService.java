package com.encore.admin.service;


import com.encore.admin.domain.Member;
import com.encore.admin.dto.*;
import com.encore.admin.repository.MemberRepository;
import com.encore.admin.common.support.ResponseCode;
import com.encore.admin.common.support.Role;
import com.encore.admin.common.support.SomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Transactional
@Service
public class MemberService {

    private final MemberRepository repository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public MemberService(MemberRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
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

    public void update(Long id, MemberUpdateRequest memberUpdateRequest) {
        Member member = repository.findById(id).orElseThrow(() -> new SomException(ResponseCode.USER_NOT_FOUND));
        member.updateMember(memberUpdateRequest.getNickname(), passwordEncoder.encode(memberUpdateRequest.getPassword()));
        repository.save(member);

    }

    public Page<SignInResponse> findAll(String nickname, Pageable pageable) {

        Page<Member> members = repository.findAllByNicknameContainingOrderByCreatedAtDesc(nickname, pageable);
        log.info("members.getContent().get(0).getRanking() {}",members.getContent().get(0).getRanking());
        return members.map(SignInResponse::of);
    }

    public SignInResponse findById(Long id) {

        Member member = repository.findById(id).orElseThrow(() -> new SomException(ResponseCode.USER_NOT_FOUND));
        return SignInResponse.of(member);
    }

    public void delete(Long id) {
        Member member = repository.findById(id).orElseThrow(() -> new SomException(ResponseCode.USER_NOT_FOUND));
        member.inactive();

    }

    public RankingListResponse loadRankingList(RankingListRequest request) {
        List<Member> members = repository.findAllByEmailIn(request.getEmailList());
        List<Ranking> rankingList = members.stream().map(Ranking::of).collect(Collectors.toList());
        return RankingListResponse.builder().rankingList(rankingList).build();
    }

    public RankingListResponse loadRankingListTop10() {

        List<Member> members = repository.findTop10ByRoleAndPointIsNotOrderByRanking(Role.USER,0L);
        List<Ranking> rankingList = members.stream().map(Ranking::of).collect(Collectors.toList());
        return RankingListResponse.builder().rankingList(rankingList).build();
    }


    public Page<Member> findAllPage(Pageable pageable) {
        Page<Member> members = repository.findAll(pageable);
        return members;
    }

    public SignInResponse findMyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Member findmember = repository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
        return SignInResponse.of(findmember);


    }

    public void active(Long id) {

        Member member = repository.findById(id).orElseThrow(() -> new SomException(ResponseCode.USER_NOT_FOUND));
        member.active();
        System.out.println(member.isActive());
    }
}

