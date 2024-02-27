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
                <ckeditor :editor="editor" v-model="contents" :config="editorConfig"></ckeditor>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-primary mt-3">작성완료</button>
            </div>
        </form>
    </div>
</template>

<script>
import axios from 'axios';
import CKEditor from '@ckeditor/ckeditor5-vue';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';

export default {
    components: {
    ckeditor: CKEditor.component
    },
    data(){
        return{
            title:"",
            contents:"",
            editor: ClassicEditor,
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
        }
    },
    methods:{
        async postCreate(){
            try {
                const formData = new FormData();
                formData.append('title', this.title);
                formData.append('contents', this.contents);
                
                const token = localStorage.getItem('token');
                const headers = token ? { Authorization: `Bearer ${token}` } : {};
                console.log(formData)
                await axios.post(`${process.env.VUE_APP_API_BASE_URL}/board/post/create`, formData, { headers });
                // 성공적으로 작성된 경우 리다이렉트 또는 다른 처리 수행
                this.$router.push({ name: 'PostList' });
            } catch (error) {
                // 오류 발생 시 처리
                console.error(error);
                alert("게시글을 이미 5개 등록하셨습니다.");
            }
        }
    }
}
</script>

<style>
.ck-editor__editable { height: 400px; }
.ck-content { font-size: 12px; }
</style>
