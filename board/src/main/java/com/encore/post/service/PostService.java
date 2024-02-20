package com.encore.post.service;


import com.encore.post.domain.Post;
import com.encore.post.dto.*;
import com.encore.post.feign.admin.AdminInternalClient;
import com.encore.post.repository.PostRepository;
import com.encore.views.Views;
import com.encore.views.ViewsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.LocalDate;

@Service
@Transactional
public class PostService{
    private final PostRepository postRepository;
    private final ViewsRepository viewsRepository;
    private final AdminInternalClient adminInternalClient;

    private final ObjectMapper objectMapper;


    @Autowired
    public PostService(PostRepository postRepository, ViewsRepository viewsRepository, AdminInternalClient adminInternalClient, ObjectMapper objectMapper) {
        this.postRepository = postRepository;
        this.viewsRepository = viewsRepository;
        this.adminInternalClient = adminInternalClient;
        this.objectMapper = objectMapper;
    }

    public Post create(PostReqDto postReqDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        LocalDate today = LocalDate.now();
        List<Post> pst = postRepository.findByEmailAndCreatedAtBetween(email, today.atStartOfDay(), today.atTime(23, 59, 59));
        if (pst.size() >= 5) {
            throw new IllegalArgumentException("하루 최대 포스팅 횟수를 넘겼습니다.");
        }


        Post post = Post.CreatePost(postReqDto.getTitle(), postReqDto.getContents(), email);
        postRepository.save(post);
        return post;
    }

    public List<PostResDto> findAll(Pageable pageable) {
        Specification<Post> spec = new Specification<Post>() {
            @Override
            public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>(); //쿼리를 생성하기 위해서 predicates라는 리스트 생성
                //                delYn 기본 N으로 설정
                predicates.add(criteriaBuilder.equal(root.get("delYn"), "N"));

                //                리스트였던 predicates를 배열로 변환
                Predicate[] predicateArr = new Predicate[predicates.size()];
                for (int i = 0; i < predicates.size(); i++) {
                    predicateArr[i] = predicates.get(i);
                }

                Predicate predicate = criteriaBuilder.and(predicateArr);
                return predicate;
            }
        };

        Page<Post> posts = postRepository.findAll(spec, pageable); // select * from post
        List<Post> postList = posts.getContent();

        if(!postList.isEmpty()) {
            List<String> emailList = postList.stream()
                    .map(Post::getEmail).collect(Collectors.toList());

            MemberReqDto memberReqDto = new MemberReqDto();
            memberReqDto.setEmailList(emailList);
            //MemberDto memberDto = adminInternalClient.memberList(memberReqDto);
            ResponseEntity<Map<String,Object>> response = adminInternalClient.memberList(memberReqDto);
            try {
                List<MemberDto> list = objectMapper.readValue(objectMapper.writeValueAsString(response.getBody().get("rankingList")), new TypeReference<List<MemberDto>>() {});
                System.out.println(list.get(0));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }


        }

        List<PostResDto> postResDtos = new ArrayList<>();
        postResDtos = postList.stream()
                .map(p -> PostResDto.builder()
                        .id(p.getId())
                        .title(p.getTitle())
                        .contents(p.getContents())
                        .member_email(p.getEmail())
                        .build()).collect(Collectors.toList());

//        Page<PostResDto> postResDtos
//                = posts.map(p -> new PostResDto(p.getId(), p.getTitle(), p.getEmail()==null? "익명유저" : email));

//         return postList.stream().map(x -> PostResDto.ToPostRestDto(x)).collect(Collectors.toList());
        return postResDtos;
    }

    public PostDetailResDto findPostDetail(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("검색하신 ID의 회원이 없습니다."));
//        PostDetailResDto postDetailResDto = new PostDetailResDto();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

//        내가 쓴 게시글이 아닐경우
        if (!post.getEmail().equals(userEmail)){
            Views view = Views.builder()
                    .post(post)
                    .build();
            viewsRepository.save(view);
            post.getViews().add(view);
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
