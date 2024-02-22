import { createRouter,createWebHistory } from "vue-router";
import SOMMain from '@/views/SOMMain.vue';
import LoginComponent from '@/views/LoginComponent.vue';
import PostTest from '@/views/PostTest.vue';
import EmailVerify from '@/views/EmailVerify.vue';
// Routes
import { memberRoutes } from './memberRouter.js';
import { postRoutes } from './postRouter.js';

const routes = [
    {   
        // 라우터 경로
        path : '/',
        // 라우터 이름
        name : 'SOMMain',
        // 넣을 component
        component:SOMMain,
    },
    {
        path:'/login',
        name : 'Login',
        component: LoginComponent,
    },
    {
        path:'/posttest',
        name : 'PostTest',
        component: PostTest,
    },
    {
        path:'/emailverify',
        name : 'EmailVerify',
        component: EmailVerify,
    },
    ...memberRoutes,
    ...postRoutes,
]


const router = createRouter({
    // vue router는 내부적으로 두가지 방식의 히스토리 관리를 제공한다.
    // 1) history: createWebHashHistory, createHashHistory
    history: createWebHistory(),
    routes
    }
)
export default router;