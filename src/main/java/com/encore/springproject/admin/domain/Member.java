package com.encore.springproject.admin.domain;

import com.encore.springproject.common.Role;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50, unique = true)
    private String emailId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    @Column(nullable = false, length = 50)
    private String nickname;
    @Column(nullable = false, unique = true)
    private String password;
    @ColumnDefault("0")
    private Long ranking;
    @CreationTimestamp
    private LocalDateTime createTime;
    @UpdateTimestamp
    private LocalDateTime updatedTime;
}
