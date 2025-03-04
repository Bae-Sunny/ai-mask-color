<template>
  
  <nav class="nav-nnv">
    <div class="container">
    <header class="d-flex justify-content-center py-3">
      <ul class="nav nav-pills">
        <router-link class="nav-link" to="/memberlist"><div class="navbar-brand">내,가꿈</div></router-link>
      </ul>
    </header>
  </div>
  </nav>

  <div class="row main-cn">
    <div class="col-3 main-sidebar1">
      <router-link class="router-link" to=""><div class="router-sbtn1">관리자 페이지</div></router-link>
      <hr>
      <router-link class="router-link" to="/qnalist"><div class="router-sbtn2">QnA</div></router-link>
      <router-link class="router-link"  to="/memberlist"><div class="router-sbtn2">회원 관리</div></router-link>
    </div>
    <div class="col router-v">
      <router-view/>
    </div>

</div>
</template>

<style>
@import url('https://fonts.googleapis.com/css2?family=Nanum+Pen+Script&display=swap');
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}





nav a.router-link-exact-active {
  color: #42b983;
}

.main-sidebar1{
  width: 220px;
  /* background-color: rgb(230, 230, 230); */
  background-color: rgb(236, 236, 236);
  font-family: 'Jua';
}
.main-cn{
  width: 100%;
    margin: auto;
    padding: 0px;
    height: 92vh;
  
}
.nav-link{
  padding: auto;
  margin: auto;
}
.nav-nnv{
  height: 80px;
  padding-top :0px;
}
body{
  overflow-x: auto;
  overflow-y: hidden;
  
  
}
.router-sbtn1{
  margin: 40px 15px 10px 15px;
  padding: 10px;
  background-color: white;
  border-radius: 13px;
  border: 1px solid rgb(204, 204, 204);
  height: 45px;
  text-align: center;
}
.router-sbtn2{
  margin: 10px 15px;
  padding: 10px;
  background-color: white;
  border-radius: 13px;
  border: 1px solid rgb(204, 204, 204);
  height: 45px;
  text-align: center;
}
.router-v{
  width: 100%;
  min-width: 200px;
    margin: 0px;
    padding: 0px;
}

.navbar-brand {
  color: #000000a4;
  font-size: 35px;
  font-family: 'Jua';
}
.router-link{
  text-decoration: none;
  color:black;
}
</style>
