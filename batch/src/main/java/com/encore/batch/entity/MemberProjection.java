//package com.encore.batch.entity;
//
//
//import lombok.Data;
//
//import javax.persistence.EntityManagerFactory;
//
//@Data
//public class MemberProjection {
//
//    private Long id;
//
//    private Long postCount;
//
//    private Long commentCount;
//
//    public MemberProjection(){};
//
//    @QueryProjection
//    public MemberProjection(Long id, Long postCount, Long commentCount){
//        this.id = id;
//        this.postCount = postCount;
//        this.commentCount = commentCount;
//    }
//
//    public Member toMember(EntityManagerFactory emf) {
//
//        Member member = emf.createEntityManager().find(Member.class, this.getId());
//        member.updatePoint(this.postCount * 3L + this.commentCount * 10L);
//        return member;
//    }
//}
//
