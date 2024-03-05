package com.encore.comment.domain;

import com.encore.like.domain.Likes;
import com.encore.post.domain.Post;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Comment  {

    public Comment(String comment, String email, Post post) {
        this.comment = comment;
        this.email = email;
        this.post = post;
        this.delYn = "N";
    }

    public void deleteComment(){ // item 삭제 시 호출
        this.delYn = "Y";
    }
    public void update(String comment){
        this.comment = comment;
    }


    public static Comment CreateComment(String comment, String email, Post post) {
        return new Comment(comment, email, post);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comment; // 욕설 filter 때문에

    private String email;

    @Builder.Default // Builder.Default 를 붙혀주지 않으면 Builder에 기본 null로 세팅되어 있기 때문에 db에 null이 들어간다.
    private String delYn="N"; // item 삭제 유무

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "post")
    private List<Likes> likes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Post post;

}