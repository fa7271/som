package com.encore.comment.dto;

import com.encore.comment.domain.Comment;
import com.encore.post.domain.Post;
import com.encore.post.dto.MemberDto;
import com.encore.post.dto.PostResDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResDto {
    private Long id;
    private String comment;
    private Long rank;

    public static CommentResDto toCommentRestDto(Comment Comment, List<MemberDto> memberDtos) {
        CommentResDtoBuilder builder = CommentResDto.builder();
        builder.id(Comment.getId())
                .comment(Comment.getComment());

        // comment에 해당하는 member 정보를 찾기
        Optional<MemberDto> memberDtoOptional = memberDtos.stream()
                .filter(memberDto -> memberDto.getEmail().equals(Comment.getEmail()))
                .findFirst();

        // member 정보가 존재한다면 nickname과 rank를 설정
        memberDtoOptional.ifPresent(memberDto -> {
            builder.rank(memberDto.getRanking());
        });

        return builder.build();
    }
}
