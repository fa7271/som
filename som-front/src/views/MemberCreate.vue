<template>
    <div class="container">
      <div class="row justify-content-center mt-5">
        <div class="col-md-6">
          <div class="card">
            <div class="card-header text-center">회원가입</div>
            <div class="card-body">
              <form @submit.prevent="submitForm">
                <div class="mb-3">
                  <label for="email" class="form-label">이메일 주소</label>
                  <input type="email" class="form-control" id="email" v-model="email" required>
                </div>
                <div class="mb-3">
                  <label for="nickname" class="form-label">닉네임</label>
                  <input type="text" class="form-control" id="nickname" v-model="nickname" required>
                </div>
                <div class="mb-3">
                  <label for="password" class="form-label">비밀번호</label>
                  <input type="password" class="form-control" id="password" v-model="password" required>
                </div>
                <div class="mb-3">
                  <label for="confirmPassword" class="form-label">비밀번호 확인</label>
                  <input type="password" class="form-control" id="confirmPassword" v-model="confirmPassword" required>
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
  export default {
    data() {
      return {
        email: '',
        nickname: '',
        password: '',
        confirmPassword: ''
      };
    },
    methods: {
      submitForm() {
        // 여기에 회원가입 양식 제출에 대한 로직을 추가하세요.
        console.log('회원가입 양식이 제출되었습니다.');
        console.log('Email:', this.email);
        console.log('Nickname:', this.nickname);
        console.log('Password:', this.password);
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
  