<template>
    <div class="container">
        <div class="page-header text-center" style="margin-top: 20px"><h1>게시글 작성</h1></div>
        <form @submit.prevent="postCreate">
            <div class="form-group">
                <label>게시글 제목:</label>
                <input type="text" v-model="title" class="form-control" placeholder="제목을 입력하세요" style="height: 60px;">
            </div>
            <!-- <div id="app" class="form-group">
                <label for="editor">게시글 내용:</label>
                <ckeditor :editor="editor" v-model="contents" :config="editorConfig"></ckeditor>
            </div> -->
            <div>
              <textarea id="editor"></textarea>
            </div>
          
            <div class="form-group">
                <button type="submit" class="btn btn-primary mt-3">작성완료</button>
            </div>
        </form>
    </div>
</template>


<script>
import axios from 'axios';
// import CKEditor from '@ckeditor/ckeditor5-vue';
// import Editor from 'ckeditor5-custom-build/build/ckeditor'
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
  export default {
    name: 'editorComponent',
    // components: {
        // Use the <ckeditor> component in this view.
          // ckeditor: CKEditor.component
    // },
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
        // editedContent: '',
        editor:null,
        editorConfig: {
          placeholder: "내용을 작성해 주세요",
        }


        // editor: Editor,
        // editorData: '<p>Content of the editor.</p>',
        // editorConfig: {
        //     placeholder: "내용을 작성해 주세요!",
        //     ckfinder: {
        //             uploadUrl: "http://localhost:8080/post/image/upload",
        //             withCredentials: true,
        //     }
        // }
      };
    },
    mounted(){
      ClassicEditor.create(document.querySelector("#editor"), this.editorConfig).then(
        editor => {
          this.editor = editor;
          this.editor.model.document.on("change", () => {
            this.sendText();
          });
        }
      )
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

    }
  }
</script>

