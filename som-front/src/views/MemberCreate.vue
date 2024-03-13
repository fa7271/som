<template>
  <div class="container">
    <div class="row justify-content-center mt-5">
      <div class="col-md-6">
        <div class="card">
          <div class="card-header text-center" style="color: black;">회원가입</div>
          <div class="card-body">
            <form @submit.prevent="submitForm">
              <div class="mb-3">
                <label for="email" class="form-label">이메일 주소</label>
                <input type="email" class="form-control" id="email" v-model="email">
              </div>
              <div class="mb-3">
                <label for="nickname" class="form-label">닉네임</label>
                <input type="text" class="form-control" id="nickname" v-model="nickname">
              </div>
              <div class="mb-3">
                <label for="password" class="form-label">비밀번호</label>
                <input type="password" class="form-control" id="password" v-model="password">
              </div>
              <button type="submit" class="btn btn-primary w-100">가입하기</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  data() {
    return {
      email: "",
      nickname: "",
      password: "",
    };
  },
  methods: {
    async submitForm(){
      try {
        const requestData = {
          email: this.email,
          nickname: this.nickname,
          password: this.password
        };

        const token = localStorage.getItem('token');
        const headers = token ? { Authorization: `Bearer ${token}` } : {};

        await axios.post(`${process.env.VUE_APP_API_BASE_URL}/admin/account/signup`, requestData, { headers });

        // 성공적으로 작성된 경우 리다이렉트 또는 다른 처리 수행
        alert("회원가입 인증 메일이 발송 되었습니다. 메일을 확인해주세요.");
        this.$router.push({ name: 'LoginComponent' });
      } catch (error) {
        // 오류 발생 시 처리
        console.error(error);
        alert("입력값 확인 필요");
      }
    }
  }
};
</script>

<style>
.container {
  height: 100vh;
}

.card {
  margin-top: 50px;
  border-radius: 10px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
}

.card-header {
  background-color: #007bff;
  color: white;
  border-radius: 10px 10px 0 0;
}

.btn-primary {
  background-color: #007bff;
  border-color: #007bff;
}

.btn-primary:hover {
  background-color: #0056b3;
  border-color: #0056b3;
}
</style>
