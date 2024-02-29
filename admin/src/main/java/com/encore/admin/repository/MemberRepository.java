package com.encore.admin.repository;

import com.encore.admin.domain.Member;
import com.encore.admin.common.support.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    List<Member> findAllByEmailIn(List<String> email);

    List<Member> findTop10ByRoleAndPointIsNotOrderByRanking(Role role, Long point);

    Page<Member> findAllByNicknameContainingOrderByCreatedAtDesc(String nickname , Pageable pageable);
}






























































