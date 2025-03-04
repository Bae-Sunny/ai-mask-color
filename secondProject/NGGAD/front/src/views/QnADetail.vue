<template>
    <div class="container-Detail row QnA-sc1">
        <div class="col QnA-sc2">
            <h2><b>제목 : {{ qna.title }}</b></h2>
        <!-- 게시판 section -->
        <table class="detail-table">
          <tbody>
            <tr>
              <th>번호</th>
              <td>{{ qna.qnum }}</td>
            </tr>
            <tr>
              <th>제목</th>
              <td>{{ qna.title }}</td>
            </tr>
            <tr>
              <th>작성자</th>
              <td>{{ qna.mnick }}</td>
            </tr>
            <tr>
              <th>조회수</th>
              <td>{{ qna.hit }}</td>
            </tr>
            <tr>
              <th>작성일자</th>
              <td>{{ formatDate(qna.cdate) }}</td>
            </tr>
            <tr>
              <th colspan="2">내용</th>
              
            </tr>
            <tr>
                <td>{{ qna.content }}</td>
            </tr>
          </tbody>
        </table>
        <div class="admin-write">
            <h2 class="admin-write" style="margin-top: 30px"><b></b></h2>
            <form @submit.prevent="answer">
              <table class="admin-write-table">
                <tbody>
                  <tr>
                    <th><label for="content" class="form-label">답변내용</label></th>
                    <td><textarea class="form-control" id="content" rows="5" v-model="ans"></textarea></td>
                  </tr>
                </tbody>
              </table>
              <button type="submit" class="write-button" >{{updatebtn}}</button>
              {{updatecom}}
            </form>
          </div>
          

    </div>
</div>
   
   
</template>
<script>
import axios from 'axios';

export default {
    data() {
        return {
            qnum:null,
            qna:[],
            ans:null,
            updatebtn:"답변등록",
            updatecom:"",
        }
    },
    created() {
        console.log(this.$route.params.num)
        this.qnum = this.$route.params.num
        console.log(this.qnum)
    },
    mounted() {
        this.getdetail()
    },
    methods: {
        getdetail(){
            this.qnum = this.$route.params.qnum
        console.log(this.qnum)
        axios.post(`${process.env.VUE_APP_BACK_END_URL}/qna/detail/${this.qnum}`)
        .then(response => {
                this.qna =response.data
                console.log(this.qna)
                this.ans = response.data.ans
                if(this.ans != null){
                  this.updatebtn="수정하기";
                }
                
            })
            .catch(error => {
                console.error('Error uploading image:', error);
            })
        },
        answer(){
            let formData = new FormData();
            formData.append('qnum',this.qnum);
            formData.append('ans',this.ans);
            if(this.ans===null || this.ans===""){
              this.updatecom="내용을 적어주세요."
              return;
            }
            axios.post(`${process.env.VUE_APP_BACK_END_URL}/qna/updateAnsAndAdate`,formData )
            .then(response => {
            this.qna =response.data

            // this.$router.push({name: 'qnadetail',params:{qnum:this.qnum}})
            this.getdetail();
                if(this.updatebtn==="수정하기"){
                  this.updatecom = "수정이 완료되었습니다."
                };
                if(this.updatebtn==="답변등록"){
                  this.updatecom = "답변이 완료되었습니다."
                };
            })
            .catch(error => {
            console.error('Error uploading image:', error);
            if(this.updatebtn==="수정하기"){
                  this.updatecom = "수정이 실패했습니다."
                };
                if(this.updatebtn==="답변등록"){
                  this.updatecom = "답변 입력이 실패했습니다."
                };
            })
        },
        submitForm() {
        // QNA 게시판 글쓰기 폼 제출 처리 로직

        console.log("내용:", this.ans);

        // 여기에 새 글을 서버에 등록하는 로직을 구현할 수 있습니다.
        // 성공 시 처리할 내용을 추가할 수 있습니다.

        },
        formatDate(dateString) {
            const date = new Date(dateString);
            const year = date.getFullYear();
            const month = String(date.getMonth() + 1).padStart(2, '0');
            const day = String(date.getDate()).padStart(2, '0');
        return `${year}-${month}-${day}`;
        },

    }
}
</script>
<style scoped>

</style>

