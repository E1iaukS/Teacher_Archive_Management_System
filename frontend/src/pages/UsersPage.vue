<template>
  <el-card>
    <template #header>用户管理</template>
    <el-button type="primary" size="small" @click="openDialog">新增用户</el-button>
    <el-table :data="users" class="mt">
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="realName" label="姓名" />
      <el-table-column prop="email" label="邮箱" />
      <el-table-column prop="status" label="状态" />
    </el-table>
    <el-dialog v-model="show" title="新增用户">
      <el-form :model="form">
        <el-form-item label="用户名"><el-input v-model="form.username" /></el-form-item>
        <el-form-item label="姓名"><el-input v-model="form.realName" /></el-form-item>
        <el-form-item label="邮箱"><el-input v-model="form.email" /></el-form-item>
        <el-form-item label="密码"><el-input v-model="form.password" show-password /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="show = false">取消</el-button>
        <el-button type="primary" @click="save">保存</el-button>
      </template>
    </el-dialog>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { fetchUsers, createUser } from '@/api/user'

const users = ref<any[]>([])
const show = ref(false)
const form = ref({ username: '', realName: '', email: '', password: 'admin123', roleCodes: ['ROLE_TEACHER'] })

const load = async () => {
  users.value = (await fetchUsers()).content || []
}

const openDialog = () => {
  show.value = true
}

const save = async () => {
  await createUser(form.value)
  show.value = false
  await load()
}

onMounted(load)
</script>

<style scoped>
.mt {
  margin-top: 12px;
}
</style>
