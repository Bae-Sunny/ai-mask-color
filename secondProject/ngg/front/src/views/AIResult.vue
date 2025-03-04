<template>
  
    <MainLayout>
      <div class="all-padding"></div>
      <div class="container">
        <div class="container-in">
          <div style="padding-top: 20px;">
                <h2 class="text-center wow fadeInUp" style="font-weight: bold; padding: 20px" data-wow-duration="3s" data-wow-delay="0.5s">AI 퍼스널컬러 진단</h2>
              </div>
          <div class="pictures" style="margin-top: 10px; padding-top: 50px;">
            <div class="w3-container w3-light-grey">
              
            </div>

            <table id="table2" style="display: table;">
              <tr>
                <td>
                  <img class="image-aires-face" :src="befimg" v-if="befimg">
                </td>
                <td colspan="2"><img class="image-aires1" :src="getAiresImageSrc('aires1')"></td>
              </tr>
              <tr>
                <td colspan="3"><img class="image-aires2" :src="getAiresImageSrc('aires2')"></td>
              </tr>
            </table>
          </div>

          <div class="ai2-btn-grp" >
            <button class="ai2-btn" @click="goToAITest">다시하기</button>
            <button class="ai2-btn" @click="saveData" v-show="!showMakeupButton">결과저장</button>
            <button class="ai2-btn" @click="goToMakeup" v-show="showMakeupButton">가상메이크업체험</button>
          </div>
        </div>
      </div>
    </MainLayout>
</template>

<script>
import MainLayout from '@/layouts/MainLayout.vue';
import router from '@/router';
import axios from 'axios';

export default {
  name: 'AiDiagResultView',
  components: { MainLayout },
  data() {
    return {
      mnum: 1,
      rnum: 0,
      season: localStorage.getItem('season'),
      befimg: localStorage.getItem('befimg'),
      befimgn: localStorage.getItem('befimgn'),
      croppedImgUrl: '',
      imagePaths: {
        spring: {
          aires1: "/img/AI_image/aires_spring1.png",
          aires2: "/img/AI_image/aires_spring2.png"
        },
        summer: {
          aires1: "/img/AI_image/aires_summer1.png",
          aires2: "/img/AI_image/aires_summer2.png"
        },
        fall: {
          aires1: "/img/AI_image/aires_fall1.png",
          aires2: "/img/AI_image/aires_fall2.png"
        },
        winter: {
          aires1: "/img/AI_image/aires_winter1.png",
          aires2: "/img/AI_image/aires_winter2.png"
        }
      },
      showMakeupButton: false // 초기에 가상메이크업체험 버튼을 숨기기 위한 상태 변수
    };
  },
  created() {
    console.log(this.mnum)
    axios.get(`${process.env.VUE_APP_BACK_END_URL}/members/detail?mnum=${this.mnum}`)
    .then((response) => {
      this.mgender = response.data.mgender
      console.log(response.data.mgender)
    })
  },
  methods: {
    getAiresImageSrc(imageType) {
      return this.imagePaths[this.season]?.[imageType] || "";
    },
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
    goToAITest() {
      this.$router.push('/AITest');
    },
    saveData() {
      alert("진단결과 저장");
      // console.log('this.befimg<=긴거\n',this.befimg)
      const cuttingImageformData = new FormData();
      cuttingImageformData.append('imgfile', this.dataURItoBlob(this.befimg), this.befimgn);
      for (let [key, value] of cuttingImageformData.entries()) {
          console.log(key, ':', value);
        }
      axios.post(`${process.env.VUE_APP_DJANGO_APP_BACK_END_URL}/cuttingImage`, cuttingImageformData, {
        headers: { 'Content-Type': 'multipart/form-data' },
        responseType: 'blob'  // blob으로 응답받기 위해 설정
      })
      .then(response => {
        // console.log(response.data)
        const url = URL.createObjectURL(response.data);          
        this.croppedImgUrl = url;
        const croppedImgSaveformData = new FormData();
        croppedImgSaveformData.append('croppedImg', response.data, this.befimgn);
        console.log('croppedImgSave 이전')
        for (let [key, value] of croppedImgSaveformData.entries()) {
          console.log(`${key}: ${value}`);
        }
        axios.post(`${process.env.VUE_APP_BACK_END_URL}/ai/croppedImgSave`, croppedImgSaveformData, { headers: { 'Content-Type': 'multipart/form-data' } })
        .then(response => {
          console.log('얍')          
          console.log(response.data)          
          this.befimgn = response.data;
          console.log('croppedImgSave 이후')
          const formData = new FormData();
          formData.append('mnum', this.mnum)
          formData.append('befimgn', this.befimgn)
          formData.append('afimgn', 'defaultImg.jpeg')
          formData.append('season', localStorage.getItem('season'))
          axios.post(`${process.env.VUE_APP_BACK_END_URL}/ai/saveTest`, formData, { headers: { 'Content-Type': 'application/json' } })
          .then(res => {
            console.log(res.data);
            this.rnum = res.data;
            axios.post(`${process.env.VUE_APP_BACK_END_URL}/members/seasonupdate`, formData, { headers: { 'Content-Type': 'application/json' } })
            .then(res => {
              this.showMakeupButton = true;
            })
            .catch(err =>{
              console.log('seasonupdate Error:',err)
            })
          })
          .catch(err =>{
            console.log('saveTest Error:',err)
          })
        })
        .catch(err =>{
          console.log('croppedImgSave Error:',err)
        })     
      })

    },
    goToMakeup() {
      this.$router.push({name:'MakeUp',params:{befimgn: this.befimgn, mnum:this.mnum ,mgender:this.mgender, season:this.season, rnum:this.rnum}});
    },
  }
}
</script>
