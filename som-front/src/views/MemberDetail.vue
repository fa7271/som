<template>
  <div class="container">
      <div class="page-header text-center" style="margin-top: 20px"><h1>게시글</h1></div>
      <table class="table">
          <thead>
              <tr>
                <th>ID</th>
                <th>email</th>
                <th>nickname</th>
              </tr>
          </thead>
          <tbody>
            <tr>
            <!-- <tr v-for="post in postList" :key="post.id"> -->
                <td>{{ postList.id }}</td>
                <td>
                    <a :href="`/posts/${postList.id}/detail`">{{ truncate(postList.title) }}</a>
                </td>
                <td>{{ postList.email }}</td> <!-- 이 부분을 수정 -->
                <td>{{ postList.contents }}</td>
                <td>{{ postList.createdAt }}</td>
            </tr>
        </tbody>
      </table>
  </div>
</template>


<script>
import axios from 'axios';
export default {
    props: ['isAdmin', 'pageTitle', 'id'],
    data() {
        return {
            postList: [],
            // pageSize: 10, //페이지 당 목록 갯수
            // currentPage: 0, //화면 페이지
            searchType: 'optional', // 기본값 설정
            searchValue: '',
            isLastPage: false,
            isLoading: false,
        };
    },
    created(){
        this.loadPosts(this.id);
    },

    methods: {
        truncate(value, length = 10) {
            if (!value) return '';
            if (value.length <= length) return value;
            return value.substr(0, length) + '...';
        },

        searchItems(){
            this.postList = [];
            this.currentPage = 0;
            this.isLastPage = false;
            this.loadPosts();
        },

        async loadPosts(id){
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
            const response = await axios.get(`${process.env.VUE_APP_API_BASE_URL}/board/post/${id}/detail`, {headers, params});
            this.postList = response.data.data;
            console.log(this.postList)
            console.log("어금지")
            }catch(error){
                console.error("데이터 불러오기 오류:", error); // 에러 콘솔에 표시
            }
        },
    }
}
</script>
