<template>
  <MainLayout :isFixed="true" :showFooter="false" >
    <div class="main-container">
      <div id="fullpage">
        <!-- 첫 번째 섹션: 비디오 섹션 -->
        <div class="section video-section">
          <video autoplay muted loop class="main-fullscreen-video" data-keepplaying>
            <source src="img/Main_image/main_video.mp4" type="video/mp4">
            <!-- Your browser does not support the video tag. -->
          </video>
        </div>
        <!-- 두 번째 섹션: 이미지 섹션 -->
        <div class="section image-section">
          <table class="main-fullscreen-table">
            <tr>
              <td class="main-td">
                <div class="main-overlay-text">소개</div>
                <a href="/introduce"><img src="img/Main_image/main_image1.png"></a>
    
              </td>
              <td class="main-td">
                <div class="main-overlay-text">자가진단</div>
                <a href="/TestColor"><img src="img/Main_image/main_image2.png"></a>
              </td>
            </tr>
            <tr>
              <td class="main-td">
                <div class="main-overlay-text">AI진단</div>
                <a href="/AITest"><img src="img/Main_image/main_image3.png"></a>
              </td>
              <td class="main-td">
                <div class="main-overlay-text" style="color: black;">QnA</div>
                <a href="/QNA"><img src="img/Main_image/main_image4.png"></a>
              </td>
            </tr>
          </table>
          <div class="main-overlay-image" >
            <img src="img/Main_image/main_image_Persoanl.png" class="main-image-text" style="opacity:0.8;">
          </div>
        </div>
      </div>
    </div>
  </MainLayout>
</template>

<script>
import MainLayout from '@/layouts/MainLayout.vue';
import fullpage from 'fullpage.js';

export default {
  components: {
    MainLayout
  },
  mounted() {
    this.$nextTick(() => {
      new fullpage('#fullpage', {
        anchors: ['video-section', 'image-section'],
        scrollingSpeed: 380,
        normalScrollElements: '#menu', // 스크롤을 허용할 요소
        onLeave: function(origin, destination, direction) {
          if (origin.item && origin.item.querySelector('video')) {
            origin.item.querySelector('video').play();
          }
        }
      });
    });
  },
  destroyed() {
    fullpage_api.destroy('all');
  }
};
</script>

<style scoped>
</style>
