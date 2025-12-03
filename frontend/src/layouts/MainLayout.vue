<template>
  <el-container class="layout">
    <el-aside :width="collapsed ? '64px' : '200px'" class="sidebar">
      <div class="logo">教师档案</div>
      <el-menu :default-active="route.path" router>
        <el-menu-item index="/dashboard"><i class="el-icon-menu"></i><span v-if="!collapsed">仪表盘</span></el-menu-item>
        <el-sub-menu index="1">
          <template #title><span>用户与权限</span></template>
          <el-menu-item index="/users">用户管理</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="2">
          <template #title><span>教师档案</span></template>
          <el-menu-item index="/teachers">教师信息</el-menu-item>
          <el-menu-item index="/archives">档案信息</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/flows">档案流转</el-menu-item>
        <el-menu-item index="/resignations">离职管理</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <div class="title">教师档案管理系统</div>
        <div class="actions">
          <el-button text size="small" @click="toggle">{{ collapsed ? '展开' : '折叠' }}</el-button>
          <el-button text size="small" @click="logout">退出</el-button>
        </div>
      </el-header>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router'
import { ref } from 'vue'
import { useAuthStore } from '../store/auth'

const route = useRoute()
const router = useRouter()
const collapsed = ref(false)
const auth = useAuthStore()

const toggle = () => (collapsed.value = !collapsed.value)
const logout = () => {
  auth.logout()
  router.push('/login')
}
</script>

<style scoped>
.layout {
  height: 100vh;
}
.sidebar {
  background: #001529;
  color: #fff;
}
.logo {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: bold;
  color: #fff;
}
.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}
</style>
