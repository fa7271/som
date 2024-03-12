// router중 member 모음

import MemberList from '@/views/MemberList.vue';
import MemberCreate from '@/views/MemberCreate.vue';
import MyPage from '@/views/MyPage.vue';
import MemberListDetail from '@/views/MemberDetail'

export const memberRoutes = [
    {   
        path : '/members',
        name : 'memberList',
        component: MemberList,
    },
    {   
        path : '/member/create',
        name : 'MemberCreate',
        component: MemberCreate,
    },
    {
        path:'/mypage',
        name : 'MyPage',
        component: MyPage,
    },
    {
        path:'/admin/member/detail/:id',
        name : 'MemberListDetail',
        component: MemberListDetail,
        props:true
    },
    {
        path:'/admin/member/verify-code',
        name : 'EmailVerify',
        component: EmailVerify,
    },


];