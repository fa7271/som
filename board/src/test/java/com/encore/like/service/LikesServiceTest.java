package com.encore.like.service;

import com.encore.like.Repository.LikesRepository;
import com.encore.like.domain.Likes;
import com.encore.post.domain.Post;
import com.encore.post.dto.PostReqDto;
import com.encore.post.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
@Rollback
class LikesServiceTest {

    @MockBean
    private LikesRepository likesRepository;

    @MockBean
    private PostRepository postRepository;

    @Autowired
    private LikesService likesService;

    @Test
    @WithMockUser(username = "test@naver.com", roles = {"USER"})
    @DisplayName("Delete_Like")
    public void DeleteLike() {
        // given
        Post post = Post.builder()
                .id(1L) // Set ID for the post
                .title("test title")
                .contents("test contents")
                .build();

        Likes existingLike = Likes.builder()
                .post(post)
                .email("test@naver.com")
                .build();

        when(postRepository.findById(1L)).thenReturn(java.util.Optional.of(post));
        when(likesRepository.findByIdAndEmail(1L, "fa7271@naver.com")).thenReturn(existingLike);

        // when
        likesService.like(1L, "fa7271@naver.com");

        // then
        verify(likesRepository, times(1)).delete(existingLike);
    }

    @Test
    @WithMockUser(username = "test@naver.com", roles = {"USER"})
    @DisplayName("Add_Like")
    public void AddLike() {

//        given
        Post post = Post.builder()
                .id(1L) // Set ID for the post
                .title("test title")
                .contents("test contents")
                .build();
//        when
        when(postRepository.findById(1L)).thenReturn(java.util.Optional.of(post));
        when(likesRepository.findByIdAndEmail(1L, "test@naver.com")).thenReturn(null);

        likesService.like(1L,"test@naver.com");
//        then
//        인스턴스 있는지 확인
        verify(likesRepository, times(1)).save(any(Likes.class));
    }
}