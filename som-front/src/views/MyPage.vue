<template>
  <div class="post-reply">
    <div data-tistory-react-app="Namecard">
      <div class="tt_box_namecard">
        <div class="tt_cont">
          <a href="https://au1802.tistory.com" class="tt_tit_cont">장준혁 개발공부블로그</a>
          <a href="https://au1802.tistory.com" class="tt_desc">GreatJang 님의 블로그입니다.</a>
        </div>
        <a href="https://au1802.tistory.com" class="tt_wrap_thumb">
          <span class="tt_thumb_g" style="background-image: url(&quot;https://img1.daumcdn.net/thumb/S56x56/?scode=mtistory2&amp;fname=https%3A%2F%2Ft1.daumcdn.net%2Ftistory_admin%2Fstatic%2Fmanage%2Fimages%2Fr3%2Fdefault_L.png&quot;);"></span>
        </a>
      </div>
    </div>
    <div id="entry74Comment" class="tt-comment-cont">
      <div data-tistory-react-app="Comment">
        <div class="tt-box-total">
          <span class="tt_txt_g">댓글</span>
          <span class="tt_num_g">2</span>
        </div>
        <div class="tt-area-reply">
          <ul class="tt-list-reply">
            <li class="tt-item-reply rp_general tt_item_secret" v-for="(comment, index) in comments" :key="index">
              <div class="tt-wrap-cmt">
                <div class="tt-box-thumb">
                  <a :href="comment.authorLink">
                    <span class="tt-thumbnail" :style="'background-image: url(&quot;' + comment.authorThumbnail + '&quot;);'"></span>
                  </a>
                </div>
                <div class="tt-box-content">
                  <div class="tt-box-meta">
                    <a :href="comment.authorLink" target="_blank" rel="noreferrer" class="tt-link-user">{{ comment.author }}</a>
                    <span class="tt_img_area_reply tt_ico_lock">비밀댓글</span>
                  </div>
                  <div class="tt-wrap-desc">
                    <p class="tt_desc">{{ comment.content }}</p>
                  </div>
                  <div class="tt-wrap-info">
                    <span class="tt_date">{{ comment.date }}</span>
                    <span class="tt-wrap-link-comment">
                      <a href="#" class="tt-link-comment"><span class="tt_txt_g">답글</span></a>
                    </span>
                  </div>
                  <div class="tt-box-modify">
                    <button type="button" class="tt_img_area_reply tt-button-modify">더보기</button>
                    <ul class="tt-list-modify">
                      <li><a href="#">삭제</a></li>
                      <li><a href="#">신고</a></li>
                      <li><a href="#">링크복사</a></li>
                    </ul>
                  </div>
                  <div class="tt_box_pwd" style="display: none;">
                    <form class="tt_form_pwd">
                      <fieldset>
                        <legend class="screen_out">비밀번호 입력</legend>
                        <input class="tt_inp_g" type="password" title="비밀번호" placeholder="비밀번호를 입력하세요." maxlength="12" value="">
                        <button type="submit" class="tt_btn_submit" disabled=""><span class="tt_img_area_reply tt_ico_check">입력하기</span></button>
                      </fieldset>
                    </form>
                  </div>
                </div>
              </div>
            </li>
          </ul>
        </div>
      </div>
      <form style="margin: 0px;">
        <div class="tt-area-write">
          <div class="tt-box-thumb">
            <span class="tt-thumbnail" style="background-image: url(&quot;https://img1.daumcdn.net/thumb/C88x88/?fname=https%3A%2F%2Ft1.daumcdn.net%2Ftistory_admin%2Fstatic%2Fmanage%2Fimages%2Fr3%2Fdefault_S.png&quot;);"></span>
          </div>
          <div class="tt_wrap_write">
            <div class="tt-box-textarea">
              <div class="tt-inner-g">
                <span class="tt_txt_user">GreatJang</span>
                <label for="comment" class="screen_out">댓글</label>
                <div contenteditable="true" placeholder="내용을 입력하세요." class="tt-cmt"></div>
              </div>
            </div>
            <div class="tt-box-write">
              <label class="tt-xe-label">
                <input type="checkbox" name="secret" id="secret">
                <span class="tt_img_area_reply tt-xe-input-helper"></span>
                <span class="tt-xe-label-text">비밀글</span>
              </label>
              <button type="submit" class="tt-btn_register" disabled="">등록</button>
            </div>
          </div>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      comments: [
        {
          author: "windy7271",
          authorLink: "https://windy7271.tistory.com",
          authorThumbnail: "https://img1.daumcdn.net/thumb/C88x88/?fname=https%3A%2F%2Ft1.daumcdn.net%2Ftistory_admin%2Fstatic%2Fmanage%2Fimages%2Fr3%2Fdefault_S.png",
          content: "정리 너무 깔끔한데 준혁?",
          date: "2024. 2. 4. 11:45"
        },
        {
          author: "GreatJang",
          authorLink: "https://au1802.tistory.com",
          authorThumbnail: "https://img1.daumcdn.net/thumb/C88x88/?fname=https%3A%2F%2Ft1.daumcdn.net%2Ftistory_admin%2Fstatic%2Fmanage%2Fimages%2Fr3%2Fdefault_S.png",
          content: "ㅎㅎㅎ땡큐",
          date: "2024. 2. 4. 22:33"
        }
      ]
    };
  }
}
</script>
