<template>
  <MainLayout>
    <div class="all-padding">
      <div class="container">
        <div class="ai-container-in">
          <div class="w3-container w3-light-grey">
            <h2 style="text-shadow:1px 1px 0 #444;font-family: 'Jua'; padding-top: 30px; padding-bottom: 30px;">AI 퍼스널컬러 진단</h2>
          </div>
          
          <div class="ai-card">
              <video autoplay muted loop class="ai-fullscreen-video" data-keepplaying style="border-radius: 10px;">
                <source src="/img/AI_image/ai_video.mp4" type="video/mp4">
              </video>        
              <div id="defaultImageContainer" v-show="!imageUploaded" @click="triggerFileInput">
                <img class="ai-image2" src="img/MakeUp_image/upload3.png" alt="기본 이미지">
              </div>
              <div id="uploadedImageContainer" v-show="imageUploaded" @click="triggerFileInput">
                <img id="uploadedImage" class="ai-image2" :src="uploadedImageUrl" alt="업로드된 이미지" > 
              </div>
          </div>
        </div>
            <h3 style="text-shadow:1px 1px 0 #444;font-family: 'Jua'"></h3>
        <div>
          <input type="file" ref="fileInput" class="ai-form-control mt-1" id="faceImage" name="faceImage" accept="image/*" @change="uploadImage" style="display:none;">
        </div>
            
        <div class="ai-btn-grp">
          <button class="ai-btn" @click="goToAiDiagResult">AI 진단 결과보기</button>
        </div>
        <div class="ai-card1">
          <p class="ai-guideline">※ 사진 올리기 가이드라인 <br>
            ◎ 가이드라인을 잘 따르는 사진이 훨씬 나은 결과를 만들어냅니다. <br>
          </p>
          <img class="ai-image-guide" src="/img/AI_image/aiimageguide1.png">
          <div class="ai-guideline1">
            <p> ○ 증명사진, 얼굴이 선명하게 나온 사진, 조명에서 찍은 사진<br>
              Ｘ 전신 사진, 뿌연 사진, 과한 필터, 얼굴 가린 사진, 흔들린 사진, 안경낀 사진, 어두운 사진, 여러명이 나온 사진 </p>
          </div>
        </div>
      </div>
    </div>
  </MainLayout>
</template>

<script>
import axios from "axios";
import MainLayout from '@/layouts/MainLayout.vue';

export default {
  name: 'AiDiagView',
  components: {
    MainLayout
  },
  data() {
    return {
      mnum:1,
      imageUploaded: false,
      uploadedImageUrl: "",
      maskmessage: "",
      loadingImage: '/img/MakeUp_image/loading.gif',
    }
  },
  methods: {
    uploadImage(event) {      
      
      const file = event.target.files[0];
      if (file) {
        this.uploadedImageUrl = this.loadingImage;
        this.imageUploaded = true;

        const reader = new FileReader();
        reader.onload = (e) => {
          localStorage.setItem('befimg', e.target.result);
          localStorage.setItem('befimgn', file.name);
        };
        reader.readAsDataURL(file);

        this.formData = new FormData();
        this.formData.append('imgfile', file);

        axios.post(`${process.env.VUE_APP_DJANGO_APP_BACK_END_URL}/detect_mask`, this.formData, {
          headers: { 'Content-Type': 'multipart/form-data' },
        }).then((response) => {
          
          console.log('전송시작!');
          if (response.data.image) {
            
            const imageUrl = `data:image/jpeg;base64,${response.data.image}`;
            // console.log('imageUrl',imageUrl)
            this.uploadedImageUrl = imageUrl;
            this.imageUploaded = true;
            this.maskmessage = response.data.message;
          } else {
            alert(response.data.message);
            this.imageUploaded = false;
          }
          console.log('전송완료!');
        }).catch((error) => {
          console.error('장고 서버로 이미지 전송 실패:', error);
          alert('이미지 전송 중 오류가 발생했습니다.');
          this.imageUploaded = false;
        });
      } else {
        alert('이미지를 선택해 주세요.');
      }
    },
    goToAiDiagResult() {
      
      if (this.maskmessage === "얼굴 영역에서 마스크 착용이 감지되었습니다.") {
        alert('마스크를 벗고 다시 사진을 찍어주세요.');
      }else if(this.maskmessage === "얼굴을 찾을 수 없습니다."){
        alert('인물사진만 진단이 가능합니다.');
      }else if(this.maskmessage ==="얼굴 영역에서 마스크 미착용이 감지되었습니다."){
        axios.get(`${process.env.VUE_APP_DJANGO_APP_BACK_END_URL}/seasontone`)
        .then((response) => {
          console.log(response.data.season)
          localStorage.setItem('season', response.data.season);
          
          this.$router.push('/AIResult');
        })

      }else{
        alert('사진을 업로드 해주세요.');
      }
    },
    triggerFileInput() {
      this.$refs.fileInput.click(); // 파일 업로드 input 클릭
    },
  }
}
</script>

<style scoped>
.ai-fullscreen-video {
  width: 100%;
  height: 100%;
  object-fit: cover;
  top: 0;
  left: 0;
}
</style>
