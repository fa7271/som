<template>
    <div class="toggle-container" @click="toggle">
      <div class="background" :style="{ backgroundColor: backgroundColor}"></div>
      <div class="circle" :style="{left: circlePosition}"></div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        isToggled: false
      };
    },
    computed: {
      circlePosition() {
        return this.isToggled ? 'calc(100% - 40px)' : '0';
      },
      backgroundColor() {
        return this.isToggled ? 'rgba(0, 0, 139, 1)' : 'rgba(192, 192, 192, 0.5)'; // 오른쪽에 있을 때는 진한 파랑색, 왼쪽에 있을 때는 연한 회색으로 설정
      }
    },
    methods: {
      toggle() {
        this.isToggled = !this.isToggled;
      }
    }
  };
  </script>
  
  <style>
  .toggle-container {
    width: 80px; /* 너비를 줄입니다. */
    height: 40px; /* 높이를 줄입니다. */
    border: 1px solid #ccc;
    border-radius: 20px; /* 반지름을 조정하여 토글의 둥근 형태를 유지합니다. */
    position: relative;
    cursor: pointer;
    overflow: hidden; /* 내부의 요소가 넘치는 부분은 숨깁니다. */
  }
  .background {
    width: 100%;
    height: 100%;
    position: absolute;
    top: 0;
    left: 0;
    transition: background-color 0.3s ease-in-out; /* 배경색 변화에 대한 애니메이션 효과 */
  }
  .circle {
    width: 40px; /* 동그라미의 크기를 줄입니다. */
    height: 40px; /* 동그라미의 크기를 줄입니다. */
    border-radius: 50%;
    background-color: #fff; /* 흰색 배경 */
    position: absolute;
    top: 0;
    left: 0;
    transition: left 0.3s ease-in-out; /* 위치 변화에 대한 애니메이션 효과 */
  }
  </style>
  