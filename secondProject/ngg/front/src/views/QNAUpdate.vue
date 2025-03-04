<template>
  <MainLayout>
    <div class="container">
      <div class="board-write">
        <h2 class="freeboard-write" style="margin-top: 30px">
          <b>QNA게시판 수정</b>
        </h2>
        <form @submit.prevent="submitForm">
          <table class="write-table">
            <tbody>
              <tr>
                <th><label for="title" class="form-label">제목</label></th>
                <td><input type="text" class="form-control" id="title" v-model="board.title"/></td>
              </tr>
              <tr>
                <th><label for="writer" class="form-label">작성자</label></th>
                <td><input type="text" class="form-control" id="writer" v-model="board.mnick" placeholder="작성자1"/></td>
              </tr>
              <tr>
                <th><label for="content" class="form-label">내용</label></th>
                <td><textarea class="form-control" id="content" rows="5" v-model="board.content"></textarea></td>
              </tr>
              <tr>
                <th><label class="form-label">공개여부</label></th>
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
          <button type="submit" class="write-button">저장</button>
        </form>
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
        title: "",
        mnum: 0,
        content: "",
        openyn: "",
        mnick: "",        
      },
      qnum: 0,
    };
  },
  created() {
    const qnum = this.$route.query.qnum;
    this.fetchData(qnum);
    this.qnum = qnum;
  },
  methods: {
    fetchData(qnum) {
      axios.get(`${process.env.VUE_APP_BACK_END_URL}/qna/qnaDetail?qnum=${qnum}`)
        .then((resp) => {
          this.board = resp.data;
        })
        .catch((err) => {
          console.error('에러 발생', err);
        });
    },

    submitForm() {
      console.log("Submitting form with board data:", this.board);

      axios.post(`${process.env.VUE_APP_BACK_END_URL}/qna/qnaUpdate`, this.board)
        .then((resp) => {
          console.log("서버 응답:", resp.data);
          if (resp.status === 200) {
            alert("정상 수정");
            this.$router.push('/QNA');
          } else {
            alert("수정 실패");
            console.error("수정 실패:", resp.data);
          }
        })
        .catch((err) => {
          console.error('에러 발생', err);
          alert("서버 에러가 발생했습니다.");
        });
    }
  }
};
</script>

<style scoped>
</style>
