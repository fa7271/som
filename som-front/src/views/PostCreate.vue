<template>
    <div class="container">
        <div class="page-header text-center" style="margin-top: 20px"><h1>게시글 작성</h1></div>
        <form @submit.prevent="postCreate">
            <div class="form-group">
                <label>게시글 제목:</label>
                <input type="text" v-model="title" class="form-control" placeholder="제목을 입력하세요" style="height: 60px;">
            </div>

            <div class="form-group">
              <label>게시글 내용:</label>
              <textarea v-model="contents" class="form-control" placeholder="내용을 입력하세요" rows="10"></textarea>
            </div>

            <!-- <div>
              <ckeditor :editor="editor" v-model="contents" :config="editorConfig"></ckeditor>
            </div> -->
        
            <div class="form-group">
                <button type="submit" class="btn btn-primary mt-3">작성완료</button>
            </div>
        </form>
    </div>
</template>

<script>
import axios from 'axios';
  export default {
    props: ['isAdmin', 'pageTitle', 'id'],
    data() {
      return {
        title: '',
        contents: '',
        isLoading: false,
        isEditing: false,
        }
      },

    created() {
      if (localStorage.getItem("token")) {
        this.isLogin = true;
        this.userRole = localStorage.getItem("role");
      }
    },
    methods: {
      async postCreate() {
        this.isLoading = true;
        try {
          const formData = new FormData();
          formData.append('title', this.title);
          formData.append('contents', this.contents);

          const token = localStorage.getItem('token');
          const headers = token ? { Authorization: `Bearer ${token}` } : {};
          await axios.post(`${process.env.VUE_APP_API_BASE_URL}/board/post/create`, formData, { headers });
          console.log(formData);

          alert("게시글이 등록되었습니다.");
          this.$router.push({ name: 'PostList' });

        } catch (error) {

          alert(error.response.data.error.label);
        }
        this.isLoading = false;
      },
    }
  }
</script>