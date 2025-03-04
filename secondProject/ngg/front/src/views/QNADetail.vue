<template>
  <MainLayout>
    <div class="container">
      <div class="board-detail">
        <h2><b>QNA 게시판 상세보기</b></h2>
        <!-- 게시판 섹션 -->
        <table class="detail-table">
          <tbody>
            <tr>
              <th>번호</th>
              <td>{{ board.qnum }}</td>
            </tr>
            <tr>
              <th>제목</th>
              <td>{{ board.title }}</td>
            </tr>
            <tr>
              <th>작성자</th>
              <td>{{ board.mnick }}</td>
            </tr>
            <tr>
              <th>조회수</th>
              <td>{{ board.hit }}</td>
            </tr>
            <tr>
              <th>작성일자</th>
              <td>{{ formatDate(board.cdate) }}</td>
            </tr>
            <tr>
              <th>내용</th>
              <td v-html="board.content"></td>
            </tr>
            <tr>
              <th>공개여부</th>
              <td>
                <label>
                  <input type="radio" v-model="board.openyn" value="1" /> 공개
                </label>
                <label>
                  <input type="radio" v-model="board.openyn" value="0" /> 비공개
                </label>
              </td>
            </tr>
          </tbody>
        </table>

        <!-- Divider Line -->
        <hr class="divider" />

        <!-- 버튼 그룹 -->
        <div class="button-group">
          <router-link to="/QNA" class="back-button">목록으로 돌아가기</router-link>
          <router-link v-if="board.ans === null" :to="`/QNAUpdate?qnum=${board.qnum}`" class="update-button">
            <span>수정</span>
          </router-link>
          <button type="button" class="delete-button" @click.prevent="deleteQna">삭제</button>
        </div>

        <!-- Divider Line -->
        <hr class="divider" />

        <!-- 관리자 섹션 -->
        <div v-if="isAdmin" v-show="board.ans" class="admin-write">
          <h2><b>작성자 문의에 답변</b></h2>
          <form @submit.prevent="submitForm">
            <table class="admin-write-table">
              <tbody>
                <tr>
                  <th><label for="adate" class="form-label">답변일자</label></th>
                  <td>{{ formatDate(board.adate) }}</td>
                </tr>
                <tr>
                  <th><label for="ans" class="form-label">답변내용</label></th>
                  <td><textarea class="form-control" id="ans" rows="5" v-model="board.ans"></textarea></td>
                </tr>
              </tbody>
            </table>
          </form>
        </div>
      </div>
    </div>
  </MainLayout>
</template>

<script>
import MainLayout from "@/layouts/MainLayout.vue";
import axios from 'axios';

export default {
  components: {
    MainLayout,
  },
  data() {
    return {
      board: {
        qnum: '',
        title: '',
        mnick: '',
        hit: '',
        cdate: '',
        content: '',
        openyn: null, // 기본값 null로 변경
        ans: null // 기본값 null로 변경
      },
      isAdmin: true,
      formData: new FormData()
    };
  },
  created() {
    const qnum = this.$route.query.qnum;
    this.fetchData(qnum);
  },
  methods: {
    fetchData(qnum) {
      axios.get(`${process.env.VUE_APP_BACK_END_URL}/qna/qnaDetail?qnum=${qnum}`)
        .then((resp) => {
          this.board = resp.data;
        })
        .catch((err) => {
          console.error('에러 발생:', err);
        });
    },
    submitForm() {
      this.formData.append("ans", this.board.ans);
      this.formData.append("qnum", this.board.qnum);

      axios.post(`${process.env.VUE_APP_BACK_END_URL}/qna/upd`, this.formData)
        .then((resp) => {
          console.log(resp.data);
          alert("답변 정상 등록");
          this.$router.push('/QNA');
        })
        .catch((err) => {
          console.error('에러 발생:', err);
        });
    },
    deleteQna() {
    axios.get(`${process.env.VUE_APP_BACK_END_URL}/qna/qnaDelete?qnum=${this.board.qnum}`)
    .then((resp) => {
      console.log(resp.data);
      alert("삭제 정상 완료");
      this.$router.push('/QNA');
    })
    .catch((err) => {
      console.error('에러 발생:', err);
    });
},
    formatDate(dateString) {
      if (!dateString) return '';
      const date = new Date(dateString);
      const year = date.getFullYear();
      const month = date.getMonth() + 1;
      const day = date.getDate();
      const hours = date.getHours();
      const minutes = date.getMinutes();
      return `${year}년 ${month}월 ${day}일 ${hours}시 ${minutes}분`;
    },
  },
};
</script>

<style scoped>
/* 필요한 스타일 지정 */
</style>
