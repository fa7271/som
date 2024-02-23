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
                    <input type="email" class="form-control" id="email" v-model="email" required>
                    <button class="btn btn-primary" type="button" @click="sendVerificationCode">전송</button>
                  </div>
                </div>
                <div class="mb-3">
                  <label for="verificationCode" class="form-label">인증번호</label>
                  <input type="text" class="form-control" id="verificationCode" v-model="verificationCode" required>
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
export default {
  data() {
    return {
      email: '',
      password: '',
      verificationCode: ''
    };
  },
  methods: {
    submitForm() {
      // 여기에 비밀번호 재설정 양식 제출에 대한 로직을 추가하세요.
      console.log('비밀번호 재설정 양식이 제출되었습니다.');
      console.log('Email:', this.email);
      console.log('인증번호:', this.verificationCode);
      console.log('새 비밀번호:', this.password);
    },
    sendVerificationCode() {
      // 이 부분에 전송 완료 알림을 표시하는 로직을 추가하세요.
      alert('전송 완료!');
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
  color: rgb(0, 0, 0);
  border-radius: 10px 10px 0 0;
}

.btn-primary {
  background-color: #007bff;
  border-color: #007bff;
  color: black; /* 폰트 색상을 검은색으로 변경 */
}

.btn-primary:hover {
  background-color: #0056b3;
  border-color: #0056b3;
}
</style>
