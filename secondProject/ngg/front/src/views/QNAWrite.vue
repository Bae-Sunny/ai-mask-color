<template>
    <MainLayout>
      <div class="all-padding"></div>
      <div class="container">
        <div class="board-write">
          <h2 class="freeboard-write" style="margin-top: 30px">
            <b>QNA게시판 글쓰기</b>
          </h2>
          <form @submit.prevent="submitForm">
            <table class="write-table">
              <tbody>
                <tr>
                  <th><label for="title" class="form-label">제목</label></th>
                  <td><input type="text" class="form-control" id="title" v-model="title"/></td>
                </tr>
                <tr>
                  <th><label for="mnick" class="form-label">작성자</label></th>
                  <td><input type="text" class="form-control" id="mnick" v-model="mnick" :placeholder="detail.mnick" readonly/></td>
                </tr>
                <tr>
                  <th><label for="content" class="form-label">내용</label></th>
                  <td><textarea class="form-control" id="content" rows="5" v-model="content"></textarea></td>
                </tr> 
                <tr>
                <th><label class="form-label">공개여부</label></th>
                <td>
                  <label>
                    <input type="radio" v-model="openyn" value="1" /> 공개
                  </label>
                  <label>
                    <input type="radio" v-model="openyn" value="0" /> 비공개
                  </label>
                </td>
              </tr>                
              </tbody>
            </table>
            <button type="submit" button class="write-button">등록</button>
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
        detail:{
          mnum:0,
          mnick:'',
        },
        title: "",
        mnick: "", // 작성자 추가
        content: "",
        mnum : 0,
        openyn: "1",
        formData: new FormData()
      };
    },
 created() {
  this.fetchData();
},

methods: {
  fetchData() {
    axios.get(`${process.env.VUE_APP_BACK_END_URL}/members/detail?mnum=1`)
      .then((resp) => {
        console.log('멤버 정보 가져오기 성공');
        console.log(resp.data);
        this.detail = resp.data;
        this.mnum = resp.data.mnum;
        this.mnick = resp.data.mnick;
        console.log(this.mnick);
      })
      .catch((err) => {
        console.error('멤버 정보 가져오기 실패:', err);
      });
  },
      submitForm() {   
        this.formData.append("title", this.title);
        this.formData.append("content", this.content);
        this.formData.append("mnick", this.mnick);
        this.formData.append("openyn", this.openyn);   
        this.formData.append("mnum", this.mnum);   

        axios.post(`${process.env.VUE_APP_BACK_END_URL}/qna/qnaWrite`, this.formData)         
        .then((resp)=>{
          console.log(resp.data)
          this.list = resp.data     
          
          alert("정상 등록")
          this.$router.push('/QNA');     
        })
        .catch((err)=>{
          console.log('에러 발생')
          console.log(err)
        })
            	
      },
    },
  };
  </script>
  
  <style scoped>
 
  </style>
  