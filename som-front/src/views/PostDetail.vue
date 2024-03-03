<template>
  <div class="container">
    <div class="page-header text-center" style="margin-top: 20px">
      <h3>게시글</h3>
    </div>

    <div class="form-group text-right"> <!-- isAuthorLoggedIn true 이면 게시글 수정 버튼 생성 -->
      <button @click="toggleEditMode" v-if="isAuthorLoggedIn" class="btn btn-outline-primary" type="submit" value="작성" style="margin-top: -3px; background-color: transparent; border-color: transparent; color: #007bff;">
        <i class="fas fa-pencil-alt" style="margin-right: 5px;"></i><span class="text-3xs font-bold mb-1">게시글 수정</span>
      </button>
    </div> <!-- 변경된 부분 -->

    <!-- 게시글 제목 -->
    <div class="post-title-container justify-content-between align-items-center">
      <div v-if="!isEditing">
        <label>게시글 제목:</label>
        <div class="post-title" v-html="title"></div>
      </div>
      
      <div v-else>
        <label>게시글 제목:</label>
        <input type="text" class="form-control" v-model="editedTitle">
      </div>

    </div>
    <!-- 게시글 내용 -->
    <div class="post-content-container">
      <label>게시글 내용:</label>
      <div v-if="!isEditing">
        <textarea v-model="contents" class="form-control" rows="10" readonly></textarea>
      </div>
      <div v-else>
        <textarea class="form-control" v-model="editedContent"></textarea>
      </div>
    </div>

    <!-- 수정한 내용 저장 버튼 -->
    <div class="form-group text-right">
      <button @click="saveChanges" v-if="isEditing" class="btn btn-primary">수정한 내용 저장</button>
    </div>

    <!-- 댓글 작성 폼과 댓글 목록 -->
    <div class="comment-section">
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
      editedContent: '',
      isAuthorLoggedIn: false // 게시글 작성자와 로그인한 사용자가 같은지 여부를 저장할 변수
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
        const response = await axios.get(`${process.env.VUE_APP_API_BASE_URL}/board/post/${id}/detail`, { headers });
        this.postList = response.data.data;
        this.title = this.postList.title;
        this.contents = this.postList.contents;

        //로그인 계정 email을 가져오기 위한 response2
        const response2 = await axios.get(`${process.env.VUE_APP_API_BASE_URL}/admin/member/mypage`, {
        headers: {
        Authorization: `Bearer ${token}`
        }
        });

        console.log("response2",response2) // response2.data.data.email로 로그인 계정 email 가져옴
        console.log("local",localStorage)
        console.log("response",response)
        console.log(this.postList.email) // this.postList.email 해당 게시글 작성자 email 가져옴
        if (response2.data.data.email === this.postList.email) { // email 비교해서 맞으면 true
          this.isAuthorLoggedIn = true;
        }
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
        await axios.post(`${process.env.VUE_APP_API_BASE_URL}/board/${this.id}/comment`, formData, { headers });
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
        const response = await axios.get(`${process.env.VUE_APP_API_BASE_URL}/board/${this.id}/comment/list`, { headers });
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
    async saveChanges() {
      try {
        const formData = new FormData();
        formData.append('title', this.editedTitle);
        formData.append('contents', this.editedContent);

        const token = localStorage.getItem('token');
        const headers = token ? { Authorization: `Bearer ${token}` } : {};
        await axios.post(`${process.env.VUE_APP_API_BASE_URL}/board/post/${this.id}/update`, formData, { headers });
        alert("게시글이 업데이트되었습니다.");
        window.location.reload();
      } catch (error) {
        console.error("게시글 업데이트 오류:", error);
        alert("게시글이 업데이트되었습니다(오류수정해야합니다).");
        window.location.reload();
      }
      this.isEditing = false; // 수정 모드 종료
    }
  }
};
</script>

<style scoped>
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
