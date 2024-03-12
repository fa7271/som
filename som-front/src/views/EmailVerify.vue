<template>
  <div>
    <h2>EmailVerify</h2>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  created(){
    this.doVerify();
  },
  methods:{
    async doVerify(){
      try{


        const search = window.location.search;
        console.log(search);

        const response = await axios.get(`${process.env.VUE_APP_API_BASE_URL}/admin/account/verify-code${search}`);

        if(response){
          alert("회원가입이 완료되었습니다.")
          window.location.href ="/login";
        }else{
          alert("회원가입 인증이 실패됐습니다.")
        }

      }catch(error){
        console.log(error)
        alert("회원가입 인증이 실패됐습니다.")
      }
    }
  }
}
</script>