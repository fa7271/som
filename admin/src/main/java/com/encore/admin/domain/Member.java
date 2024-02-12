package com.encore.admin.domain;

import com.encore.admin.dto.MemberUpdateRequest;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import com.encore.common.support.Role;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false, length = 50, unique = true)
    private String nickname;

    @Setter
    private String password;

    @ColumnDefault("0")
    private Long ranking;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private boolean active;

    public Member updateMember(Member member, MemberUpdateRequest dto) {

        this.nickname = dto.getNickname();
        this.password = dto.getPassword();

        return member;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void active() {
        this.active = true;
    }
}
