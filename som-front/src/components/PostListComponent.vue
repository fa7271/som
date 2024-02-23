<template>
    <div class="container">
        <div class="page-header text-center " style="margin-top: 20px">
            <span class="text-3xl font-bold mb-1">{{pageTitle}}</span>
        </div>
        <div class="d-flex justify-content-between" style="margin-top:20px;">
            <form @submit.prevent="searchPosts" style="display: flex; align-items: center;">
                <select v-model="searchType" style="width: auto; margin-right: 5px;">
                    <option value="title">제목</option>
                </select>
                <input v-model="searchValue" type="text"/>
                <button type="submit">검색</button>
            </form>
            <a href="/postcreate">
                <button class="btn btn-outline-primary" type="submit" value="작성" style="margin-top: -3px; background-color: transparent; border-color: transparent; color: #007bff;">
                    <i class="fas fa-pencil-alt" style="margin-right: 5px;"></i><span class="text-3xs font-bold mb-1">게시글 작성</span>
                </button>
            </a>
            
            
                    </div>
        <div class="table-container shadow-sm">
        <table class="table">
            <thead>
                <tr>
                    <th>게시글 번호</th>
                    <th>제목</th>
                    <th>글쓴이(rank)</th>
                    <th>작성일</th>
                    <th>조회수</th>
                </tr>
            </thead>
                <tbody>
                    <tr v-for="post in filteredPosts" :key="post.postId" @click="goToDetail(post.id)">
                        <td>{{post.id}}</td>
                        <td>{{post.title}}</td>
                        <!-- <td>
                            <a :href="`/posts/${post.id}/detail`">{{ truncate(post.title) }}</a>
                        </td> -->
                        <td>{{post.nickname + '(' + post.rank + ')'}}</td>
                        <td>{{post.createdAt}}</td>
                        <td>{{post.view}}</td>
                        <td v-if="isAdmin"><button @click="deletePost(post.id)" class="btn btn-secondary">삭제</button></td>
                    </tr>
                </tbody>
        </table>
        </div>
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
            searchType: 'title', // 기본값 설정
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
        goToDetail(postId) {
        // postId를 사용하여 해당 게시물의 상세 페이지 경로를 생성
        const detailPath = `/posts/${postId}/detail`;
        // 생성된 경로로 페이지 이동
        this.$router.push(detailPath);
        },
        truncate(value, length = 10) {
            if (!value) return '';
            if (value.length <= length) return value;
            return value.substr(0, length) + '...';
        },

        searchPosts(){
            this.postList = [];
            this.currentPage = 0; // 검색을 하면 페이지를 다시 1페이지로 설정
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
                title: this.searchValue
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
            this.isLoading = false;
        },
        changePage(pageNum) {
        this.currentPage = pageNum;
        this.loadPosts();
        }
    },
    computed: {
    filteredPosts() {
      return this.postList.filter(post => post.title.includes(this.searchValue));
    }
  }
}
</script>

<style>
    .table-container {
        box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
    }
</style>
