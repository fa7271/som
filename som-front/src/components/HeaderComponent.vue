<template>
    <nav class="navbar navbar-expand-lg bg-light navbar-light">
        <div class="navbar-collapse w-100 order-1 order-md-0 dual-collapse2">
            <ul class="navbar-nav mr-auto" v-if="userRole === 'ADMIN'">
                <li class="nav-item">
                    <a class="nav-link" href="/members">회원관리</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/posts/manage">게시글 관리</a>
                </li>
            </ul>
        </div>
        <div class="mx-auto order-0">
            <a class="navbar-brand mx-auto" href="/">
                <img src="@/assets/som.png"/>
            </a>
        </div>
        <div class="navbar-collapse w-100 order-3 dual-collapse2">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link" href="/posttest">테스트용 페이지</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/postlist">게시글 목록</a>
                </li>
                <li class="nav-item" v-if="isLogin">
                    <a class="nav-link" href="/mypage">MyPage</a>
                </li>
                <li class="nav-item" v-if="!isLogin">
                    <a class="nav-link" href="/member/create">회원가입</a>
                </li>
                <li class="nav-item" v-if="!isLogin">
                    <a class="nav-link" href="/login">로그인</a>
                </li>
                <li class="nav-item" v-if="isLogin">
                    <a class="nav-link" href="#" @click="doLogout">로그아웃</a>
                </li>
            </ul>
        </div>
    </nav>
</template>

<script>

export default {
    data() {
        return {
            isLogin: false,
            userRole: null
        }
    },
    created() {
        if (localStorage.getItem("token")) {
            this.isLogin = true;
            this.userRole = localStorage.getItem("role");
        }
    },
    methods: {
        doLogout() {
            localStorage.clear();
            window.location.reload();
        }
    },
}
</script>

<style scoped>
/* 추가된 CSS 스타일 */
.navbar {
    border-bottom: 1px solid #ddd;
}
</style>
