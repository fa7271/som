<template>
    <div class="container">
      <div class="row justify-content-center mt-5">
        <div class="col-md-6">
          <div class="card">
            <div class="card-header text-center" style="color: black;">비밀번호 재설정</div>
            <div class="card-body">
              <form @submit.prevent="submitForm">
                <div class="mb-3">
                  <label for="email" class="form-label">이메일 주소</label>
                  <div class="input-group">
                    <input type="email" class="form-control" id="email" v-model="email">
                    <button class="btn btn-primary" type="button" @click="sendVerificationCode">전송</button>
                  </div>
                </div>
                <div class="mb-3">
                  <label for="code" class="form-label">인증번호</label>
                  <input type="text" class="form-control" id="code" v-model="code" required>
                </div>
                <div class="mb-3">
                  <label for="password" class="form-label">새 비밀번호</label>
                  <input type="password" class="form-control" id="password" v-model="password" required>
                </div>
                <button type="submit" class="btn btn-primary w-100">확인</button>
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
      email: '',
      password: '',
      code: ''
    };
  },
  methods: {
    async submitForm() {
        try {
        const requestData = {
          email: this.email,
          code: this.code,
          password: this.password
        };

        const token = localStorage.getItem('token');
        const headers = token ? { Authorization: `Bearer ${token}` } : {};

        await axios.post(`${process.env.VUE_APP_API_BASE_ADMIN_URL}/admin/account/vertifycode`, requestData, { headers });

        // 성공적으로 작성된 경우 리다이렉트 또는 다른 처리 수행
        // this.$router.push({ name: 'LoginComponent' });
        alert("비밀번호 재설정 완료되었습니다.");
        this.$router.push({ name: 'LoginComponent' });
      } catch (error) {
        // 오류 발생 시 처리
        console.error(error);
        alert("입력값 확인 필요");
      }
      // 여기에 비밀번호 재설정 양식 제출에 대한 로직을 추가하세요.
      console.log('비밀번호 재설정 양식이 제출되었습니다.');
      console.log('Email:', this.email);
      console.log('인증번호:', this.code);
      console.log('새 비밀번호:', this.password);
    },
    async sendVerificationCode() {
      try {
        // const requestData = {
        //   email: this.email,
        // };

        const token = localStorage.getItem('token');
        const headers = token ? { Authorization: `Bearer ${token}` } : {};

        await axios.get(`${process.env.VUE_APP_API_BASE_ADMIN_URL}/admin/account/findPassword/${this.email}`, {headers});

        // 성공적으로 작성된 경우 리다이렉트 또는 다른 처리 수행
        // this.$router.push({ name: 'LoginComponent' });
        alert("이메일이 전송되었습니다.")
      } catch (error) {
        // 오류 발생 시 처리
        console.error(error);
        alert("입력값 확인 필요");
      }
      // 이 부분에 전송 완료 알림을 표시하는 로직을 추가하세요.
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
  background-color: #007BFF;
  color: rgb(0, 0, 0);
  border-radius: 10px 10px 0 0;
}
.btn-primary {
  background-color: #007BFF;
  border-color: #007BFF;
  color: black; /* 폰트 색상을 검은색으로 변경 */
}
.btn-primary:hover {
  background-color: #0056B3;
  border-color: #0056B3;
}
</style>