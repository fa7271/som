<template>
    <PostListComponent
    :isAdmin="true"
    pageTitle="게시글 관리"
    />
</template>

<script>
import PostListComponent from '@/components/PostListComponent.vue';
export default {
    components:{
        PostListComponent
    },
}
</script>