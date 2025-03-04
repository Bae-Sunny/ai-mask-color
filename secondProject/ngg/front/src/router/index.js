import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'Main',
    component: () => import('../views/Main.vue')
  },
  {
    path: '/TestColor',
    name: 'TestColor',
    component: () => import('../views/TestColor.vue')
  },
  {
    path: '/MakeUp/:mnum/:mgender/:season/:befimgn/:rnum',
    name: 'MakeUp',
    component: () => import('../views/MakeUp.vue')
  },
  {
    path: '/AITest',
    name: 'AITest',
    component: () => import('../views/AITest.vue')
  },
  {
    path: '/AIResult',
    name: 'AIResult',
    component: () => import('../views/AIResult.vue')
  },
  {
    path: '/QNA',
    name: 'QNA',
    component: () => import('../views/QNA.vue')
  },
  {
    path: '/QNADetail',
    name: 'QNADetail',
    component: () => import('../views/QNADetail.vue')
  },
  {
    path: '/QNAWrite',
    name: 'QNAWrite',
    component: () => import('../views/QNAWrite.vue')
  },
    {
    path: '/QNAUpdate',
    name: 'QNAUpdate',
    component: () => import('../views/QNAUpdate.vue')
  },
  {
    path: '/Introduce',
    name: 'Introduce',
    component: () => import('../views/Introduce.vue')
  },
  {
    path: '/Login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/MyPage',
    name: 'MyPage',
    component: () => import('../views/MyPage.vue')
  },
  {
    path: '/MyPageUpdate/:mnum',
    name: 'MyPageUpdate',
    component: () => import('../views/MyPageUpdate.vue')
  },
  {
    path: '/History/:mnum',
    name: 'History',
    // component: () => import('../views/History.vue')
    component: () => import('@/views/History.vue')
  },
  {
    path: '/MyProfile/:mnum',
    name: 'MyProfile',
    component: () => import('../views/MyProfile.vue')
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
