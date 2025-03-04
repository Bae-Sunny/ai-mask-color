<template>
  <MainLayout>
    <div class="all-padding"></div>
    <div class="container">
    <div class="freeboard-list" style="margin:auto; text-align:center;">
      
        <h2 class="freeboard-title" style="margin-top: 30px;"><b>QNA 게시판</b></h2>
        <div class="label-group"> 
          <!-- 검색영역 -->
          <form class="d-flex" @submit.prevent="submitForm">
            <select
              class="form-control btn-mini"
              v-model="searchType"
              style="width: 14%; margin-right: 13px; font-size: 1.1rem; padding-left:20px;"
              name="searchType"
            >
              <option value="">검색              &nbsp;▼</option>
              <option value="1">제목</option>
              <option value="2">이름</option>
              <option value="3">내용</option>
            </select>
            <input
              class="form-control me-2"
              type="text"
              placeholder="Search.."
              v-model="searchValue"
              style="width: 300px; margin-right: 13px; font-size: 1.1rem"
              name="searchValue"
            />
            <button class="search-button" type="submit">검색</button>
          </form>
          <!-- //검색영역 -->
        </div>
        <table class="freeboard">
          <thead>
            <tr>
              <th class="nums">번호</th>
              <th class="title" style="width: 40%">제목</th>
              <th>작성자</th>
              <th>조회수</th>
              <th>작성일자</th>
              <th>답변일자</th>
            </tr>
          </thead>
          <tbody>          
            
            <tr
              v-for="(item, index) in paginatedList"
              :key="index"
              @click="goToDetail(item.qnum)"
            >
              <td>{{ item.qnum }}</td>
              <td class="freeboard-td-title">
                <router-link :to="'/QNAdetail?qnum=' + item.qnum"
                  ><span style="color: #005bcc;"
                    ></span
                  >&nbsp;{{ item.title
                  }}</router-link
                >
              </td>
              <td>{{ item.mnick }}</td>
              <td>{{ item.hit }}</td>
              <td>{{ formatDate(item.cdate) }}</td>
              <td>{{ formatDate(item.adate) }}</td>
            </tr>
          </tbody>
        </table>
      <div style="margin:auto; text-align:center;">
        <nav aria-label="Page navigation" class="pageblock" style="margin:auto; text-align:center;">
          <ul class="pagination" style="margin:auto; text-align:center;">
            <li class="page-item" :class="{ disabled: currentPage === 1 }">
              <a class="page-link" href="#" @click.prevent="changePage(currentPage - 1)">Previous</a>
            </li>
            <li v-for="page in pageNumbers" :key="page" class="page-item" :class="{ active: page === currentPage }">
              <a class="page-link" href="#" @click.prevent="changePage(page)">{{ page }}</a>
            </li>
            <li class="page-item" :class="{ disabled: currentPage === totalPages }">
              <a class="page-link" href="#" @click.prevent="changePage(currentPage + 1)">Next</a>
            </li>
          </ul>
        </nav>
      </div>
          <button class="write-button" type="button" @click="goToWrite">
            글쓰기
          </button>
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
  computed: {
    totalPages() {
      return Math.ceil(this.listData.length / this.itemsPerPage);
    },
     paginatedList() { // 여기 이름을 변경
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return this.listData.slice(start, end); // 실제 데이터를 slice
    },
    pageNumbers() {
      const startPage = Math.floor((this.currentPage - 1) / this.pageBlockSize) * this.pageBlockSize + 1;
      const endPage = Math.min(startPage + this.pageBlockSize - 1, this.totalPages);
      const pages = [];
      for (let i = startPage; i <= endPage; i++) {
        pages.push(i);
      }
      return pages;
    },    
  },
  created(){
    this.fetchData();      
   },
  data() {
    return {
      listData: [], // 게시글 데이터를 담을 배열      
      searchType: "", // 검색 유형
      searchValue: "", // 검색어
      cPage: 1, // 검색어
      currentPage: 1,
      itemsPerPage: 5,
      pageBlockSize: 10
    };
  },
  methods: {
    fetchData(){        
         axios.post(`${process.env.VUE_APP_BACK_END_URL}/qna/qnaList`)
        .then((resp)=>{
          console.log('1')
          console.log(resp.data)
          this.listData = resp.data                         
        })
        .catch((err)=>{
          console.log('에러뜸')
          console.log(err)
        })
      },     
    submitForm() {
      // 검색 폼 제출 로직을 구현
      // 실제로는 API를 호출하여 검색 결과를 받아와야 함           
      const formData = new FormData();
      formData.append('searchType', this.searchType);
      formData.append('searchValue', this.searchValue);
      formData.append('cPage', this.cPage);
      //alert(this.searchValue+ " : " + this.searchType);
      
      axios.post(`${process.env.VUE_APP_BACK_END_URL}/qna/qnaList`, formData)      
        .then((resp)=>{
          console.log('after /qna/qnaList')
          console.log(resp.data)
          this.listData = resp.data          
        })
        .catch((err)=>{
          console.log('에러뜸')
          console.log(err)
        })
    },
    goToDetail(qnum) {
      this.$router.push(`/QNAdetail?qnum=${qnum}`);
    },
        changePage(page) {
      if (page > 0 && page <= this.totalPages) {
        this.currentPage = page;
      }
    },
    goToWrite() {
      this.$router.push('/QNAWrite');
    },
    formatDate(dateString) {
      if (!dateString) return '';
      const date = new Date(dateString);
      const year = date.getFullYear();
      const month = ('0' + (date.getMonth() + 1)).slice(-2);
      const day = ('0' + date.getDate()).slice(-2);
      return `${year}.${month}.${day}`;
    },
  },
};
</script>
    
<style scoped>
.pageblock .pagination {
  display: flex;
  justify-content: center; /* 페이지 아이템을 가로로 중앙 정렬 */
}
</style>

    