package com.encore.views;

import com.encore.post.domain.Post;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "views")
public class Views {
    protected Views(){

    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private Long viewsCount;

    public Views(Post post, Long viewsCount) {
        this.post = post;
        this.viewsCount = viewsCount;
    }

    public static Views Init(Post post, Long viewsCount) {
        return new Views(post, viewsCount);
    }
}
