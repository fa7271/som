// router중 post 모음

import PostCreate from '@/views/PostCreate.vue';
import PostList from '@/views/PostList.vue';
import PostListManage from '@/views/PostListManage.vue';
import PostListDetail2 from '@/views/PostDetail2.vue'

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
        name : '',
        component: PostListDetail2,
        props:true
    },
];