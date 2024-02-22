<template>
    <div class="container">
        <div class="page-header text-center" style="margin-top: 20px"><h1>{{pageTitle}}</h1></div>
        <div class="d-flex justify-content-between" style="margin-top:20px;">
            <form @submit.prevent="searchPosts" style="display: flex; align-items: center;">
                <select v-model="searchType" style="width: auto; margin-right: 5px;">
                    <option value="optional">선택</option>
                    <option value="postid">게시글 번호</option>
                    <option value="title">제목</option>
                    <option value="member">글쓴이</option>
                </select>
                <input v-model="searchValue" type="text"/>
                <button type="submit">검색</button>
            </form>
            <a href="/postcreate"><button class="btn btn-primary" type="submit" value="작성">게시글 작성</button></a>
        </div>
        <table class="table">
            <thead>
                <tr>
                    <th>게시글 번호</th>
                    <th>제목</th>
                    <th>글쓴이(rank)</th>
                    <th>작성일</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="post in postList" :key="post.postId">
                    <td>{{post.id}}</td>
                    <td>
                        <a :href="`/posts/${post.id}/detail`">{{ truncate(post.title) }}</a>
                    </td>
                    <td>{{post.nickname + '(' + post.rank + ')'}}</td>
                    <td>{{post.createdAt}}</td>
                    <td v-if="isAdmin"><button @click="deletePost(post.id)" class="btn btn-secondary">삭제</button></td>
                </tr>
            </tbody>
        </table>
        <!-- 페이지네이션 컴포넌트 추가 -->
        <PaginationComponent :currentPage="currentPage" :totalPages="totalPageCount" @page-change="changePage" />
    </div>
</template>

<script>
import axios from 'axios';
import PaginationComponent  from '@/components/PaginationComponent.vue'; // 컴포넌트 이름 변경
export default {
  components: {
    PaginationComponent  // 컴포넌트 이름 변경된 것을 등록합니다.
  },
    props: ['isAdmin', 'pageTitle'],
    data() {
        return {
            postList: [],
            pageSize: 10, //페이지 당 목록 갯수
            currentPage: 0, //화면 페이지
            searchType: 'optional', // 기본값 설정
            searchValue: '',
            isLastPage: false,
            isLoading: false,
            totalPageCount: 0 // 전체 페이지 수
        };
    },
    created(){
        this.loadPosts();
    },

    methods: {
        truncate(value, length = 10) {
            if (!value) return '';
            if (value.length <= length) return value;
            return value.substr(0, length) + '...';
        },

        searchPosts(){
            this.postList = [];
            this.currentPage = 1; // 검색을 하면 페이지를 다시 1페이지로 설정
            this.loadPosts();
        },

        async deletePost(postId){
            if (confirm("정말 삭제 하시겠습니까?")) {
                const token = localStorage.getItem('token');
                const headers = token ? { Authorization: `Bearer ${token}` } : {};
                await axios.delete(`${process.env.VUE_APP_API_BASE_URL}/board/post/${postId}/delete`, { headers });
                window.location.reload();
            }
        },

        async loadPosts(){
            this.isLoading = true;
            try{
            // params 키워드 사용해야 파라미터 방식으로 axios요청 가능
            const params = {
                page: this.currentPage,
                size: this.pageSize,
                // [this.searchType]: this.searchValue
            }
            if(this.searchType === "postid"){
                params.postid = this.searchValue;
            }else if(this.searchType === "title"){
                params.title = this.searchValue;
            }
            const token = localStorage.getItem('token');
            const headers = token ? { Authorization: `Bearer ${token}` } : {};
            const response = await axios.get(`${process.env.VUE_APP_API_BASE_URL}/board/post/list`, {headers, params});
            this.postList = response.data.data;
            this.totalPageCount = response.data.totalPage; // 전체 페이지 수 업데이트
            console.log(this.postList)
            console.log(response)
            }catch(error){
                console.error("데이터 불러오기 오류:", error); // 에러 콘솔에 표시
            }
        },
        changePage(pageNum) {
        this.currentPage = pageNum;
        this.loadPosts();
        }
    }
}
</script>