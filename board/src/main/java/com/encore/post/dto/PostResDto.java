package com.encore.post.dto;

import com.encore.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.loadbalancer.config.LoadBalancerCacheAutoConfiguration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResDto {
    private Long id;
    private String title;
    private String nickname;
    private Long rank;
    private String contents;
    private int view;
    private LocalDate createdAt;

    public static PostResDto ToPostRestDto(Post post, List<MemberDto> memberDtos) {
        PostResDtoBuilder builder = PostResDto.builder();
        builder.id(post.getId())
                .title(post.getTitle())
                .contents(post.getContents())
                .view(post.getView())
                .createdAt(LocalDate.from(post.getCreatedAt())); // 날짜만 출력

        // post에 해당하는 member 정보를 찾기
        Optional<MemberDto> memberDtoOptional = memberDtos.stream()
                .filter(memberDto -> memberDto.getEmail().equals(post.getEmail()))
                .findFirst();

        // member 정보가 존재한다면 nickname과 rank를 설정
        memberDtoOptional.ifPresent(memberDto -> {
            builder.rank(memberDto.getRanking());
        });

        return builder.build();
    }
}
