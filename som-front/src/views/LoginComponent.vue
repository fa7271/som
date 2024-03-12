<template>
  <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
    <div class="sm:mx-auto sm:w-full sm:max-w-sm bg-white rounded-lg shadow-lg">
      <div class="px-6 py-12">
        <img class="mx-auto h-15 w-auto" src="@/assets/sommain.png" alt="Your Company" />
        <h2 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">WE ARE Sound Of Mind</h2>
      </div>

      <div id="update-form" class="px-6 py-8">
        <form @submit.prevent="doLogin" class="space-y-6" action="#" method="POST">
          <div>
            <label for="email" class="block text-sm font-medium leading-6 text-gray-900">Email address</label>
            <div class="mt-2">
              <input v-model="email" type="email" class="block w-full rounded-md border-0 py-1.5 pl-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
            </div>
          </div>

          <div>
            <div class="flex items-center justify-between">
              <label for="password" class="block text-sm font-medium leading-6 text-gray-900">Password</label>
              <div class="text-sm">
                <a href="passwordchange" class="font-semibold text-indigo-600 hover:text-indigo-500">Forgot password?</a>
              </div>
            </div>
            <div class="mt-2">
              <input v-model="password" type="password" class="block w-full rounded-md border-0 py-1.5 pl-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
            </div>
          </div>

          <div>
            <button type="submit" class="flex w-full justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">Sign in</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import {jwtDecode} from 'jwt-decode';
export default {
    data(){
        return{
            email:"",
            password:""
        }
    },
    methods:{
      async doLogin(){
        // 2가지의 예외가능성

        // 1. 200번대 상태값 + 토큰x 
        // 2. 200번대 상태값x -> 분기처리
      try{
        const loginData = {email:this.email, password:this.password};
        const response = await axios.post(`${process.env.VUE_APP_API_BASE_URL}/admin/account/signin`,loginData);
        // axios.post(`http://localhost:8000/admin/account/signin`,loginData).then((value)=> {
        //   console.log(value)
        // })
        const token = response?.data?.data.token
        console.log(token)
        if(token){
          const decoded = jwtDecode(token)
          localStorage.setItem("token",token) // 로그인 성공시
          localStorage.setItem("role",decoded.role)
        // created함수는 reload될때 1번만 실행되는 hook함수
        // 그런데, router.push를 통한 화면전환은 reload를 실행시키지 않으므로, created함수 호출이 되지 않음
          window.location.href ="/";
        }else{
           alert("Loign failed")
        }
        
      }catch(error){
        const error_message = error.response.data.error_message
        if (error_message){
          console.log(error_message)
          alert(error_message)
        }
        else{
          console.log(error)
          alert("Loign failed")
        }
      }
    }
  }
}
</script>