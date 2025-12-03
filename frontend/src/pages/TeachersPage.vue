<template>
  <el-card>
    <template #header>教师列表</template>
    <el-button type="primary" size="small" @click="openDialog">新增教师</el-button>
    <el-table :data="teachers" class="mt" size="small">
      <el-table-column prop="teacherNo" label="工号" />
      <el-table-column prop="name" label="姓名" />
      <el-table-column prop="deptId" label="部门" />
      <el-table-column prop="title" label="职称" />
    </el-table>
    <el-dialog v-model="show" title="新增教师">
      <el-form :model="form">
        <el-form-item label="工号"><el-input v-model="form.teacherNo" /></el-form-item>
        <el-form-item label="姓名"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="电话"><el-input v-model="form.phone" /></el-form-item>
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
import { fetchTeachers, createTeacher } from '@/api/teacher'

const teachers = ref<any[]>([])
const show = ref(false)
const form = ref({ teacherNo: '', name: '', phone: '', archiveNo: 'AR-001' })

const load = async () => {
  teachers.value = (await fetchTeachers()).content || []
}

const openDialog = () => (show.value = true)

const save = async () => {
  await createTeacher(form.value)
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
