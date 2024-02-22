<!-- 첫 제작 -->
<template>

    <div class="xe-widget-wrapper" style="width: 98.5%;">
        <div class="page-header text-center" style="margin-top: 20px">
            <h1>사용자 랭킹</h1>
        </div>
        <ul class="widget-list">
            <li v-for="post in posts" :key="post.id" class="post-item">
                <a :href="post.url" class="post-link">
                    <div class="post-content">
                        <span class="post-title">{{ post.title }}</span>
                        <span class="post-views">{{ post.views }}</span>
                    </div>
                </a>
            </li>
        </ul>
    </div>

    <div class="xe-widget-wrapper">
        <div class="page-header text-center" style="margin-top: 20px">
            <h1>게시글 일간순위</h1>
        </div>
        <table class="table">
            <thead>
                <tr>
                    <th>순위</th>
                    <th>제목</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="post in postList" :key="post.postId">
                    <td>{{post.rank}}</td>
                    <td>
                        <a :href="`/board/${post.id}/detail`">{{ truncate(post.title) }}</a>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>

    <div class="xe-widget-wrapper">
        <div class="page-header text-center" style="margin-top: 20px">
            <h1>게시글 주간순위</h1>
        </div>
        <table class="table">
            <thead>
                <tr>
                    <th>순위</th>
                    <th>제목</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="post in postList" :key="post.postId">
                    <td>{{post.rank}}</td>
                    <td>
                        <a :href="`/board/${post.id}/detail`">{{ truncate(post.title) }}</a>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>

    <div class="xe-widget-wrapper">
        <div class="page-header text-center" style="margin-top: 20px">
            <h1>게시글 월간순위</h1>
        </div>
        <table class="table">
            <thead>
                <tr>
                    <th>순위</th>
                    <th>제목</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="post in postList" :key="post.postId">
                    <td>{{post.rank}}</td>
                    <td>
                        <a :href="`/board/${post.id}/detail`">{{ truncate(post.title) }}</a>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>


</template>

<script>
import axios from 'axios';
export default {
    data(){
        return{
            postList: [],
            postList2: [],
            postList3: [],
        }
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
        async loadPosts(){
            this.isLoading = true;
            try{
            // params 키워드 사용해야 파라미터 방식으로 axios요청 가능
            const params = {
                page: this.currentPage,
                size: this.pageSize,
                // [this.searchType]: this.searchValue
            }
            if(this.searchType === "rank"){
                params.rank = this.searchValue;
            }else if(this.searchType === "title"){
                params.title = this.searchValue;
            }
            const token = localStorage.getItem('token');
            const headers = token ? { Authorization: `Bearer ${token}` } : {};
            const response = await axios.get(`${process.env.VUE_APP_API_BASE_URL}/board/post/today`, {headers, params});
            const response2 = await axios.get(`${process.env.VUE_APP_API_BASE_URL}/board/post/week`, {headers, params});
            const response3 = await axios.get(`${process.env.VUE_APP_API_BASE_URL}/board/post/month`, {headers, params});
            this.postList = response.data.data;
            this.postList2 = response2.data.data;
            this.postList3 = response3.data.data;
            console.log(this.postList)
            console.log(response)
            }catch(error){
                console.error("데이터 불러오기 오류:", error); // 에러 콘솔에 표시
            }
            this.isLoading = false;
        },

        //     async posts(){
        //     try{
        //     const token = localStorage.getItem('token');
        //     const headers = token ? {Authorization: `Bearer ${token}`} : {};
        //     // const response = await axios.get(`${process.env.VUE_APP_API_BASE_URL}/members`, {headers});
        //     console.log(token)
        //     axios.post(`http://localhost:8000/board/post/list`,{headers}).then((value)=> {
        //     console.log(value)
        //     })
        //     // this.memberList = response.data;
        //     }catch(error){
        //         console.log(error)
        //     }
        // },
    },



}
</script>





<style scoped>
.xe-widget-wrapper {
    margin-left: auto; /* 좌측 여백을 우측 여백과 동일하게 설정 */
    margin-right: auto; /* 우측 여백을 좌측 여백과 동일하게 설정 */
    float: left;
    width: 460px;
    border: 1px solid #ddd;
    margin: 10px;
    padding: 10px;
    background-color: #f9f9f9;
}

.widget-list {
    list-style: none;
    padding: 0;
}

.post-item {
    margin-bottom: 10px;
}

.post-link {
    text-decoration: none;
    color: #333;
    display: block;
    transition: background-color 0.3s;
}

.post-link:hover {
    background-color: #f0f0f0;
}

.post-content {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.post-title {
    flex-grow: 1;
    font-size: 16px;
}

.post-views {
    font-size: 14px;
    color: #666;
}
</style>
