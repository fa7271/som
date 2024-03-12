<template>
    <div class="continer2" style="overflow-y">
        <div class="page-header text-center" style="margin-top: 20px"><h1>게시글</h1></div>
        <div class="form-group">
            <label>게시글 제목:</label>
            <input type="text" v-model="title" class="form-control" placeholder="제목을 입력하세요" style="height: 60px;" readonly>
        </div>
        <div class="post-content-container" >
            <label>게시글 내용:</label>
            <div v-if="!isEditing">
              <textarea v-model="contents" class="form-control" rows="10" readonly></textarea>
            </div>
            <div v-else>
                <textarea class="form-control" v-model="editedContent"></textarea>
            </div>
        </div>
    </div>
      <!-- 댓글 작성 폼과 댓글 목록 -->
      <div class="comment-section container">
        <div class="form-container">
          <form @submit.prevent="commentCreate" class="form-row align-items-end">
            <div class="col">
              <div class="form-group mb-0">
                <label>댓글 작성</label>
                <input type="text" v-model="comment" class="form-control custom-input">
              </div>
            </div>
            <div class="col-auto">
              <button type="submit" class="btn btn-outline-secondary" :disabled="!comment.trim()">등록</button>
            </div>
          </form>
        </div>
        <div class="comment-list-container">
          <div v-for="comment in commentList" :key="comment.id" class="comment-box">
            <div class="email-box">
              <p v-if="userRole !== 'ADMIN'">익명</p>
              <p v-if="userRole === 'ADMIN'">{{ comment.nickname }}</p>
            </div>
            <div class="comment-text-box">
              <p>{{ comment.comment }}</p>
            </div>
          </div>
        </div>
      </div>
</template>

<script>
  import axios from 'axios';
  export default {
    props: ['isAdmin', 'pageTitle', 'id'],
    data() {
      return {
        postList: [],
        commentList: [],
        title: '',
        contents: '',
        comment: '',
        isLoading: false,
        isEditing: false,
        editedTitle: '',
        editedContent: ''
      };
    },
    created() {
      this.loadPosts(this.id);
      this.loadComments();
      if (localStorage.getItem("token")) {
        this.isLogin = true;
        this.userRole = localStorage.getItem("role");
      }
    },
    methods: {
      async loadPosts(id) {
        this.isLoading = true;
        try {
          const token = localStorage.getItem('token');
          const headers = token ? { Authorization: `Bearer ${token}` } : {};
          const response = await axios.get(`${process.env.VUE_APP_API_BASE_BOARD_URL}/board/post/${id}/detail`, { headers });
          this.postList = response.data.data;
          this.title = this.postList.title;
          this.contents = this.postList.contents;
        } catch (error) {
          console.error("데이터 불러오기 오류:", error);
        }
      },
      async commentCreate() {
        this.isLoading = true;
        try {
          const formData = new FormData();
          formData.append('comment', this.comment);
          const token = localStorage.getItem('token');
          const headers = token ? { Authorization: `Bearer ${token}` } : {};
          await axios.post(`${process.env.VUE_APP_API_BASE_BOARD_URL}/board/${this.id}/comment`, formData, { headers });
          // 댓글이 등록된 후 댓글 목록을 다시 불러옴
          await this.loadComments();
          // 최신 댓글이 가장 위로 오도록 댓글 목록을 역순으로 정렬
          this.commentList = this.commentList.reverse();
          alert("댓글이 등록되었습니다.");
        } catch (error) {
          console.error(error);
          alert("입력값 확인 필요");
        }
        this.isLoading = false;
      },
      async loadComments() {
        this.isLoading = true;
        try {
          const token = localStorage.getItem('token');
          const headers = token ? { Authorization: `Bearer ${token}` } : {};
          const response = await axios.get(`${process.env.VUE_APP_API_BASE_BOARD_URL}/board/${this.id}/comment/list`, { headers });
          this.commentList = response.data.data;
        } catch (error) {
          console.error("데이터 불러오기 오류:", error);
        }
      },
      toggleEditMode() {
        this.isEditing = !this.isEditing;
        if (this.isEditing) {
          // 수정 모드로 전환 시 제목과 내용을 복사하여 편집 가능한 변수에 저장
          this.editedTitle = this.title;
          this.editedContent = this.contents;
        }
      },
      saveChanges() {
        // 서버에 새로운 제목과 내용을 업데이트하는 로직을 추가해야 합니다.
        // 이 예제에서는 제목과 내용을 업데이트하는 서버 요청을 보내는 코드를 작성하지는 않았습니다.
        this.title = this.editedTitle;
        this.contents = this.editedContent;
        this.isEditing = false; // 수정 모드 종료
      }
    }
  }
  </script>

<style scoped>
.continer2{
    height: auto;
    width: 100%;
    margin-right: auto;
    margin-left: auto;
    flex-direction: column;
    max-width: 1140px;
    padding-right: 15px;
    padding-left: 15px;
    flex-direction: column;
}
.ck-editor__editable { height: 400px; }
.ck-content { font-size: 12px; }
.post-title-container {
  margin-bottom: 20px;
}
.post-content-container {
  margin-bottom: 20px;
}
.post-title {
  border: 1px solid #ccc;
  padding: 10px;
}
.post-contents {
    min-height: 400px;
  border: 1px solid #ccc;
  padding: 10px;
}
.comment-section {
  display: flex;
  flex-direction: column;
}
.form-container {
  margin-bottom: 20px;
}
.comment-box {
  margin-bottom: 10px;
  padding: 10px;
  border: 1px solid #ccc;
}
.email-box,
.comment-text-box {
  margin-bottom: 5px;
}
.email-box p,
.comment-text-box p {
  margin: 0;
  padding: 5px;
}
/* Custom Input Style */
.custom-input {
  border: 1px solid #CED4DA;
  border-radius: 0.25rem;
  padding: 0.375rem 0.75rem;
  box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
}
/* Custom Button Style */
.custom-btn {
  border-radius: 0.25rem;
  box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
}
</style>