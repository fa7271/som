package com.encore.springproject.admin.repository;

import com.encore.springproject.admin.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Member, Long> {

}
