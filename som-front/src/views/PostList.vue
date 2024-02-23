<template>
    <PostListComponent
    :isAdmin="false"
    pageTitle="게시글 목록"
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