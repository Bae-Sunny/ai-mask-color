<template>
<div class="Memberlist-sc1">
    <div class="Memberlist-sc2">
    <div class="all-padding"></div>
    <div class="container mt-5 membercon">
      <h1 style="padding-top: 30px; margin-bottom: 10px;">회원 목록</h1>
      <div class="search-container">
      <select
              class="form-control btn-mini"
              v-model="searchType"
              style="width: 100px; margin-right: 13px; font-size: 1.1rem"
              name="searchType"
            >
              <option value="0">설정</option>
              <option value="1">이메일</option>
              <option value="2">이름</option>
              <option value="3">닉네임</option>
            </select>
            <input class="search-input" type="text" v-model="searchValue"/>
            <div @click="fetchMembers()" class="search-button">검색</div>
          </div>
      <table class="table table-bordered membertable">
        <thead>
          <tr>
            <th>이메일</th>
            <th>이름</th>
            <th>닉네임</th>
            <th>성별</th>
            <th>생년월일</th>
            <th>퍼스널컬러</th>
            <th>사진이름</th>
            <th>회원상태</th>
            <th>가입날짜</th>
            <th>회원관리</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, index) in paginatedList"
          :key="index">
            <td>{{ item.memail }}</td>
            <td>{{ item.mname }}</td>
            <td>{{ item.mnick }}</td>
            <td>{{ item.mgender }}</td>
            <td>{{ formatDate(item.mbirth) }}</td>
            <td>{{ item.season }}</td>
            <td>{{ item.mimgn }}</td>
            <td>{{ item.status }}</td>
            <td>{{ formatDate(item.mdate) }}</td>
            <td>
              <button class="btn btn-primary btn-sm" @click="showModal(item)">수정</button>
              <button class="btn btn-danger btn-sm" @click="deleteMember(item)">삭제</button>
            </td>
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
      <button class="btn btn-primary mb-3" @click="fetchMembers">새로고침</button>

      <!-- 편집 모달 -->
      <modal-prop v-if="popState" @close="closeModal" :member="currentMember"></modal-prop>
    </div>
</div>
</div>
</template>
<script>
import ModalProp from "../components/ModalProp.vue";
import axios from 'axios';
export default {
    components: {
    ModalProp,
  },
  data() {
    return {
      popState : false,
      members: [],
      currentMember: null, // 선택된 회원 정보를 담을 객체
      listData: [], // 게시글 데이터를 담을 배열
      searchType: 0, // 검색 유형
      searchValue: "", // 검색어
      currentPage: 1,
      itemsPerPage: 10,
      pageBlockSize: 10
    };
  },
  computed: {
    totalPages() {
      return Math.ceil(this.listData.length / this.itemsPerPage);
    },
    paginatedList() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
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
  },

  methods: {
    formatDate(dateString) {
            const date = new Date(dateString);
            const year = date.getFullYear();
            const month = String(date.getMonth() + 1).padStart(2, '0');
            const day = String(date.getDate()).padStart(2, '0');
        return `${year}-${month}-${day}`;
        },
    fetchMembers() {  //리스트 호출(검색 문장,검색 타입)
      const formData = new FormData();
      formData.append('searchType', this.searchType);
      formData.append('searchValue', this.searchValue);
      axios 
        .post(`${process.env.VUE_APP_BACK_END_URL}/member/list`,formData)
        .then((response) => {
          this.listData =response.data;
          console.log(this.listData);
        })
        .catch((error) => {
          console.error(error);
        });
    },

    showModal(member) { // 모달을 열고 선택된 항목을 설정하는 함수
      this.currentMember = member;
      this.popState = true;
    },

    closeModal() { // 모달을 닫고 선택된 항목을 초기화하는 함수
      this.popState = false;
      let formData = new FormData();
      formData.append('memberDTO',this.currentMember)
      axios
        .post(`${process.env.VUE_APP_BACK_END_URL}/member/update`,this.currentMember)
        .then(() => {
        })
        .catch((error) => {
          console.error(error);
        });
      this.currentMember = null;
    },

    deleteMember(member) { //회원 삭제
      axios
        .delete(`${process.env.VUE_APP_BACK_END_URL}/member/delete/${member.mnum}`)
        .then(() => {
          this.fetchMembers();
        })
        .catch((error) => {
          console.error(error);
        });
    },

    changePage(page) { //페이지 이동
      if (page > 0 && page <= this.totalPages) {
        this.currentPage = page;

      }
        },
  },
  mounted() {
    this.fetchMembers();
  },
}
</script>
<style scoped>

</style>