<template>
  <el-card>
    <template #header>离职记录</template>
    <el-table :data="records" size="small">
      <el-table-column prop="teacherId" label="教师ID" />
      <el-table-column prop="resignationDate" label="离职日期" />
      <el-table-column prop="resignationType" label="类型" />
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { fetchResignations } from '@/api/resignation'

const records = ref<any[]>([])

onMounted(async () => {
  records.value = (await fetchResignations()).content || []
})
</script>
