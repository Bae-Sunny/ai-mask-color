<template>
  <MainLayout>
    <div class="all-padding"></div>
    <div class="my-container" style="padding-top: 30px; padding: 10px; font-size: 25px;">
      <div>
        <h1 style="padding-top: 30px;">프로필 사진 변경하기</h1>
        <hr />
      </div>
      <div style="width: 100%; height: 100%; padding-bottom: 30px; padding-top: 30px;">
        <table style="width: 100%; height: 100%">
          <tr rowspan="2">
            <td style="border-right: solid 2px rgba(0, 0, 0, 0.3); width: 25%; height: 30%; padding-bottom: 30px;">
              <div>
                <div>
                  <div class="mypage-box" style="width: 50%; height: 50%; margin: auto">
                    <img
                      v-show="detail.mimgn"
                      :src="`/img/upimg/${detail.mimgn}`"
                      class="mypage-profile"
                      style="width: 100%; height: 100%;"
                    />
                    <img
                      v-show="!detail.mimgn"
                      src="/img/MakeUp_image/upload3.png"
                      class="mypage-profile"
                      style="width: 100%; height: 100%;"
                    />
                  </div>
                  <br>
                  <h1 style="text-align: center">{{ detail.mnick }}</h1>
                </div>
                <hr style="width: 50%; margin: auto; padding-bottom: 15px; margin-top: 15px; margin-bottom: 5px;">
                <div>
                  <a href="/MyPage" class="custom-link">개인정보</a><br />
                  <router-link :to="`/History/${detail.mnum}`" class="custom-link">나의 기록</router-link><br />
                  <router-link :to="`/MyProfile/${detail.mnum}`" class="custom-link">프로필 변경</router-link><br />
                  <hr style="width: 50%; margin: auto; padding-bottom: 15px; margin-top: 15px;" />
                  <a href="" class="custom-link">로그아웃</a><br />
                  <a @click.stop="delAccount(detail.mnum)" class="custom-link" style="cursor: pointer;">회원탈퇴</a><br />
                </div>
              </div>
            </td>
            <td rowspan="2" style="text-align: center; width: 75%; margin: auto">
              <div style="border: solid 1px white; border-radius: 15px; width: 80%; margin: auto; height: 100%; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);">
                <form @submit.prevent="selectFile">
                  <div class="ori_image_container">
                    <div class="ori_image_container_before" style="margin-left: 70px;">
                      <p>변경 전</p>
                      <img :src="`/img/upimg/${detail.mimgn}`" class="rounded-image" style="height: 205px; width: 185px;">
                    </div>
                    <div class="ori_image_container_after" style="margin-right: 70px; cursor:pointer;" @click="triggerFileInput">
                      <p>변경 후</p>
                      <img :src="previewImageUrl || '/img/MakeUp_image/upload3.png'" alt="" class="rounded-image" style="height: 205px; width: 185px;" />
                    </div>
                  </div>
                  <input ref="fileInput" type="file" id="formFile" style="display: none;" @change="changeFile" multiple accept="image/*" />
                  <div style="display: flex;">
                    <button type="submit" class="make-btn btn-dark" style="font-size: 25px; margin-left: 265px; margin-top: 50px; opacity: 0.8;">저장</button>
                  </div>
                </form>
              </div>
            </td>
          </tr>
        </table>
      </div>
    </div>
  </MainLayout>
</template>

<script>
import MainLayout from "@/layouts/MainLayout.vue";
import axios from 'axios';
import { Popover } from 'bootstrap';

export default {
  components: {
    MainLayout,
  },
  data() {
    return {
      detail: {}, // 서버에서 받아온 회원 정보
      selectedFiles: null, // 선택된 파일
      previewImageUrl: null, // 파일 선택 후 미리보기 URL
      profileImageUrl: '/img/MyPage_image/noimage.png', // 프로필 이미지 URL 초기값
    };
  },
  created() {
    const mnum = this.$route.params.mnum;
    this.fetchData(mnum);
  },
  methods: {
    fetchData(mnum) {
      axios.get(`${process.env.VUE_APP_BACK_END_URL}/members/detail?mnum=${mnum}`)
        .then((resp) => {
          this.detail = resp.data;
          this.profileImageUrl = `/img/upimg/${this.detail.mimgn}`; // 프로필 이미지 경로 설정
        })
        .catch((err) => {
          console.error("Fetch data error:", err);
        });
    },
    selectFile() {
      const formData = new FormData();
      if (!this.selectedFiles || this.selectedFiles.length === 0) {
        alert("파일을 선택하세요.");
        return;
      }
      formData.append('file', this.selectedFiles[0]);
      formData.append('mnum', this.detail.mnum);
  
      axios.post(`${process.env.VUE_APP_BACK_END_URL}/members/myprofileupdate`, formData, {
        headers: { 'Content-Type': 'multipart/form-data' },
      }).then((response) => {
        console.log("Profile update response:", response.data);
        this.$router.push({ name: 'MyPage', params: { mnum: this.detail.mnum } });
      }).catch(error => {
        console.error("Profile update error:", error);
        alert("프로필 업데이트 실패: " + error.message);
      });
    },
    changeFile(event) {
      this.selectedFiles = event.target.files;
      if (this.selectedFiles && this.selectedFiles[0]) {
        const reader = new FileReader();
        reader.onload = (e) => {
          this.previewImageUrl = e.target.result;
        };
        reader.readAsDataURL(this.selectedFiles[0]);
      }
    },
    triggerFileInput() {
      this.$refs.fileInput.click(); // 파일 업로드 input 클릭
    },
    delAccount(mnum) {
      axios.get(`${process.env.VUE_APP_BACK_END_URL}/members/delAccount`, {
        params: { mnum }
      })
      .then((response) => {
        // 회원 탈퇴 후의 로직 추가
        console.log('Member deleted successfully:', response.data);
        // 예: 로그아웃 후 홈 페이지로 리디렉션
        this.$router.push('/');
      })
      .catch((error) => {
        console.error('Error deleting member:', error);
      });
    }
  },
  mounted() {
    // Bootstrap Popover 초기화
    const popoverTriggerList = document.querySelectorAll('[data-bs-toggle="popover"]');
    popoverTriggerList.forEach((popoverTriggerEl) => {
      new Popover(popoverTriggerEl);
    });
  },
};
</script>

<style scoped>
/* 필요한 CSS 스타일링 */
</style>
