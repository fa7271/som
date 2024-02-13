package com.encore.comment.domain;

import com.encore.like.domain.Likes;
import com.encore.post.domain.Post;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Comment  {

    public void deleteComment(){ // item 삭제 시 호출
        this.delYn = "Y";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contents; // 욕설 filter 때문에

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
    @JoinColumn(name = "post_id")
    private Post post;

}