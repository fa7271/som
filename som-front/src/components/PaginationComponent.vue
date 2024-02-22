<template>
  <ul class="pagination">
    <!-- 이전 버튼 -->
    <li class="page-item" :class="{ 'disabled': currentPage === 0 }">
      <a class="page-link" href="#" @click.prevent="changePage(currentPage - 1)">이전</a>
    </li>
    <!-- 페이지 번호 -->
    <li v-for="page in visiblePages" :key="page" :class="{ 'page-item': true, 'active': page === currentPage }">
      <a class="page-link" href="#" @click.prevent="changePage(page)">{{ page + 1 }}</a>
    </li>
    <!-- 다음 버튼 -->
    <li class="page-item" :class="{ 'disabled': currentPage === totalPages - 1 }">
      <a class="page-link" href="#" @click.prevent="changePage(currentPage + 1)">다음</a>
    </li>
  </ul>
</template>
<script>
export default {
  props: {
    currentPage: {
      type: Number,
      required: true
    },
    totalPages: {
      type: Number,
      required: true
    },
    visiblePageCount: {
      type: Number,
      default: 5 // 보여질 페이지 수를 설정합니다.
    }
  },
  computed: {
    visiblePages() {
      const middle = Math.floor(this.visiblePageCount / 2);
      let startPage = this.currentPage - middle;
      if (startPage < 0) startPage = 0;
      let endPage = startPage + this.visiblePageCount - 1;
      if (endPage >= this.totalPages) {
        endPage = this.totalPages - 1;
        startPage = Math.max(0, endPage - this.visiblePageCount + 1);
      }
      return Array.from({ length: endPage - startPage + 1 }).map((_, index) => startPage + index);
    }
  },
  methods: {
    changePage(page) {
      this.$emit('page-change', page);
    }
  }
}
</script>
<style scoped>
.pagination {
  display: flex !important;
  list-style: none !important;
  padding: 0 !important;
  justify-content: center; /* 가운데 정렬 */
}
.page-item {
  margin-right: 5px !important;
}
.page-link {
  text-decoration: none !important;
  color: #555 !important; /* 회색 */
  background-color: #ddd !important; /* 연한 회색 */
  border: 1px solid #ccc !important; /* 연한 회색 */
  padding: 6px 12px !important;
  border-radius: 4px !important;
  transition: all 0.3s ease !important;
}
.page-link:hover {
  background-color: #ccc !important; /* 연한 회색 */
  border-color: #bbb !important; /* 연한 회색 */
}
.page-link:focus {
  box-shadow: 0 0 0 0.2rem rgba(85, 85, 85, 0.25) !important; /* 회색 */
}
.page-item.disabled .page-link {
  pointer-events: none !important;
  background-color: #ddd !important; /* 연한 회색 */
  border-color: #ccc !important; /* 연한 회색 */
  color: #aaa !important; /* 연한 회색 */
}
.page-item.active .page-link {
  z-index: 1 !important;
  color: #fff !important;
  background-color: #555 !important; /* 회색 */
  border-color: #444 !important; /* 짙은 회색 */
}
</style>