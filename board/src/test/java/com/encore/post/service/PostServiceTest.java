package com.encore.post.service;

import com.encore.post.domain.Post;
import com.encore.post.dto.PostReqDto;
import com.encore.post.dto.PostResDto;
import com.encore.post.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class PostServiceTest {

    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;


    @Test
    @WithMockUser(username = "test@naver.com", roles = {"USER"})
    @DisplayName("Valid_request_Create - Owner") // 본인
    public void Valid_request_Create_AsOwner() {
        //given
        PostReqDto postReqDto = new PostReqDto();
        postReqDto.setTitle("test title");
        postReqDto.setContents("test contents");

        //when
        Post post = postService.create(postReqDto);

        //then
        Assertions.assertThat("test@naver.com").isEqualTo(post.getEmail());
    }


    @Test
    @WithMockUser(username = "otheruser@example.com", roles = {"USER"})
    @DisplayName("Limit_Create - Owner")
    public void Limit_Create_UnauthorizedUser() {
        //given
        LocalDate now = LocalDate.now();
        for (int i = 0; i < 5; i++) {
            PostReqDto postReqDto = new PostReqDto();
            postReqDto.setTitle("test title" + i);
            postReqDto.setContents("test contents" + i);
            postService.create(postReqDto);
        }
        //when
        PostReqDto postReqDto = new PostReqDto();
        postReqDto.setTitle("error title");
        postReqDto.setContents("error contents");
        //then
        assertThrows(IllegalArgumentException.class, () -> postService.create(postReqDto));
    }

    @Test
    @WithMockUser(username = "test@naver.com", roles = {"USER"})
    @DisplayName("findAll")
    public void findAll() {
        // when
        PageRequest pr = PageRequest.of(0, 3);
        Specification<Post> spec = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("delYn"), "N");
        Page<Post> PostPage = postRepository.findAll(spec, pr);

        // then
        List<Post> content = PostPage.getContent();

        //then
        Assertions.assertThat(3).isEqualTo(content.size()); // 현재 페이지 포스팅 수 3
        assertEquals(0, PostPage.getNumber()); // 현재 페이지 1
    }
}