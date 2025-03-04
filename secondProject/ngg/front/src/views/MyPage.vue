<template>
  <MainLayout>
    <div class="all-padding"></div>
    <div
      class="my-container"
      style="padding-top: 30px; padding: 10px; font-size: 25px"
    >
      <div>
        <h1 style="padding-top: 30px">마이 페이지</h1>
        <hr />
      </div>
      <div
        style="
          width: 100%;
          height: 100%;
          padding-bottom: 30px;
          padding-top: 30px;
        "
      >
        <table style="width: 100%; height: 100%">
          <tr rowspan="2">
            <td
              style="
                border-right: solid 2px rgba(0, 0, 0, 0.3);
                width: 25%;
                height: 30%;
                padding-bottom: 30px;
              "
            >
              <div>
                <div>
                  <div
                    class="mypage-box"
                    style="width: 50%; height: 50%; margin: auto"
                  >
                    <!-- public\img\MyPage_image -->
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
                  <br />
                  <h1 style="text-align: center">{{ detail.mnick }}</h1>
                </div>
                <hr
                  style="
                    width: 50%;
                    margin: auto;
                    padding-bottom: 15px;
                    margin-top: 15px;
                    margin-bottom: 5px;
                  "
                />
                <div>
                  <a href="/MyPage" class="custom-link">개인정보</a><br />
                  <router-link
                    :to="`/History/${detail.mnum}`"
                    class="custom-link"
                    >나의 기록</router-link
                  ><br />
                  <router-link
                    :to="`/MyProfile/${detail.mnum}`"
                    class="custom-link"
                    >프로필 변경</router-link
                  ><br />
                  <hr
                    style="
                      width: 50%;
                      margin: auto;
                      padding-bottom: 15px;
                      margin-top: 15px;
                    "
                  />
                  <a href="" class="custom-link">로그아웃</a><br />
                  <a
                    @click.stop="delAccount(detail.mnum)"
                    class="custom-link" style="cursor: pointer;"
                    >회원탈퇴</a
                  ><br />
                </div>
              </div>
            </td>
            <td rowspan="2" style="width: 75%; margin: auto">
              <div
                style="
                  border: solid 1px white;
                  border-radius: 15px;
                  width: 80%;
                  margin: auto;
                  height: 100%;
                  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
                "
              >
                <table
                  style="
                    width: 80%;
                    text-align: left;
                    margin: auto;
                    margin-top: 10%;
                  "
                >
                  <tr style="border-bottom: solid 1px rgba(0, 0, 0, 0.1)">
                    <td>
                      <i class="bi bi-person-circle"></i>&nbsp; |&nbsp;
                      {{ detail.mname }}
                    </td>
                  </tr>
                  <tr style="border-bottom: solid 1px rgba(0, 0, 0, 0.1)">
                    <td>
                      <i class="bi bi-envelope"></i>&nbsp; |&nbsp;
                      {{ detail.memail }}
                    </td>
                  </tr>
                  <tr style="border-bottom: solid 1px rgba(0, 0, 0, 0.1)">
                    <td>
                      <i class="bi bi-key"></i>&nbsp; |&nbsp;
                      <span>*******</span>
                    </td>
                  </tr>

                  <tr style="border-bottom: solid 1px rgba(0, 0, 0, 0.1)">
                    <td>
                      <i class="bi bi-gender-ambiguous"></i>&nbsp; |&nbsp;
                      {{ detail.mgender }}
                    </td>
                  </tr>
                  <tr style="border-bottom: solid 1px rgba(0, 0, 0, 0.1)">
                    <td>
                      <i class="bi bi-cake2"></i>&nbsp; |&nbsp;
                      {{ formatMdateDate(detail.mbirth) }}
                    </td>
                  </tr>
                  <tr style="border-bottom: solid 1px rgba(0, 0, 0, 0.1)">
                    <td>
                      <i class="bi bi-geo-alt-fill"></i>&nbsp; |&nbsp;
                      {{ getLocationName(detail.locnum) }}
                    </td>
                  </tr>
                  <tr style="border-bottom: solid 1px rgba(0, 0, 0, 0.1)">
                    <td>
                      <i class="bi bi-palette"></i>&nbsp; |&nbsp;
                      {{ detail.season }}
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <i class="bi bi-pen"></i>&nbsp; |&nbsp;
                      {{ formatMdateDate(detail.mdate) }}
                    </td>
                  </tr>

                  <tr style="text-align: right">
                    <button class="myPage-btn" @click="goUpdate">수정</button>
                  </tr>
                </table>
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
import axios from "axios";

export default {
  components: {
    MainLayout,
  },
  data() {
    return {
      detail: {
        mpwd: "yourpassword",
        mnum: 0,
      },
      locnumMapping: {
        1: "서울특별시",
        2: "경기도",
        3: "강원도",
        4: "충청도",
        5: "전라도",
        6: "경상도",
        7: "제주도",
        default: "기타",
      },
    };
  },
  computed: {
    maskedPassword() {
      const password = this.detail.mpwd; // 실제 비밀번호
      const masked = "*".repeat(password.length); // '*'로 마스킹된 문자열 생성
      return masked;
    },
  },
  created() {
    this.fetchData();
  },
  methods: {
    fetchData() {
      axios
        .get(`${process.env.VUE_APP_BACK_END_URL}/members/detail?mnum=1`)
        .then((resp) => {
          console.log(resp.data);
          this.mnum = resp.data.mnum;
          console.log(this.mnum);
          this.detail = resp.data;
        })
        .catch((err) => {
          console.log("에러뜸");
          console.log(err);
        });
    },
    goUpdate() {
      this.$router.push({
        name: "MyPageUpdate",
        params: { mnum: this.detail.mnum },
      });
    },
    getLocationName(locnum) {
      return this.locnumMapping[locnum] || this.locnumMapping.default;
    },
    formatMdateDate(dateString) {
      if (!dateString) return ""; // Handle empty input gracefully
      const date = new Date(dateString); // Parse the date string
      const year = date.getFullYear();
      const month = date.getMonth() + 1; // Months are zero indexed
      const day = date.getDate();
      return `${year}년 ${month}월 ${day}일`;
    },
    formatBirthDate(dateStr) {
      if (!dateStr) {
        return "";
      } else {
        const [day, month, year] = dateStr.split("-");
        if (year > 24) {
          return `19${year}년 ${month} ${day}일`;
        } else {
          return `20${year}년 ${month} ${day}일`;
        }
      }
    },
    delAccount(mnum) {
      console.log("Deleting account with mnum:", mnum);
      axios.get(`${process.env.VUE_APP_BACK_END_URL}/members/delAccount`, {
          params: { mnum: mnum },
        })
        .then((response) => {
          // console.log("Member deleted successfully:", response.data);
          // console.log("Current Route:", this.$route.fullPath);
          this.$router.push('/');
        })
        .catch((error) => {
          console.error("Error deleting member:", error);
        });
    },
  },
  mounted() {
    // Bootstrap Popover 초기화
    const popoverTriggerList = document.querySelectorAll(
      '[data-bs-toggle="popover"]'
    );
    popoverTriggerList.forEach((popoverTriggerEl) => {
      new Popover(popoverTriggerEl);
    });
  },
};
</script>
<style scoped>
</style>