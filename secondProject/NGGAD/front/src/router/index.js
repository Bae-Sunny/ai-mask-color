import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'home',
    component: () => import(/* webpackChunkName: "about" */ '../views/QnAnswer.vue')
  },
  {
    path: '/qnalist',
    name: 'qnalist',
    // route level code-splittingQnADetail
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/QnAnswer.vue')
  },
  {
    path: '/qnadetail/:qnum',
    name: 'qnadetail',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/QnADetail.vue')
  },
  {
    path: '/memberlist',
    name: 'memberlist',
    component: () => import( '../views/MemberList.vue')
  },
  
  
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
