<div align="center">

 ![header](https://capsule-render.vercel.app/api?type=shark&height=300&text=📬SOM📬&desc=%20%20Sound%20of%20mind&textBg=false&fontColor=FFFFFF&section=header&fontSize=70&fontAlign=50&fontAlignY=49&animation=fadeIn)

 **[플레이 데이터] 한화시스템 BEYOND SW캠프 3기**/☁️**TEAM SOM**☁️

 </div>

<br>

<div align="center">
 
## &nbsp;📬[SOM데모 사이트 바로가기](https://www.naver.com)

</div>

<div align="center">

 ## ☁️팀원☁️

[**이혜선** 🐴](https://github.com/hyesunlee30) (표준화개발)
<br>
[**이창선** 🐷](https://github.com/keepself) (표준화개발)
 <br>
[**신보석** 🐭](https://github.com/fa7271) (서비스개발)
<br>
[**장준혁** 🐰](https://github.com/GreatJang) (서비스개발)


<br>

---

</br>

##  💻STACK

</div>


<div align=center> 

<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/vue.js-4FC08D?style=for-the-badge&logo=vue.js&logoColor=white">
<img src="https://img.shields.io/badge/linux-FCC624?style=for-the-badge&logo=linux&logoColor=black">
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
<img src="https://img.shields.io/badge/mariaDB-003545?style=for-the-badge&logo=mariaDB&logoColor=white">
<img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=Redis&logoColor=white">
<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">

<br>

---

<br>

</div>

<br><br><br>

## 🌠프로젝트 소개

**Q)어떤계기에서 이 프로그램이 시작되었나요?**

<br>

개발자를 움직이는 마법의 단어가 있습니다 **“고민이 있어요”**
개발자 4명이 모여서 한 달 동안 개발을 하는데 꼭 도움이 되는 개발을 하고 싶다고 생각했습니다.
서비스 개발의 목표이자 저희 프로젝트의 이름인
**The Soud Of Mind** 는 한 에피소드에서 시작되는데요
누군가 다음날이 오기전 선생님께 “저는 이만 떠납니다.” 라고 남긴거였죠.
처음엔 누가 남긴지 모르는 메세지였지만 꼬리가 밟혔고 결국 저희 조원 창선이인것이 들통났습니다.

**그 점에서 저희 팀은 아이디어를 얻었습니다!**
대부분은 자신의 고민을 말하기 싫어할 것 입니다.
혹은 자신의 고민을 말할 곳이 없을 수도 있을것입니다.
하지만 해결은 하고싶을때.
**“익명의 고민을 들어주고 방향을 제시해주는 서비스”**
그점을 제안하는 것 입니다.

## 🎯프로젝트 목표

**Q)이 프로그램을 통해서 도달하고싶은 목표는 무었인가요?**

<br>

**개발**에 빗대어 저희 목표를 알려드리자면,
사람들의 스피드는 다르고, 선행 학습의 양도 다릅니다. 섣부르게 재능과 능력을 판단해서는 안된다고 생각하는데요.
근데 혼자 가면 힘들 수 있으니 같이 가자는 게 저의 생각입니다.
개발자는 고민을 해결해주는 사람이라고 생각합니다. 고민이 있는 사람들의 목소리를 들을 수 있는 그리고 그 고민을 해결해줄 수 있는 개발자로 성장하길 바라는 마음으로 만들었습니다.
**단지 개발 기술만이 아니라, 자신의 힘듦, 속마음 등을 나눌 수 있고
따뜻한 마음을 전할 수 있는 !고민 전문! 게시판이 되었으면 합니다.**

<br>

## 🔍 요구사항 명세서

<details>
<summary>자세히 보기(click)</summary>


|구분|요구사항 명|요구사항 설명|
|:---:|:---:|:---:|
|**batch**|member rangking update|매일 새벽에 배치로 30 일전부터 어제까지의 총 댓글 개수 * 10 점, 총 글 개수 * 3 점으로 계산해서 랭킹|
|**MSA**|MSA|모놀리식 구조에서 springbatch 적용을 위한 MSA 구조 적용 |
||빌드|docker-compose를 이용한 docker 로컬 배포|
|**Common**|공통 에러 처리|공통 에러 처리|
|**admin**|사용자 비활성화|사용자 관리 페이지에서 비활성화 버튼 선택시 사용자 active =false|
||security 적용|filter, config 작성 및 확인|
||사용자 비활성화|사용자 관리 페이지에서 비활성화 버튼 선택시 사용자 active =false|
||사용자 페이징 리스트|최신순 페이징 된 사용자 리스트 반환 검색필터(제목,닉네임) 적용|
||사용자 수정|사용자 수정버튼 클릭시 사용자 수정 페이지로 이동해 수정(닉네임)|
||사용자 상세보기|사용자 email로 검색된 사용자 상세 정보 보기|
||회원가입|닉네임, 이메일, 패스워드로 가입 이메일 인증으로 인증코드 전달 후 인증코드 확인하여 active =true, redis에 인증 토큰 저장|
||로그인|이메일, 패스워드로 로그인 후 토큰 반환 |
||로그아웃|토큰과 롤 삭제 후 사용자 리스트로 이동|
||패스워드 찾기|이메일 인증으로 패스워드 리셋 후 다시 셋팅|
||내부통신|board에 rangking,nickname 정보 전달|이메일LIST 파라미터로 받아 랭킹,닉네임 반환|
||내부통신|board에 rangkin top 10 전달|board에 rangkin top 10 전달|
|**board**|게시글 작성|게시글 작성시 욕설, 링크 를 포함하면 작성은 가능하되 "*" 표시로 바뀌어서 표시|
||게시글 작성|게시글을 하루(당일, 00 시 넘어가면 초기화)에 5 개 초과로 작성할 수 없음|
||게시글 작성|한 게시글 좋아요는 한 개만 가능하다.|
||게시글 조회게시글 조회수, 내가 쓴 게시글에서는 조회수가 추가 되지 않는다.|
||게시글 조회|게시글, 댓글 등에서 사용자 정보를 보여주는 부분에 랭킹도 함께 보여준다.|
||게시글 리스트 페이징 및 검색|최신순, 게시글 리스트를 글 10개씩 페이징하여 리스트 출력, 검색필터(제목,닉네임)를 적용|
||랭킹 TOP 10|랭킹 TOP 10 보여주기|
||일간 조회수 TOP 10|일간 조회수 TOP 10 보여주기|
||주간 조회수 TOP 10|주간 조회수 TOP 10 보여주기|
||월간 조회수 TOP 10|월간 조회수 TOP 10 보여주기|
||댓글 TOP 10|댓글수 TOP 10 보여주기|
||게시글 수정|게시글을 수정할 수 있다. 제목과 내용, 본인과 어드민 가능|
||게시글 삭제|게시글을 삭제할 수 있다. 본인과 어드민 가능|
||게시글 작성|vue 관련 에디터 적용|
|**comment**|댓글 리스트|게시글에서 최신순으로 댓글을 확인할 수 있다.|
||댓글 작성|"게시글에 댓글을 달 수 있는 부분이 있고 댓글을 작성 후 다시 상세정보를 호출해 댓글을 확인할 수 있다"|
||댓글 수정|자신이 단 댓글을 수정할 수 있다.|
||댓글 삭제|"자신이 단 댓글을 삭제할 수 있다. 어드민의 경우 자신이 작성한 댓글이아니어도 삭제할 수 있다"|
</details>

<br>

---

</br>

##  📋기술 설계도

<div align="center">

<img src="https://github.com/fa7271/som/assets/150704738/dec95846-474d-43d9-99e5-15a9ead76fc8)" width="800" height="600"/>

</div>

<br>

---

</br>

##  📋ERD 

<div align="center">
 
<img src="https://github.com/fa7271/som/assets/150704738/5722a510-9e79-4e60-ae85-aca6912cfdc0" width="800" height="600"/>

</div>

<br>

---

</br>

##  📝테스트 및 결과

<details>
<summary>테스트 및 결과보기(click)</summary>
</details>

![footer](https://capsule-render.vercel.app/api?type=shark&height=200&text=Thank%20you&section=footer&reversal=true&fontColor=FFFFFF&fontAlign=83&fontAlignY=89&fontSize=38)



   




