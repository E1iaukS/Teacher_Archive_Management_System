import { createRouter, createWebHistory } from 'vue-router'
import DashboardPage from '@/pages/DashboardPage.vue'
import LoginPage from '@/pages/LoginPage.vue'
import UsersPage from '@/pages/UsersPage.vue'
import TeachersPage from '@/pages/TeachersPage.vue'
import ArchivesPage from '@/pages/ArchivesPage.vue'
import FlowsPage from '@/pages/FlowsPage.vue'
import ResignationsPage from '@/pages/ResignationsPage.vue'
import { useAuthStore } from '@/store/auth'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/login', component: LoginPage },
    { path: '/', redirect: '/dashboard' },
    { path: '/dashboard', component: DashboardPage },
    { path: '/users', component: UsersPage },
    { path: '/teachers', component: TeachersPage },
    { path: '/archives', component: ArchivesPage },
    { path: '/flows', component: FlowsPage },
    { path: '/resignations', component: ResignationsPage },
  ],
})

router.beforeEach((to, _from, next) => {
  const auth = useAuthStore()
  if (to.path !== '/login' && !auth.token) {
    next('/login')
  } else {
    next()
  }
})

export default router
