// router중 post 모음

import PostCreate from '@/views/PostCreate.vue';
import PostList from '@/views/PostList.vue';
import PostListManage from '@/views/PostListManage.vue';
import PostListDetail from '@/views/PostDetail.vue'

export const postRoutes = [
    {
        path:'/postcreate',
        name : 'PostCreate',
        component: PostCreate,
    },
    {
        path:'/postlist',
        name : 'PostList',
        component: PostList,
    },
    {
        path:'/posts/manage',
        name : 'PostListManage',
        component: PostListManage,
    },
    {
        path:'/posts/:id/detail',
        name : 'PostListDetail',
        component: PostListDetail,
        props:true
    },
];