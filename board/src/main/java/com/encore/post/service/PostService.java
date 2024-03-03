package com.encore.post.service;


import com.encore.common.support.ResponseCode;
import com.encore.common.support.SomException;
import com.encore.post.domain.Post;
import com.encore.post.dto.*;
import com.encore.post.feign.admin.AdminInternalClient;
import com.encore.post.repository.PostRepository;
import com.encore.util.RedisUtil;
import com.encore.views.Views;
import com.encore.views.ViewsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.LocalDate;

@Service
@Transactional
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final ViewsRepository viewsRepository;
    private final AdminInternalClient adminInternalClient;
    private final RedisUtil redisUtil;
    private final ObjectMapper objectMapper;

    @Autowired
    public PostService(PostRepository postRepository, ViewsRepository viewsRepository, AdminInternalClient adminInternalClient, RedisUtil redisUtil, ObjectMapper objectMapper) {
        this.postRepository = postRepository;
        this.viewsRepository = viewsRepository;
        this.adminInternalClient = adminInternalClient;
        this.redisUtil = redisUtil;
        this.objectMapper = objectMapper;
    }

    public Post create(PostReqDto postReqDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        LocalDate today = LocalDate.now();
        List<Post> pst = postRepository.findByEmailAndCreatedAtBetween(email, today.atStartOfDay(), today.atTime(23, 59, 59));
        if (pst.size() >= 5) {
            throw new SomException(ResponseCode.ENABLE_POST_CREATE_SIZE);
        }

        Post post = Post.CreatePost(postReqDto.getTitle(), postReqDto.getContents(), email);
        postRepository.save(post);
        return post;
    }

    public Page<PostResDto> findAll(String title,Pageable pageable) {

        Page<Post> posts = postRepository.findAllByTitleContainingAndDelYnIsNotOrderByCreatedAtDesc(title,"Y",pageable); // select * from post
        List<Post> postList = posts.getContent();
        List<MemberDto> list = new ArrayList<>();
        if (!postList.isEmpty()) {
            List<String> emailList = postList.stream()
                    .map(Post::getEmail).collect(Collectors.toList());

            MemberReqDto memberReqDto = new MemberReqDto();
            memberReqDto.setEmailList(emailList);


            //MemberDto memberDto = adminInternalClient.memberList(memberReqDto);
            ResponseEntity<Map<String,Object>> response = adminInternalClient.memberList(memberReqDto);
          
            try {
                list = objectMapper.readValue(objectMapper.writeValueAsString(response.getBody().get("rankingList")), new TypeReference<List<MemberDto>>() {
                });
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        List<MemberDto> finalList = list;
        return posts.map(p -> PostResDto.ToPostRestDto(p, finalList));

    }

    public PostDetailResDto findPostDetail(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("검색하신 ID의 게시글 없습니다."));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        String viewCount = redisUtil.getData(userEmail); //17_16_13_ ...
        if (viewCount == null) {
            redisUtil.setDataExpire(userEmail, String.valueOf(id) + "_");
            post.updateView();
        }else {
            String[] strArray = viewCount.split("_");
            List<String> redisList = Arrays.asList(strArray);
            boolean isView = false;

            if (!redisList.isEmpty()) {
                for (String redisListId : redisList) {
                    if (String.valueOf(id).equals(redisListId)) {
                        isView = true;
                        break;
                    }
                }
                if (!isView) {
                    viewCount += id + "_";
                    redisUtil.setDataExpire(userEmail, viewCount);
                    post.updateView();
                }
            }
        }
        return PostDetailResDto.ToPostDto(post);
    }

    public Post update(Long id, PostReqDto postReqDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // email 정보 꺼내기

        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("not found post"));
        if (!post.getEmail().equals(email) && !authentication.getAuthorities().contains((new SimpleGrantedAuthority("ROLE_ADMIN")))) { // order한 사람이 아니고 admin이 아니면
            throw new AccessDeniedException("권한이 없습니다.");
        }

        post.updatePost(postReqDto.getTitle(), postReqDto.getContents());
        return post;
    }

    public Post delete(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // email 정보 꺼내기

        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("not found post"));
        if (!post.getEmail().equals(email) && !authentication.getAuthorities().contains((new SimpleGrantedAuthority("ROLE_ADMIN")))) { // order한 사람이 아니고 admin이 아니면
            throw new AccessDeniedException("권한이 없습니다.");
        }

        post.deletePost();
        return post;
    }

}