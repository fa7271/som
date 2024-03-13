import { createApp } from 'vue';
import App from './App.vue';
import './index.css';
import router from '@/router/index.js'
import '@/assets/css/bootstrap.min.css' // bootstrap import
import axios from 'axios';
import '@fortawesome/fontawesome-free/css/all.css';

const app = createApp(App);
app.use(router);
app.mount('#app');

// 401응답의 경우 interceptor를 통해 공통적으로 토큰 제거 후 로그 관리
axios.interceptors.response.use(response => response, error =>{
    if(error.response && error.response.status === 401||error.response && error.response.status === 403){
        localStorage.clear();
        alert("로그인 시간이 만료되었습니다. 다시 로그인 해주세요")
        window.location.href = "/login";
    }
    return Promise.reject(error)
})