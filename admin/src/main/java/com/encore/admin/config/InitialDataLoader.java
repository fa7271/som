package com.encore.admin.config;

import com.encore.admin.domain.Member;
import com.encore.admin.repository.MemberRepository;
import com.encore.common.support.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InitialDataLoader implements CommandLineRunner {
    //CommandLineRunner를 통해 스프링빈으로 등록되는 시점에 run메서드 실행

    private final MemberRepository repository;
    private final PasswordEncoder passwordEncoder;

    public InitialDataLoader(MemberRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if(repository.findByEmail("admin@test.com").isEmpty()){
            List<Member> members = new ArrayList<>();
            Member adminMember = Member.builder()
                    .nickname("admin")
                    .email("admin@test.com")
                    .password(passwordEncoder.encode("1234"))
                    .ranking(0L)
                    .role(Role.ADMIN)
                    .active(true)
                    .build();
            members.add(adminMember);
            for (int i = 0; i<100; i++) {
                Member member = Member.builder()
                        .nickname("test"+i)
                        .email("test"+i+"@test.com")
                        .password(passwordEncoder.encode("1234"))
                        .ranking(0L)
                        .role(Role.USER)
                        .active(true)
                        .build();
                members.add(member);
            }

            repository.saveAll(members);
        }
    }
}
