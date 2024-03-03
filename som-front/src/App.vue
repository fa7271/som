  <template>
  <HeaderComponent/>
  <div>
    <router-view/>
  </div>
  <FooterComponent/>
</template>

<script>
// 상대경로
// import HomeComponent from "./components/HomeComponent.vue";
// 절대경로 : @는 jsconfig의 경로 키워드로 선언
import HeaderComponent from "@/components/HeaderComponent.vue";
import FooterComponent from "@/components/FooterComponent.vue";
export default {
    // 사용할 하위 컴포넌트 목록 나열
    components:{
      HeaderComponent,
      FooterComponent,
    }
}
</script>

