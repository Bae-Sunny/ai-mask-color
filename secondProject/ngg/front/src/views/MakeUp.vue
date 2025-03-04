<template>
  <MainLayout>
    <div style="padding-top: 30px;"></div>
    <div class="container" style="font-size: 25px; font-family: 'Jua';">
      <br>
      <h2 class="text-center wow fadeInUp" style="font-weight: bold;" data-wow-duration="3s" data-wow-delay="0.5s">
        MakeUp Yourself<i class="bi bi-info-circle" style="font-size: 0.7em;" data-bs-toggle="popover"
          title="메이크업을 해보세요"
          data-bs-content="하단의 MakUp 이미지를 클릭하여 피부톤에 맞는 화장 전 후를 비교해 보세요."></i>
      </h2>
      <div class="ori_image_container">
        <div class="ori_image_container_before">
          <p>Before MakeUp</p>
          <img :src="beforeImage" alt="Cropped Face" class="rounded-image">          
        </div>
        <div class="ori_image_container_after">
          <p>After MakeUp</p>
          <img :src="afterImage" class="rounded-image">
        </div>
      </div>

      <button type="button" class="make-btn btn-dark" @click="historyUpdate" style="font-size: 25px; margin-left: 20px;">
        나의 기록 업데이트
      </button>
      <div id="content1" class="makeupSlide">
        <swiper :modules="modules" :slides-per-view="3" :space-between="50" navigation
          :pagination="{ clickable: true, el: '.swiper-pagination-custom' }" :scrollbar="{ draggable: true }"
          :loop="true" @swiper="onSwiper" @slideChange="onSlideChange"
          style="padding: 10px; margin-right: 35px;">
          <swiper-slide v-for="index in 4" :key="index">
            <p>{{ translatedSeason }} {{ translatedGender }} MakeUp {{ index }}</p><br>
            <img :src="getMakeupImage(index)" class="rounded-shadow" @click="afterMakeup(getMakeupImage(index))">
          </swiper-slide>
          <div class="swiper-pagination-custom"></div> <!-- Pagination을 위한 요소 -->
        </swiper>
      </div>
    </div>
  </MainLayout>
</template>

<script>
import axios from "axios";
import { Navigation, Pagination, A11y } from 'swiper/modules';
import { Swiper, SwiperSlide } from 'swiper/vue';
import 'swiper/css';
import 'swiper/css/navigation';
import 'swiper/css/pagination';
import MainLayout from '@/layouts/MainLayout.vue';
import { Popover } from 'bootstrap'; // Bootstrap에서 Popover 가져오기

export default {
  data() {
    return {
      files: [],
      croppedImageSrc: localStorage.getItem('croppedImage'),
      modules: [Navigation, Pagination, A11y],
      season: '', 
      mgender: '', 
      mnum: 0,
      rnum: 0,
      befimgn:'',
      afimgn:'',
      imgpath: '/img/before_image/',
      beforeImage: '',
      afterImage: '',
      loadingImage: '/img/MakeUp_image/loading.gif',
    };
  },
  computed: {
    translatedSeason() {
      const seasonMap = {
        spring: '봄',
        summer: '여름',
        fall: '가을',
        winter: '겨울'
      };
      return seasonMap[this.season] || this.season;
    },
    translatedGender() {
      const genderMap = {
        '남': '남자',
        '여': '여자'
      };
      return genderMap[this.mgender] || this.mgender;
    }
  },
  methods: {
    dataURItoBlob(dataURI) {
      const byteString = atob(dataURI.split(',')[1]);
      const mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];
      const ab = new ArrayBuffer(byteString.length);
      const ia = new Uint8Array(ab);
      for (let i = 0; i < byteString.length; i++) {
        ia[i] = byteString.charCodeAt(i);
      }
      return new Blob([ab], { type: mimeString });
    },
    getMakeupImage(index) {      
      const season = this.season;
      const mgender = this.mgender;
      return `/img/MakeUp_image/${mgender}_${season}_${index}.png`;
    },
    async afterMakeup(makeupImagePath) {
      try {
        const beforeImage = await this.loadImageAsBase64(this.beforeImage);
        const makeupImage = await this.loadImageAsBase64(makeupImagePath);
        this.afterImage = this.loadingImage;
        const response = await axios.post(`${process.env.VUE_APP_DJANGO_APP_BACK_END_URL}/PersonalPredict`, {
          before_image: beforeImage,
          makeup_image: makeupImage
        });
        const imageData = response.data.image_base64;
        this.afterImage = 'data:image/jpeg;base64,' + imageData;
      } catch (error) {
        console.error('Error loading makeup image:', error);
      }
    },
    loadImageAsBase64(imagePath) {
      return new Promise((resolve, reject) => {
        const xhr = new XMLHttpRequest();
        xhr.onload = function() {
          const reader = new FileReader();
          reader.onloadend = function() {
            resolve(reader.result);
          };
          reader.readAsDataURL(xhr.response);
        };
        xhr.onerror = function() {
          reject(new Error('Failed to load image'));
        };
        xhr.open('GET', imagePath);
        xhr.responseType = 'blob';
        xhr.send();
      });
    },
    async historyUpdate() {
      try {
        this.afimgn = 'makeup_' + this.befimgn;
        const afterImage = await this.loadImageAsBase64(this.afterImage);
        const formData = new FormData();
        formData.append('afterimage', this.dataURItoBlob(afterImage), this.afimgn);

        const response = await axios.post(`${process.env.VUE_APP_DJANGO_APP_BACK_END_URL}/base64toFile`, formData, {
          headers: { 'Content-Type': 'multipart/form-data' },
          responseType: 'blob'
        });

        const springformData = new FormData();
        springformData.append('imgfile', response.data, this.afimgn);

        await axios.post(`${process.env.VUE_APP_BACK_END_URL}/ai/after_imageSave`, springformData, { headers: { 'Content-Type': 'multipart/form-data' } });

        const updateHistoryForm = new FormData();
        updateHistoryForm.append('afimgn', this.afimgn);
        updateHistoryForm.append('rnum', this.rnum);

        await axios.post(`${process.env.VUE_APP_BACK_END_URL}/ai/updateHistory`, updateHistoryForm, { headers: { 'Content-Type': 'application/json' } });

        // History.vue로 라우팅
        this.$router.push({ name: 'History', params: { mnum: this.mnum } });
      } catch (error) {
        console.error('Error updating history:', error);
      }
    }
  },
  mounted() {
    // Bootstrap Popover 초기화
    const popoverTriggerList = document.querySelectorAll('[data-bs-toggle="popover"]');
    popoverTriggerList.forEach((popoverTriggerEl) => {
      new Popover(popoverTriggerEl);
    });

    this.rnum = this.$route.params.rnum;
    this.season = this.$route.params.season;
    this.mnum = this.$route.params.mnum;
    this.mgender = this.$route.params.mgender;
    this.befimgn = this.$route.params.befimgn;
    this.beforeImage = this.imgpath + this.$route.params.befimgn;
    this.afterImage = this.imgpath + this.$route.params.befimgn;
  },
  components: {
    Swiper,
    SwiperSlide,
    MainLayout
  }
};
</script>

<style scoped>
/* 여기에 스타일을 추가하세요 */
</style>
