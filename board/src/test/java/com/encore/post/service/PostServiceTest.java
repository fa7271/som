package com.encore.post.service;

import com.encore.post.config.RedisConfig;
import com.encore.post.domain.Post;
import com.encore.post.dto.PostDetailResDto;
import com.encore.post.dto.PostReqDto;
import com.encore.post.dto.PostResDto;
import com.encore.post.repository.PostRepository;
import com.encore.util.RedisUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Equals;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
@Rollback
class PostServiceTest {

    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;

    @MockBean
    private RedisUtil redisUtil;

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
        // given
        PageRequest pr = PageRequest.of(0, 3);
        Specification<Post> spec = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("delYn"), "N");
        Page<Post> PostPage = postRepository.findAll(spec, pr);

        // when
        List<Post> content = PostPage.getContent();

        //then
        Assertions.assertThat(3).isEqualTo(content.size()); // 현재 페이지 포스팅 수 3
        assertEquals(0, PostPage.getNumber()); // 현재 페이지 1
    }

    @Test
    @WithMockUser(username = "test@naver.com", roles = {"USER"})
    @DisplayName("Delete_Post_With_permission")
    public void deletePost() {
        //given
        List<Post> posts = postRepository.findAll();

        PostReqDto postReqDto = new PostReqDto();
        postReqDto.setTitle("test title");
        postReqDto.setContents("test contents");
        Post post = postService.create(postReqDto);

        assertEquals(posts.size()+1,postRepository.count());
//         when
        postRepository.delete(post);
//         then
        assertEquals(posts.size(), postRepository.count());

    }
    @Test
    @DisplayName("Delete_Post_By_Different_User")
    @WithMockUser(username = "user1@example.com", roles = {"USER"})
    void deletePostByDifferentUser() {
        // Given
        // Create a post by user1
        PostReqDto postReqDto = new PostReqDto();
        postReqDto.setTitle("test title");
        postReqDto.setContents("test contents");
        Post post = postService.create(postReqDto);

        // When
        postRepository.save(post);

        // Then
        // Attempt to delete the post by user2 should throw AccessDeniedException
        assertThrows(AccessDeniedException.class, () -> {
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken("user2@example.com", "password", Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")))
            );
            postService.delete(post.getId());
        });
    }
    @Test
    @DisplayName("RedisPostViewd")
    @WithMockUser(username = "amdin@test.com", roles = {"USER"})
    void RedisPostViewd(){
        Long postId = 17L;

        when(redisUtil.getData("admin@test.com")).thenReturn(null);

        // 메소드 호출
        PostDetailResDto postDetailResDto = postService.findPostDetail(postId);

        // 결과 확인
        assertThat(postDetailResDto).isNotNull();


    }
}