<template>
  <div class="container">
      <div class="page-header text-center" style="margin-top: 20px"><h1>게시글</h1></div>
            <form @submit.prevent="postCreate">
                <a href=""><button class="btn btn-primary" type="submit" value="작성">게시글 수정</button></a>
                <div class="form-group">
                    <label>게시글 제목:</label>
                    <input type="text" v-model="title" class="form-control" readonly>
                </div>
                <div class="form-group">
                    <label>게시글 내용:</label>
                    <div v-html="contents"></div>
                    <!-- <ckeditor :editor="editor" v-model="contents" :config="editorConfig"></ckeditor> -->
                </div>
                <!-- <div class="form-group">
                    <button type="submit" class="btn btn-primary mt-3">작성완료</button>
                </div> -->
            </form>
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
</template>


<script>
import axios from 'axios';
// import CKEditor from '@ckeditor/ckeditor5-vue';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
export default {
    components: {
    // ckeditor: CKEditor.component
    },
    props: ['isAdmin', 'pageTitle', 'id'],
    data() {
        return {
            postList: [],
            // pageSize: 10, //페이지 당 목록 갯수
            // currentPage: 0, //화면 페이지
            searchType: 'optional', // 기본값 설정
            searchValue: '',
            title: this.title,
            contents: this.contents,
            comment : '',
            isLastPage: false,
            isLoading: false,
            editor: ClassicEditor,
            editorData: '<p>내용을 입력하세요</p>',
            editorConfig: {
                    toolbar: [ 'heading', '|', 'bold', 'italic', 'link', 'bulletedList', 'numberedList', 'blockQuote' ],
                    heading: {
                        options: [
                        { model: 'paragraph', title: '본문', class: 'ck-heading_paragraph' },
                        { model: 'heading1', view: 'h1', title: '헤딩 1', class: 'ck-heading_heading1' },
                        { model: 'heading2', view: 'h2', title: '헤딩 2', class: 'ck-heading_heading2' },
                        { model: 'heading3', view: 'h3', title: '헤딩 3', class: 'ck-heading_heading3' },
                        { model: 'heading4', view: 'h4', title: '헤딩 4', class: 'ck-heading_heading4' },
                        { model: 'heading5', view: 'h5', title: '헤딩 5', class: 'ck-heading_heading5' },
                        { model: 'heading6', view: 'h6', title: '헤딩 6', class: 'ck-heading_heading6' }
                    ]
                }
            }
        };
    },
    created(){
        this.loadPosts(this.id);
        this.commentCreate(this.id);
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
            this.title = this.postList.title,
            this.contents = this.postList.contents;
            console.log(this.postList)
            }catch(error){
                console.error("데이터 불러오기 오류:", error); // 에러 콘솔에 표시
            }
        },
        async commentCreate(id){
            try {
                const formData = new FormData();
                formData.append('comment', this.comment);

                const token = localStorage.getItem('token');
                const headers = token ? { Authorization: `Bearer ${token}` } : {};
                console.log(formData)
                await axios.post(`${process.env.VUE_APP_API_BASE_URL}/board/${id}/comment`, {headers,formData});
                // 성공적으로 작성된 경우 리다이렉트 또는 다른 처리 수행
                this.$router.push({ name: 'SOMMain' });
            } catch (error) {
                // 오류 발생 시 처리
                console.error(error);
                alert("입력값 확인 필요");
            }
        }
    }
}
</script>
<style>
.ck-editor__editable { height: 400px; }
.ck-content { font-size: 12px; }
</style>
