package com.encore.batch.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
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

    private boolean active;

    @Column(nullable = false, length = 50, unique = true)
    private String nickname;

    @Setter
    private String password;

    @ColumnDefault("0")
    private Long ranking;

    @ColumnDefault("0")
    private Long point;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    enum Role {
        USER,ADMIN;
    }

    public void updatePoint(Long point) {
        this.point = point;
    }
    public void updateRanking(Long ranking) {
        this.ranking = ranking;
    }
}
