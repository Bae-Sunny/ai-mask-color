<template>
  <MainLayout>
    <div class="all-padding"></div>
    <div
      class="my-container"
      style="padding-top: 30px; padding: 10px; font-size: 25px"
    >
      <div>
        <h1 style="padding-top: 30px">내 정보 수정하기</h1>
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
                  <a @click.stop="delAccount(detail.mnum)" class="custom-link" style="cursor: pointer;">회원탈퇴</a><br />
                </div>
              </div>
            </td>
            <td
              rowspan="2"
              style="text_align: center; width: 75%; margin: auto"
            >
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
                <form
                  @submit.prevent="myupdate"
                  style="
                    width: 80%;
                    margin: auto;
                    margin-top: 40px;
                    text-align: left;
                  "
                >
                  <table
                    style="width: 100%; font-size: 27px; border-spacing: 0 10px"
                  >
                    <tr>
                      <th class="mar">이름</th>
                      <td class="mar">
                        <input
                          type="text"
                          name="name"
                          v-model="detail.mname"
                          readonly
                          class="mypage-readonly"
                        />
                      </td>
                    </tr>
                    <tr>
                      <th class="mar">닉네임</th>
                      <td class="mar">
                        <input
                          type="text"
                          name="nickname"
                          v-model="nickname"
                          class="nickname-input short-input"
                        />&nbsp;
                        <input
                          type="button"
                          @click="nickchk"
                          value="중복체크"
                          class="btn btn-dark"
                        />
                        <p
                          style="display: none; margin: auto 0; font-size: 20px"
                          id="nickCheckMsg"
                          class="text"
                        ></p>
                      </td>
                    </tr>
                    <tr>
                      <th class="mar">비밀번호</th>
                      <td class="mar">
                        <input
                          type="password"
                          name="password"
                          v-model="detail.mpwd"
                          placeholder="대문자, 숫자, 특수문자 포함 8자리"
                          class="nickname-input short-input"
                        />
                      </td>
                    </tr>
                    <tr>
                      <th class="mar">이메일</th>
                      <td class="mar">
                        <input
                          type="email"
                          name="email"
                          v-model="detail.memail"
                          readonly
                          class="mypage-readonly"
                        />
                      </td>
                    </tr>
                    <tr>
                      <th class="mar">성별</th>
                      <td class="mar">
                        <input
                          type="text"
                          name="mgender"
                          v-model="detail.mgender"
                          readonly
                          class="mypage-readonly"
                        />
                      </td>
                    </tr>
                    <tr>
                      <th class="mar">생일</th>
                      <td class="mar">
                        <input
                          type="text"
                          name="birthday"
                          v-model="formattedDate"
                          readonly
                          class="mypage-readonly"
                        />
                      </td>
                    </tr>
                    <tr>
                      <th class="mar">지역</th>
                      <td class="mar">
                        <select
                          name="location"
                          v-model="this.detail.locnum"
                          class="custom-select short-input"
                        >
                          <option
                            v-for="e in locations"
                            :key="e"
                            :value="e"
                          >
                            {{ getLocationName(e) }}
                          </option>
                        </select>
                      </td>
                    </tr>
                  </table>
                  <div style="text-align: center; margin-top: 2px">
                    <button
                      type="submit"
                      class="btn btn-dark"
                      style="width: 150px; font-size: 20px; margin-bottom: 30px"
                    >
                      변경하기
                    </button>
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
import axios from "axios";
export default {
  components: {
    MainLayout,
  },
  data() {
    return {
      detail: {
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
      nickname: "", // 입력한 닉네임
      nickCheckMessage: "", // 중복 체크 메시지
      locations: [1, 2, 3, 4, 5, 6, 7], // 선택 가능한 지역 목록
    };
  },
  computed: {
    formattedDate() {
      if (!this.detail.mbirth) return '';
      return this.detail.mbirth.split('T')[0];
    }
  },
  created() {
    const mnum = this.$route.params.mnum;
    this.fetchData(mnum);
  },
  methods: {
    fetchData(mnum) {
      console.log(mnum);
      axios
        .get(`${process.env.VUE_APP_BACK_END_URL}/members/detail?mnum=${mnum}`)
        .then((resp) => {
          console.log(resp.data);
          this.detail = resp.data;
          this.nickname = resp.data.mnick;
          this.removeSelectedLocationOption();
          this.location = resp.data.locnum;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    nickchk() {
      const userNick = this.nickname;
      const nickCheckMsg = document.getElementById("nickCheckMsg");
      axios
        .get(`${process.env.VUE_APP_BACK_END_URL}/members/myidchk/${userNick}`)
        .then((res) => {
          console.log(res); // 존재하면1, 사용가능 0
          if (res.data === 0) {
            nickCheckMsg.innerText = "사용 가능한 닉네임 입니다.";
            nickCheckMsg.style.color = "green";
          } else {
            nickCheckMsg.innerText = "이미 사용중인 닉네임 입니다.";
            nickCheckMsg.style.color = "red";
          }
          nickCheckMsg.style.display = "block";
        })
        .catch((error) => {});
    },
    getLocationName(locnum) {
      return this.locnumMapping[locnum] || this.locnumMapping.default;
    },

    myupdate(event) {
      console.log("지역 :",this.detail.locnum)
      const formData = new FormData();
      formData.append("mnum", this.detail.mnum);
      formData.append("mpwd", this.detail.mpwd);
      formData.append("locnum", this.detail.locnum);
      formData.append("mnick", this.nickname); // nickname을 사용
      

      axios
        .post(`${process.env.VUE_APP_BACK_END_URL}/members/myupdate`, formData)
        .then(() => {
          this.fetchData(this.detail.mnum); // 데이터 다시 로드
          this.$router.push({ name: "MyPage" });
        })
        .catch((error) => {
          console.log(error);
        });
    },
    changeDate(dateStr){
      const [day, month, year] = dateStr.split('-');
      if (year > 24){
        return `19${year}년 ${month} ${day}일`;
      }
      else{
        return `20${year}년 ${month} ${day}일`;
      }
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
};
</script>
  
  <style scoped>
.custom-select {
  font-size: 22px;
  /* padding-top : 10px;
      padding-left : 10px;
      padding-bottom : 10px; */
  height: 35px;
  width: 85px;
}
.mar {
  padding-bottom: 20px;
}
.mypage-readonly[readonly] {
  border: none; /* 테두리 제거 */
  background-color: transparent; /* 배경색 제거 */
  color: inherit; /* 텍스트 색상 유지 */
  opacity: 1;
}
.mypage-readonly[readonly]:focus {
  outline: none; /* 포커스 시 테두리 제거 */
}
.short-input {
  width: 200px; /* 입력 필드 너비 조절 */
  border: 1px solid #72756f65;
  border-radius: 5px;
  font-size: 25px;
  font-weight: lighter;
  color: #4f524cf3;
  height: 40px;
  opacity: 0.8;
}
.nickname-input {
  padding-left: 5px;
}

.short-input:focus {
  outline: none; /* 포커스 시 기본 outline 제거 */
  border-color: #66afe9; /* 포커스 시 테두리 색상 변경 */
}
.short-input::placeholder {
  font-size: 15px; /* 원하는 글씨 크기 */
}

.check-button {
  width: 90px; /* 버튼 너비 조절 */
  font-size: 20px;
  font-family: "Jua";
  font-weight: lighter;
}
</style>