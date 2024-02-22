<template>
    <div class="container mx-auto flex flex-wrap">
      <!-- 게시글 월간순위 -->
      <div class="mt-10 w-full md:w-1/4 px-2">
        <div class="box-shadow p-4 mb-6">
          <div class="page-header text-center">
            <h1 class="text-3xl font-bold mb-5">게시글 월간순위</h1>
          </div>
          <table class="w-full">
            <thead>
              <tr>
                <th class="border-b border-gray-300 py-2">순위</th>
                <th class="border-b border-gray-300 py-2">제목</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="post in postList3" :key="post.postId" class="border-b border-gray-200 last:border-b-0">
                <td class="py-2">{{ post.rank }}</td>
                <td class="py-2">
                  <a :href="`/board/${post.id}/detail`" class="text-blue-500">{{ truncate(post.title) }}</a>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      <!-- 게시글 주간순위 -->
      <div class="mt-10 w-full md:w-1/4 px-2">
        <div class="box-shadow p-4 mb-6">
          <div class="page-header text-center">
            <h1 class="text-3xl font-bold mb-5">게시글 주간순위</h1>
          </div>
          <table class="w-full">
            <thead>
              <tr>
                <th class="border-b border-gray-300 py-2">순위</th>
                <th class="border-b border-gray-300 py-2">제목</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="post in postList2" :key="post.postId" class="border-b border-gray-200 last:border-b-0">
                <td class="py-2">{{ post.rank }}</td>
                <td class="py-2">
                  <a :href="`/board/${post.id}/detail`" class="text-blue-500">{{ truncate(post.title) }}</a>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      <!-- 게시글 일간순위 -->
      <div class="mt-10 w-full md:w-1/4 px-2">
        <div class="box-shadow p-4 mb-6">
          <div class="page-header text-center">
            <h1 class="text-3xl font-bold mb-5">게시글 일간순위</h1>
          </div>
          <table class="w-full">
            <thead>
              <tr>
                <th class="border-b border-gray-300 py-2">순위</th>
                <th class="border-b border-gray-300 py-2">제목</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="post in postList" :key="post.postId" class="border-b border-gray-200 last:border-b-0">
                <td class="py-2">{{ post.rank }}</td>
                <td class="py-2">
                  <a :href="`/board/${post.id}/detail`" class="text-blue-500">{{ truncate(post.title) }}</a>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      <!-- 사용자 랭킹 -->
      <div class="mt-10 w-full md:w-1/4 px-2">
        <div class="box-shadow p-4 mb-6">
          <div class="page-header text-center">
            <h1 class="text-3xl font-bold mb-5">사용자 랭킹</h1>
          </div>
          <table class="w-full">
            <thead>
              <tr>
                <th class="border-b border-gray-300 py-2">순위</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="post in postList" :key="post.postId" class="border-b border-gray-200 last:border-b-0">
                <td class="py-2">{{ post.rank }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </template>
  <script>
  import axios from 'axios';
  export default {
    data() {
      return {
        posts: [],
        postList: [],
        postList2: [],
        postList3: []
      }
    },
    created() {
      this.loadPosts();
    },
    methods: {
      truncate(value, length = 10) {
        if (!value) return '';
        if (value.length <= length) return value;
        return value.substr(0, length) + '...';
      },
      async loadPosts() {
        try {
          const token = localStorage.getItem('token');
          const headers = token ? { Authorization: `Bearer ${token}` } : {};
          const [response1, response2, response3] = await Promise.all([
            axios.get(`${process.env.VUE_APP_API_BASE_URL}/board/post/today`, { headers }),
            axios.get(`${process.env.VUE_APP_API_BASE_URL}/board/post/week`, { headers }),
            axios.get(`${process.env.VUE_APP_API_BASE_URL}/board/post/month`, { headers })
          ]);
          this.postList = response1.data.data;
          this.postList2 = response2.data.data;
          this.postList3 = response3.data.data;
          console.log(this.postList);
          console.log(response1);
        } catch (error) {
          console.error("데이터 불러오기 오류:", error);
          console.error("데이터 불러오기 오류:", error);
        }
      }
    }
  }
  </script>
  <style scoped>
  .container {
    max-width: 1500px; /* 컨테이너의 최대 너비를 조정합니다. */
    margin-left: auto;
    margin-right: auto;
  }
  .box-shadow {
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  }
  </style>