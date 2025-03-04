<template>

    <div class="container row QnA-sc1">
        
        <div class="QnA-sc2">
            <div class="row QnA-count">
              <div class="row QnA-t">
               QnA
            </div>
            
            <div class="col QnA-Q">
              <div class="QnA-Qc1">문의 상황</div>
              <div class="QnA-Qc1">{{ count.count_with_answer + count.count_without_answer }}</div>
            </div>
            <div class="col QnA-Q">
              <div class="QnA-Qc2">완료 문의</div>
              <div class="QnA-Qc2">{{ count.count_with_answer }}</div>
            </div>
            <div class="col QnA-Q">
              <div class="QnA-Qc3">비완료 문의</div>
              <div class="QnA-Qc3">{{ count.count_without_answer }}</div>
            </div>
          
        </div>
        <hr class="QnA-hr">
        <div class="search-container">
            <select
              class="form-control btn-mini"
              v-model="searchType"
              style="width: 16%; margin-right: 13px; font-size: 1.1rem"
              name="searchType"
            >
              <option value="0">설정</option>
              <option value="1">제목</option>
              <option value="2">닉네임</option>
              <option value="3">내용</option>
            </select>
            <input class="search-input" type="text" v-model="searchValue"/>
            <div @click="fetchData()" class="search-button">검색</div>
        </div>
        <table class="table table-hover mt-3">
            <thead>
            <tr>
                <th>번호</th>
                <th>제목</th>
                <th>닉네임</th>
                <th>등록일</th>
                <th>조회수</th>
                <th>상태</th>
            </tr>
        </thead>
        <tbody>
            <tr class="QnA-table" v-for="(item, index) in paginatedList"
            :key="index" @click="detail(item.qnum)">
                
                    <td >{{ item.qnum }}</td>
                    <td >{{ item.title}}</td>
                    <td >{{ item.mnick}}</td>
                    <td >{{ formatDate(item.cdate)  }}</td>
                    <td >{{ item.hit }}</td>
                    <td >{{ item.status }}</td>

                </tr>
        </tbody>
        </table>
        <nav aria-label="Page navigation" class="pageblock">
          <ul class="pagination">
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
    </div>


</template>
<script>
import axios from 'axios';
export default {
    data() {
        return {
            list:[],
            count:[],
            listData: [], // 게시글 데이터를 담을 배열
      searchType: 0, // 검색 유형
      searchValue: "", // 검색어
      currentPage: 1,
      itemsPerPage: 10,
      pageBlockSize: 10,
      cPage: 1, // 검색어
      mnick:"",
        }
    },
    
    computed: {
    totalPages() {
      return Math.ceil(this.listData.length / this.itemsPerPage);
    },
    paginatedList() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      console.log('start:'+start)
      console.log('end:'+end)
      return this.listData.slice(start, end);
    },
    
    pageNumbers() {
      const startPage = Math.floor((this.currentPage - 1) / this.pageBlockSize) * this.pageBlockSize + 1;
      const endPage = Math.min(startPage + this.pageBlockSize - 1, this.totalPages);
      const pages = [];
      for (let i = startPage; i <= endPage; i++) {
        pages.push(i);
      }
      return pages;
    }
  },created() {
    this.fetchData();

  },
    methods: {
        fetchData(event){
            const formData = new FormData();
      formData.append('searchType', this.searchType);
      formData.append('searchValue', this.searchValue);

      console.log('확인'+this.searchType+this.searchValue)
            axios.post(`${process.env.VUE_APP_BACK_END_URL}/qna/qnalist`,formData).then(response => {
                console.log(response.data);
                this.listData = response.data;
            })
            .catch(error => {
                console.error('Error uploading image:', error);
            })
        },
        getcount(){
            console.log(`${process.env.VUE_APP_BACK_END_URL}/qna/countAns`)
            axios.get(`${process.env.VUE_APP_BACK_END_URL}/qna/countAns`)
            .then(response => {
                this.count=response.data;
                console.log(this.formatDate(this.count.cdate))
                this.count.cdate =  this.formatDate(this.count.cdate);
                console.log(this.count);
            })
            .catch(error => {
                console.error('Error uploading image:', error);
            })
        },
        formatDate(dateString) {
            const date = new Date(dateString);
            const year = date.getFullYear();
            const month = String(date.getMonth() + 1).padStart(2, '0');
            const day = String(date.getDate()).padStart(2, '0');
        return `${year}-${month}-${day}`;
        },
        detail(num){
            console.log(num)
            this.$router.push({name: 'qnadetail',params:{qnum:num}})
        },
        changePage(page) {
      if (page > 0 && page <= this.totalPages) {
        this.currentPage = page;
        console.log(page)
      }
        },
    },
    mounted() {
    
    this.getcount();
  },

}
</script>
<style scoped>


</style>

