<template>
  <div>
    <AppHeader :isFixed="isFixed" />
    <div v-if="showLayoutContent" :class="{ 'fixed-header-padding': isFixed }" class="layout-content" >
      <slot></slot>
    </div>
    <AppFooter v-if="showFooter"/>
  </div>
</template>

<script>
import AppHeader from '@/components/Header.vue';
import AppFooter from '@/components/Footer.vue';

export default {
  components: {
    AppHeader,
    AppFooter
  },
  props: {
    isFixed: {
      type: Boolean,
      default: false
    },
    showFooter:{
      type:Boolean,
      default: true
    },
    showLayoutContent: {
      type: Boolean,
      default: true 
    }
  }
}
</script>

<style scoped>
  .layout-content {  
  background-image: url('/public/img/header_image/header_background.jpg');
  background-size: cover;
  background-position: center;
  min-height: 100vh; /* 화면 전체 높이를 최소 높이로 설정 */
  justify-content: center; /* 내용을 수직 중앙 정렬 */
  
  }
  
</style>
