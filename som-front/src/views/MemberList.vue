<template>
  <div class="container">
    <div class="page-header text-center" style="margin-top: 20px">
      <h1>{{ pageTitle }}</h1>
    </div>
    <div class="d-flex justify-content-between" style="margin-top: 20px;">
      <form @submit.prevent="searchPosts" style="display: flex; align-items: center;">
        <select v-model="searchType" style="width: auto; margin-right: 5px;">
          <option value="nickname">닉네임</option>
        </select>
        <input v-model="searchValue" type="text" placeholder="닉네임을 입력하세요"/>
        <button type="submit" style="background-color: transparent; border: none;">검색</button>
      </form>
    </div>
    <div class="table-container">
      <table class="table">
        <thead>
          <tr>
            <th>ID</th>
            <th>email</th>
            <th>nickname</th>
            <th>role</th>
            <th>휴면상태</th>
            <th>랭킹</th>
            <th>활성화</th>
            <th>비활성화</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="member in filteredMembers" :key="member.id">
            <td>{{ member.id }}</td>
            <!-- <td>{{ member.email }}</td> -->
            <td>
              <a :href="`/admin/member/detail/${member.id}`">{{ truncate(member.email) }}</a>
            </td>
            <td>{{ member.nickname }}</td>
            <td>{{ member.role }}</td>
            <td>{{ member.active===true?"활성화":"비활성화" }}</td>
            <td>{{ member.ranking }}</td>
            <!-- <div>
              <toggle-component v-if="member.active == 1" :isToggled="true" @click="activeMember(member.id)"></toggle-component>
              <toggle-component v-else :isToggled="true" @click="deleteMember(member.id)"></toggle-component> 
            </div> -->
            
            <td><button @click="activeMember(member.id)" class="btn btn-outline-dark">활성화</button></td>
            <td><button @click="deleteMember(member.id)" class="btn btn-outline-dark">비활성화</button></td>
          </tr>
        </tbody>
      </table>
    </div>
    <!-- 페이지네이션 컴포넌트 추가 -->
    <PaginationComponent :currentPage="currentPage" :totalPages="totalPageCount" @page-change="changePage" />
  </div>
</template>

<script>
import axios from 'axios';
import PaginationComponent from '@/components/PaginationComponent.vue';

export default {
  components: {
    PaginationComponent,
  },
  props: ['isAdmin', 'pageTitle', 'member'],
  data() {
    return {
      memberList: [],
      pageSize: 10,
      currentPage: 0,
      searchType: 'nickname',
      searchValue: '',
      isLastPage: false,
      isLoading: false,
      totalPageCount: 0
    };
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
    async searchPosts() {
      this.currentPage = 0;
      this.loadPosts();
    },
    async deleteMember(memberId) {
      if (confirm("비활성화 하시겠습니까?")) {
        const token = localStorage.getItem('token');
        const headers = token ? { Authorization: `Bearer ${token}` } : {};
        await axios.delete(`${process.env.VUE_APP_API_BASE_URL}/admin/member/delete/${memberId}`, { headers });
        window.location.reload();
      }
    },
    async activeMember(memberId) {
      if (confirm("활성화 하시겠습니까?")) {
        const token = localStorage.getItem('token');
        const headers = token ? { Authorization: `Bearer ${token}` } : {};
        await axios.post(`${process.env.VUE_APP_API_BASE_URL}/admin/member/active/${memberId}`, {}, { headers });
        window.location.reload();
      }
    },
    async loadPosts() {
      this.isLoading = true;
      try {
        const params = {
          page: this.currentPage,
          size: this.pageSize,
          nickname: this.searchValue
        }
        const token = localStorage.getItem('token');
        const headers = token ? { Authorization: `Bearer ${token}` } : {};
        const response = await axios.get(`${process.env.VUE_APP_API_BASE_URL}/admin/member`, { headers, params });
        this.memberList = response.data.data;
        this.totalPageCount = response.data.totalPage;
        console.log(response.data.count)
        console.log(this.memberList)
      } catch (error) {
        console.error("데이터 불러오기 오류:", error);
      }
      this.isLoading = false;
    },
    changePage(pageNum) {
      this.currentPage = pageNum;
      this.loadPosts();
    }
  },
  computed: {
    filteredMembers() {
      return this.memberList.filter(member => member.nickname.includes(this.searchValue));
    }
  }
};
</script>

<style>
.table-container {
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
}
</style>
