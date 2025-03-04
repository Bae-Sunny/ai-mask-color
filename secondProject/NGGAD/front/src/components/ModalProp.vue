<template>
<div class="backdrop">
  <div class="mymodal">
  <form @submit.prevent="updateMember">
    <div class="mb-3">
      <label for="memail" class="form-label">아이디</label>
      <input
        type="email"
        class="form-control"
        id="memail"
        v-model="member.memail"
        readonly
      />
    </div>
    <div class="mb-3">
      <label for="mname" class="form-label">이름</label>
      <input
        type="text"
        class="form-control"
        id="mname"
        v-model="member.mname"
      />
    </div>
    <div class="mb-3">
      <label for="mnick" class="form-label">닉네임</label>
      <input
        type="text"
        class="form-control"
        id="mnick"
        v-model="member.mnick"
      />
    </div>
    <div class="mb-3">
      <label for="mgender" class="form-label">성별</label>
      <select class="form-control" id="mgender" v-model="member.mgender">
        <option value="M">남자</option>
        <option value="F">여자</option>
      </select>
    </div>
    <div class="mb-3">
      <label for="mbirth" class="form-label">생년월일</label>
      <input
        type="date"
        class="form-control"
        id="mbirth"
        v-model="member.mbirth"
      />
    </div>
    
    <div class="mb-3">
      <label for="season" class="form-label">퍼스널컬러</label>
      <input
        type="text"
        class="form-control"
        id="season"
        v-model="member.season"
      />
    </div>
    <div class="mb-3">
      <label for="mimgn" class="form-label">사진이름</label>
      <input
        type="text"
        class="form-control"
        id="mimgn"
        v-model="member.mimgn"
      />
    </div>
    <div class="mb-3">
      <label for="status" class="form-label">회원상태</label>
      <select class="form-control" id="status" v-model="member.status">
        <option value="active">활성</option>
        <option value="inactive">비활성</option>
      </select>
    </div>
    <div class="mb-3">
      <label for="mdate" class="form-label">가입날짜</label>
      <input
        type="date"
        class="form-control"
        id="mdate"
        v-model="member.mdate"
        readonly
      />
    </div>
    <button type="submit" class="btn btn-primary" @click="$emit('close',false)">저장</button>
  </form>
  </div>
</div>
</template>

<script>
export default {
  props: {
    member: {
      type: Object,
      required: true
    }
  },
  methods: {
    updateMember() {
      // 여기에 업데이트 로직 추가
    }
  }
};
</script>
<style scoped>
    .mymodal{
        width: 400px;
        padding: 20px;
        background: white;
        margin: 100px auto;
        border-radius: 10px;
    }
    .backdrop{
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5); /* 배경 색상 및 투명도 */
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000; /* 높은 z-index 값 */
    }
</style>