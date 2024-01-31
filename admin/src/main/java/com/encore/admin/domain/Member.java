package com.encore.admin.domain;

import com.encore.admin.dto.MemberUpdateRequest;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
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
    private String emailId;

//    @Enumerated(EnumType.STRING)
//    private Role role;

    @Column(nullable = false, length = 50, unique = true)
    private String nickname;

    @Setter
    private String password;
    @Setter
    private String tempPassword;
    private boolean shouldChangePw;

    @ColumnDefault("0")
    private Long ranking;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedTime;

    public Member updateMember(Member member, MemberUpdateRequest dto) {

        this.nickname = dto.getNickname();
        this.password = dto.getPassword();

        return member;
    }

    public void changePassword(String password) {
        this.password = password;
    }
}
