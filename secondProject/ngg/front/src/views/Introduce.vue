<template>
  <MainLayout>
    <div class="all-padding"></div>
    <div class="container" style="max-height: 8000px;">
      <div>
        <!-- <h2 class="text-center wow fadeInUp" style="margin-top: 30px; font-weight: bold;" data-wow-duration="3s" data-wow-delay="0.5s">
        내, 가꿈 소개</h2> -->
        <div class="intro">
          <div class="tabs">
            <button @click="scrollToImage('image5')" class="tabs-scroll-btn">자가진단</button>
            <button @click="scrollToImage('image9')" class="tabs-scroll-btn">AI진단</button>
          </div>
          <img ref="image1" src="/img/intro_image/1.png" style="margin-top:1.5px; width:1000px; image">
          <img ref="image2" src="/img/intro_image/2.png">
          <img ref="image3" src="/img/intro_image/3.png">
          <img ref="image4" src="/img/intro_image/4.png">
          <img ref="image5" src="/img/intro_image/5.png">
          <router-link to="/TestColor" class="try-btn" ref="image6">자가진단 바로가기</router-link>
          <img ref="image7" src="/img/intro_image/6.png">
          
          <img ref="image9" src="/img/intro_image/7.png">
          <router-link to="/AITest" class="try-btn" ref="image8">AI진단 바로가기</router-link>
          <img ref="image11" src="/img/intro_image/8.png">
        </div>
      </div>
    </div>
  </MainLayout>
</template>


<script>
import MainLayout from '@/layouts/MainLayout.vue';
export default {
  components: {
    MainLayout
  },
  methods: {
    scrollToImage(imageRef) {
      const element = this.$refs[imageRef];
      if (element) {
        element.scrollIntoView({ behavior: 'smooth' });
      }
    }
  }
}
</script>
<!-- <style scoped>
.tabs {
  margin-bottom: 20px;
}
.tabs button {
  margin-right: 10px;
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
.tabs button:hover {
  background-color: #0056b3;
}
</style> -->
