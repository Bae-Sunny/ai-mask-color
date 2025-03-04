<template>
  <MainLayout>
    <div class="container1">
      <!-- 이용 약관 모달이랑 구분하기 위한 div -->
      <div class="container">
        <div class="cont">
          <div class="lmform sign-in">
            <div class="lmform-area">
              <form method="POST" class="login-form">
                <div class="main-title-center">
                  <h2 class="login-title">로그인</h2>
                </div>

                <!-- 이메일 -->
                <div class="info-input-container">
                  <div class="label-area">
                    <label for="memail" class="login-label mustInput"
                      >✔️ 이메일</label
                    >
                  </div>
                  <div class="input-area">
                    <input
                      class="login-input-text"
                      type="email"
                      id="loginmemail"
                      placeholder="이메일 입력"
                      v-model="memail"
                      @input="checkEmailFormat"
                    />
                    <div class="error-msg-area">
                      <p v-if="emailError" class="login-error-msg">
                        올바른 이메일 형식이 아닙니다.
                      </p>
                    </div>
                  </div>
                </div>
                <br />
                <!-- 비밀번호 -->
                <div class="info-input-container">
                  <div class="label-area">
                    <label for="mpwd" class="login-label mustInput"
                      >✔️ 비밀번호</label
                    >
                  </div>
                  <div class="input-area">
                    <input
                      class="login-input-text"
                      type="password"
                      name="mpwd"
                      id="loginmpwd"
                      placeholder=" 비밀번호 입력"
                      v-model="mpwd"
                      @input="checkPwdFormat"
                    />
                  </div>
                  <div class="error-msg-area">
                    <p v-if="showPwdMsg && !mpwd" class="login-error-msg">
                      비밀번호를 입력해주세요.
                    </p>
                  </div>
                </div>
                <br />
                <div class="myinfo-btn-area">
                  <button
                    class="login-ctlbtn cancle-btn"
                    id="back-btn1"
                    type="button"
                    @click="back"
                  >
                    뒤로가기
                  </button>
                  &nbsp;
                  <button
                    class="login-ctlbtn select-btn"
                    id="login-btn"
                    type="button"
                    @click="submitForm('login')"
                  >
                    로그인
                  </button>
                </div>
              </form>
            </div>
          </div>
          <div class="sub-cont">
            <div class="img">
              <div class="img__text m--up">
                <h2 class="if-title">처음 오셨다면?</h2>
                <p>회원가입하시고 나 자신을 가꿔보세요!</p>
              </div>
              <div class="img__text m--in">
                <h2 class="if-title">이미 계정이 있다면,</h2>
                <p>로그인하고 당신의 계절을 찾으세요!</p>
              </div>
              <div class="img__btn">
                <span class="m--up">Sign Up</span>
                <span class="m--in">Sign In</span>
              </div>
            </div>
            <div class="lmform sign-up">
              <form
                method="POST"
                autocomplete="off"
                @submit.prevent="submit"
                class="join-form"
              >
                <div class="main-title-center">
                  <h2 class="title">회원가입</h2>
                </div>

                <!-- 이메일 -->
                <div class="info-input-container">
                  <div class="label-area">
                    <label for="memail" class="join-label mustInput"
                      >✔️ 이메일</label
                    >
                  </div>
                  <div class="input-area">
                    <input
                      class="join-input-text"
                      type="email"
                      v-model="memail"
                      @input="checkEmailFormat"
                      id="memail"
                      placeholder="이메일 입력"
                    />
                    <button
                      class="email-check-btn"
                      id="email-ckeck"
                      @click="emailCheck"
                    >
                      인증
                    </button>
                    <div class="error-msg-area">
                      <p v-if="emailError" class="login-error-msg">
                        올바른 이메일 형식이 아닙니다.
                      </p>
                      <p
                        style="display: none"
                        id="emailCheck-msg"
                        class="msg"
                      ></p>
                    </div>
                  </div>
                </div>

                <!-- 이메일 인증 번호 -->
                <div class="info-input-container">
                  <div class="label-area">
                    <label for="code" class="join-label mustInput"
                      >✔️ 인증번호</label
                    >
                  </div>
                  <div class="input-area">
                    <input
                      class="join-input-text"
                      type="text"
                      @keydown.enter.prevent="handleEnter"
                      id="code"
                      v-model="code"
                      placeholder="인증번호 입력"
                    />
                  </div>
                  <div class="error-msg-area">
                    <p style="display: none" id="Code-msg" class="msg"></p>
                  </div>
                </div>

                <!-- 비밀번호 -->
                <div class="info-input-container">
                  <div class="label-area">
                    <label for="mpwd" class="join-label mustInput"
                      >✔️ 비밀번호</label
                    >
                  </div>
                  <div class="input-area">
                    <input
                      class="join-input-text"
                      type="password"
                      name="mpwd"
                      id="mpwd"
                      placeholder="대문자, 숫자, 특수문자 포함 8자리"
                      v-model="mpwd"
                      @input="checkPwdFormat"
                    />
                    <div class="error-msg-area">
                      <p v-if="pwdError" class="login-error-msg">
                        올바른 비밀번호 형식이 아닙니다.
                      </p>
                    </div>
                  </div>
                </div>

                <!-- 닉네임 -->
                <div class="info-input-container">
                  <div class="label-area">
                    <label for="mnick" class="join-label mustInput"
                      >✔️ 닉네임</label
                    >
                  </div>
                  <div class="input-area">
                    <input
                      class="join-input-text"
                      type="text"
                      name="mnick"
                      id="mnick"
                      v-model="mnick"
                      placeholder="닉네임 입력"
                    />
                  </div>
                  <div class="error-msg-area">
                    <p style="display: none" id="nameCheck-msg" class="msg"></p>
                  </div>
                </div>

                <!-- 이름 -->
                <div class="info-input-container">
                  <div class="label-area">
                    <label for="mname" class="join-label mustInput"
                      >✔️ 이름</label
                    >
                  </div>
                  <div class="input-area">
                    <input
                      class="join-input-text"
                      type="text"
                      name="mname"
                      id="mname"
                      v-model="mname"
                      placeholder="이름 입력"
                    />
                  </div>
                  <div class="error-msg-area">
                    <p style="display: none" id="nameCheck-msg" class="msg"></p>
                  </div>
                </div>

                <!-- 성별 -->
                <div class="info-input-container">
                  <div class="label-area">
                    <label for="mgender" class="join-label chooseRadio"
                      >✔️ 성별</label
                    >
                  </div>
                  <div class="gender-input-area">
                    <input
                      type="radio"
                      v-model="mgender"
                      value="남"
                      name="mgender"
                    />
                    <label for="option1" class="join-label custom-radio"></label
                    >남성
                    <input
                      type="radio"
                      v-model="mgender"
                      value="여"
                      name="mgender"
                    />
                    <label for="option1" class="join-label custom-radio"></label
                    >여성
                  </div>
                </div>

                <!-- 생년월일 -->
                <div class="info-input-container">
                  <div class="label-area">
                    <label for="mbirth" class="join-label dateCHeck"
                      >📅 생년월일</label
                    >
                  </div>
                  <div class="input-area">
                    <!-- <input type="date" id="mbirth"> -->
                    <Datepicker
                      locale="ko"
                      v-model="mbirth"
                      class="datepicker"
                      :enable-time-picker="false"
                    />
                  </div>
                </div>

                <!-- 지역 -->
                <div class="info-input-container">
                  <div class="label-area">
                    <label for="locnum" class="join-label chooseRadio"
                      >✔️ 지역</label
                    >
                  </div>
                  <div class="loc-input-area">
                    <input
                      type="radio"
                      v-model="locnum"
                      name="locnum"
                      value="1"
                      class="loc"
                    />서울
                    <input
                      type="radio"
                      v-model="locnum"
                      name="locnum"
                      value="2"
                      class="loc"
                    />경기도
                    <input
                      type="radio"
                      v-model="locnum"
                      name="locnum"
                      value="3"
                      class="loc"
                    />강원도
                    <input
                      type="radio"
                      v-model="locnum"
                      name="locnum"
                      value="4"
                      class="loc"
                    />충청도
                    <input
                      type="radio"
                      v-model="locnum"
                      name="locnum"
                      value="5"
                      class="loc"
                    />전라도
                    <input
                      type="radio"
                      v-model="locnum"
                      name="locnum"
                      value="6"
                      class="loc"
                    />경상도
                    <input
                      type="radio"
                      v-model="locnum"
                      name="locnum"
                      value="7"
                      class="loc"
                    />제주도
                  </div>
                </div>
                <br />

                <!-- 버튼 -->
                <div class="myinfo-btn-area">
                  <button
                    class="big-ctlbtn cancle-btn"
                    id="back-btn"
                    @click="back"
                  >
                    뒤로가기
                  </button>
                  &nbsp;
                  <button
                    class="big-ctlbtn select-btn"
                    id="join-btn"
                    type="button"
                    @click="submitForm('signup')"
                  >
                    가입하기
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </MainLayout>
</template>
  
  
<script>
  import MainLayout from '@/layouts/MainLayout.vue';
  import { ref, onMounted, defineComponent } from 'vue';
  import Datepicker from '@vuepic/vue-datepicker';
  import '@vuepic/vue-datepicker/dist/main.css';
  import axios from 'axios';

  export default defineComponent({
    components: {
      Datepicker,
      MainLayout,
    },
    setup() {
      const mbirth = ref(null); // v-model에 사용할 변수
      const rightPanelActive = ref(false);
      const formData = ref({
        memail: '',
        mpwd: '',
        mname: '',
        mnick: '',
        code: '',
        mgender: '',
        locnum: '',
      });

      const toggleSignup = () => {
        const cont = document.querySelector('.cont');
        cont.classList.toggle('s--signup');
      };

      // 이벤트 리스너 추가
      onMounted(() => {
        const imgBtn = document.querySelector('.img__btn');
        if (imgBtn) {
          imgBtn.addEventListener('click', toggleSignup);
        }
      });

      return {
        mbirth,
        rightPanelActive,
      };
    },
    data() {
      return {
        memail: '',
        mpwd: '',
        mname: '',
        mnick: '',
        code: '',
        mgender: '',
        locnum: '',
        emailError: false,
        showPwdMsg: false,
        pwdError: false,
      };
    },
    methods: {
      back() {
        // 뒤로가기 기능 구현
        window.history.back();
      },
      toggleRightPanel(active) {
        this.rightPanelActive = active;
      },
      checkEmailFormat() {
        // 이메일 형식 검증 메서드
        this.emailError = !this.validateEmail(this.memail);
      },
      validateEmail(memail) {
        // 간단한 이메일 형식 검증 로직
        return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(memail);
      },
      emailCheck() {
        // 이메일 중복 체크 요청
        const memail = document.getElementById("memail").value;
        axios
          .post(
            `${process.env.VUE_APP_BACK_END_URL}/api/auth/emailCheck`,
            {
              memail: memail,
            }
          )
          .then((res) => {
            alert("Res DAta:" + res.data);
            if (res.data == 0) {
              alert("인증 번호가 발송되었습니다.");
              document.getElementById("emailCheck-msg").style.display = "none";
            } else {
              document.getElementById("emailCheck-msg").innerHTML =
                "이미 사용중인 이메일입니다.";
              document.getElementById("emailCheck-msg").style.display = "block";
            }
          })
          .catch((error) => {
            alert("인증 번호 발송에 오류가 발생했습니다.");
            console.error("API 호출 에러(인증번호 발송)", error);
            return false;
          });
      },
      checkPwdFormat() {
        // 비밀번호 형식 검증 메서드
        this.pwdError = !this.validatePwd(this.mpwd);
      },
      validatePwd(pwd) {
        // 간단한 비밀번호 형식 검증 로직 (대문자, 숫자, 특수문자 포함 8자리 이상)
        return /^(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,}$/.test(pwd);
      },
      submitForm(formType) {
        if (formType === 'login') {
          // 로그인 폼 제출
          if (!this.emailError) {
            // 이메일 형식이 유효한 경우
            if (!this.mpwd) {
              // 비밀번호가 입력되지 않은 경우
              this.showPwdMsg = true;
              return;
            } else {
              this.showPwdMsg = false;
            }
            // 비밀번호가 입력된 경우 로그인 시도
            alert('환영합니다.');
            window.location.href = '/';
          } else {
            // 이메일 형식이 유효하지 않으면 에러 메시지 표시 등 추가 로직 수행 가능
            alert('이메일 형식이 올바르지 않습니다.');
          }
        } else if (formType === 'signup') {
          // 회원가입 폼 제출
          if (!this.emailError && !this.pwdError) {

             // 비밀번호가 입력된 경우 인증번호 일치여부
            axios.post(`${process.env.VUE_APP_BACK_END_URL}/api/auth/emailCheck/certification`, {
            memail: this.memail,
            code: this.code, // 입력된 인증번호
        })
      .then((res) => {
        console.log(res);
        if (res.data === true) {
          // 인증번호 일치 시 서버로 회원가입 데이터 전송


            
            // 이메일과 비밀번호 형식이 유효한 경우
            axios.post(
              `${process.env.VUE_APP_BACK_END_URL}/members/signup`,
              {
                memail: this.memail,
                mpwd: this.mpwd,
                mname: this.mname,
                mnick: this.mnick,
                mgender: this.mgender,
                locnum: this.locnum,
                mbirth: this.mbirth,
              },
              { headers: { "Content-Type": "application/json" } } // JSON 형식으로 데이터 전송
            )
              .then((res) => {
                if (res.status === 200) {
                  alert("회원가입이 완료되었습니다.");
                  this.$router.push("/");
                } else {
                  alert("회원가입 중 문제가 발생했습니다.");
                }
              })
              .catch((error) => {
                console.error("API 호출 에러(/signup):", error);
                alert("회원가입 중 문제가 발생했습니다.");
              });}
              else{
                alert("인증번호가 일치하지 않습니다.")
              }
              })
          } else {
            // 이메일 혹은 비밀번호 형식이 유효하지 않은 경우
            alert("이메일 또는 비밀번호 형식이 올바르지 않습니다.");
          }
        } else {
          // 기타 처리
          console.log("기타 폼 제출 처리");
        }
      },
    },
  });
</script>

