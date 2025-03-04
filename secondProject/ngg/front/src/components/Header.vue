<template>
  <header :class="{ 'header-fixed': isFixed }" class="header-background">
    <div class="header-container">
      <div class="header-con">
        <nav class="header-navbar navbar navbar-expand-lg header-bg-body-tertiary">
          <router-link to="/"><img src="/img/header_image/header_logo.png" alt="Logo" style="height: 6vh; width: 18vh"></router-link>     
          <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="header-navbar-nav navbar-nav me-auto mb-3 mb-lg-0 header-effect">
              <li class="header-nav-item nav-item">
                <a class="header-nav-link nav-link" href="/Introduce">소개</a>
              </li>
              <li class="header-nav-item nav-item">
                <a class="header-nav-link nav-link" href="/TestColor">자가진단</a>
              </li>
              <li class="header-nav-item nav-item">
                <a class="header-nav-link nav-link" href="/AITest">AI진단</a>
              </li>
              <li class="header-nav-item nav-item">
                <a class="header-nav-link nav-link" href="/MyPage">마이페이지</a>
              </li>
              <li class="header-nav-item nav-item">
                <a class="header-nav-link nav-link" href="/QNA">QnA</a>
              </li>

            </ul>
            <div class="header-button-container">
              <!-- <a href="Login"><button type="button" class="btn btn-outline-dark">회원가입</button></a> -->
              <a href="/Login"><button type="button" class="btn btn-outline-dark">로그아웃</button></a>
              <!-- <a href="/MyPage"><button type="button" class="btn btn-outline-dark">마이페이지</button></a> -->
            </div>
          </div>
        </nav>
      </div>
    </div>
  </header>
</template>


<script>
export default {
  name: 'AppHeader',
  props: {
    isFixed: {
      type: Boolean,
      default: false
    }
  }
}
</script>
<style scoped>

</style>

