<template>
  <div class="container">
      <div class="page-header text-center" style="margin-top: 20px">
        <h1>게시글</h1>
        </div>
            <!-- <form @submit.prevent="postCreate"> -->
                <!-- <a href=""><button class="btn btn-primary" type="submit" value="작성">게시글 수정</button></a> -->
                <div class="form-group">
                    <label>게시글 제목:</label>
                    <div v-html="title"></div>
                </div>
                <div class="form-group">
                    <label>게시글 내용:</label>
                    <div v-html="contents"></div>
                    <!-- <ckeditor :editor="editor" v-model="contents" :config="editorConfig"></ckeditor> -->
                </div>
            <!-- </form> -->
        </div>

    <div class="container">
        <form @submit.prevent="commentCreate">
            <div class="form-group">
                <label>댓글 작성</label>
                <input type="text" v-model="comment" class="form-control">
                <button type="submit" class="btn btn-primary" :disabled="!comment.trim()">등록</button>
            </div>
        </form>
    </div>

    <!-- <div v-for="comment in commentList" :key="comment.id" class="comment-box">
        <p>{{ comment.member_email }}</p>
        <p>{{ comment.comment }}</p>
    </div> -->

    <div v-for="comment in commentList" :key="comment.id" class="comment-box">
        <div class="email-box">
          <p v-if="userRole !== 'ADMIN'">익명</p>
          <p v-if="userRole === 'ADMIN'">{{ comment.nickname }}</p>
        </div>
        <div class="comment-text-box">
          <p>{{ comment.comment }}</p>
        </div>
      </div>
      


</template>


<script>
import axios from 'axios';
// import CKEditor from '@ckeditor/ckeditor5-vue';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
export default {
    props: ['isAdmin', 'pageTitle', 'id'],
    data() {
        return {
            postList: [],
            commentList:[],
            // pageSize: 10, //페이지 당 목록 갯수
            // currentPage: 0, //화면 페이지
            searchType: 'optional', // 기본값 설정
            searchValue: '',
            title: '',
            contents: '',
            comment : '',
            isLastPage: false,
            isLoading: false,
            editor: ClassicEditor,
        };
    },
    created(){
        this.loadPosts(this.id);
        this.loadComments();
        if (localStorage.getItem("token")) {
            this.isLogin = true;
            this.userRole = localStorage.getItem("role");
        }
    },

    methods: {
        async loadPosts(id){
            console.log(id)
            this.isLoading = true;
            try{
            // params 키워드 사용해야 파라미터 방식으로 axios요청 가능
            const params = {
                page: this.currentPage,
                size: this.pageSize,
            }
            const token = localStorage.getItem('token');
            const headers = token ? { Authorization: `Bearer ${token}` } : {};
            const response = await axios.get(`${process.env.VUE_APP_API_BASE_URL}/board/post/${id}/detail`, {headers, params});
            this.postList = response.data.data;
            this.title = this.postList.title,
            this.contents = this.postList.contents;
            console.log(this.postList)
            }catch(error){
                console.error("데이터 불러오기 오류:", error); // 에러 콘솔에 표시
            }
        },
        async commentCreate(){
            this.isLoading = true;
            try {
                const formData = new FormData();
                formData.append('comment', this.comment);

                const token = localStorage.getItem('token');
                const headers = token ? { Authorization: `Bearer ${token}` } : {};
                console.log(formData)
                await axios.post(`${process.env.VUE_APP_API_BASE_URL}/board/${this.id}/comment`, formData, { headers });

                // this.$router.push({ name: 'PostListDetail' });
                // 댓글 등록 후에 해당 게시글을 다시 불러옴
                this.loadPosts(this.id);
                alert("댓글이 등록되었습니다.");
                window.location.reload();
                // this.$router.push({ name: 'PostListDetail' });
            } catch (error) {
                // 오류 발생 시 처리
                console.error(error);
                alert("입력값 확인 필요");
            }
            this.isLoading = false;
        },
        async loadComments(id){
            console.log(id)
            this.isLoading = true;
            try{
            // params 키워드 사용해야 파라미터 방식으로 axios요청 가능
            const params = {
                page: this.currentPage,
                size: this.pageSize,
            }
            const token = localStorage.getItem('token');
            const headers = token ? { Authorization: `Bearer ${token}` } : {};
            const response = await axios.get(`${process.env.VUE_APP_API_BASE_URL}/board/${this.id}/comment/list`, {headers, params});
            this.commentList = response.data.data;
            // this.title = this.postList;
            // this.contents = this.postList.contents;
            console.log(this.commentList)
            }catch(error){
                console.error("데이터 불러오기 오류:", error); // 에러 콘솔에 표시
            }
        },
    }
}
</script>


<style scoped>
.ck-editor__editable { height: 400px; }
.ck-content { font-size: 12px; }
.comment-box {
  margin-bottom: 10px;
  padding: 10px;
  border: 1px solid #ccc;
}

.email-box, .comment-text-box {
  margin-bottom: 5px;
}

.email-box p, .comment-text-box p {
  margin: 0;
  padding: 5px;
}
</style>

