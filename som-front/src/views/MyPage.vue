<template>
  <div class="overflow-hidden bg-white py-40 sm:py-52">
    <div class="mx-auto max-w-5xl px-6 lg:px-8">
      <div class="mx-auto grid max-w-2xl grid-cols-1 gap-y-16 sm:gap-y-20 lg:mx-0 lg:max-w-none lg:grid-cols-2 lg:gap-x-16">
        <div class="lg:pt-4">
          <div class="lg:max-w-lg">
            <h2 class="text-lg font-semibold leading-7 text-indigo-600">sound of mind</h2>
            <p class="mt-2 text-2xl font-bold tracking-tight text-gray-900 sm:text-3xl">SOM서비스에 표시되는 내정보</p>
            <p class="mt-6 text-lg leading-8 text-gray-600">개인 정보 및 이를 관리하기 위한 옵션입니다.</p>
            <dl class="mt-10 max-w-xl space-y-8 text-lg leading-7 text-gray-600 lg:max-w-none">
              <div v-for="feature in features" :key="feature.name" class="relative pl-9">
                <dt class="inline font-semibold text-gray-900">
                  <component :is="feature.icon" class="absolute left-1 top-1 h-4 w-4 text-indigo-600" aria-hidden="true" />
                  {{ feature.name }}
                </dt>
                {{ ' ' }}
                <dd class="inline">{{ feature.description }}</dd>
              </div>
            </dl>
          </div>
        </div>
        <div class="card bg-white rounded-xl shadow-xl ring-1 ring-gray-400/10 w-full md:w-[32rem] lg:w-auto p-4 rounded-lg">
          <div class="card-header text-center"><h2 class="mt-2 text-2xl font-bold tracking-tight text-gray-900 sm:text-3xl">회원 정보</h2></div>
          <div class="card-body py-8">
            <div class="mb-4">
              <strong>이름:</strong> {{ memberinfo.nickname }}
            </div>
            <div class="mb-4">
              <strong>이메일:</strong> {{ memberinfo.email }}
            </div>
            <div class="mb-4">
              <strong>역할:</strong> {{ memberinfo.role }}
            </div>
            <div class="mb-4">
              <strong>랭킹:</strong> {{ memberinfo.ranking }}
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
const memberinfo = ref({});
const fetchMember = async () => {
  try {
    const token = localStorage.getItem('token');
    const response = await axios.get(`${process.env.VUE_APP_API_BASE_URL}/admin/member/mypage`, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    memberinfo.value = response.data.data;
  } catch(error) {
    alert("인증 시간이 만료되었습니다.");
    localStorage.clear();
    window.location.href = "/login";
  }
};
onMounted(fetchMember);
</script>