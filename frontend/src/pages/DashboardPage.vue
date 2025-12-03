<template>
  <el-row :gutter="16">
    <el-col :span="6" v-for="card in cards" :key="card.title">
      <el-card shadow="hover">
        <div class="stat-title">{{ card.title }}</div>
        <div class="stat-value">{{ card.value }}</div>
      </el-card>
    </el-col>
  </el-row>
  <el-card class="mt">
    <template #header>最近档案流转</template>
    <el-table :data="flows" size="small">
      <el-table-column prop="archiveId" label="档案ID" />
      <el-table-column prop="flowType" label="类型" />
      <el-table-column prop="operatedAt" label="时间" />
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { fetchFlows } from '@/api/flow'

const cards = ref([
  { title: '总教师数', value: 0 },
  { title: '在职', value: 0 },
  { title: '离职', value: 0 },
  { title: '档案在库', value: 0 },
])
const flows = ref<any[]>([])

onMounted(async () => {
  flows.value = (await fetchFlows(0, 5)).content || []
})
</script>

<style scoped>
.stat-title {
  color: #888;
}
.stat-value {
  font-size: 28px;
  font-weight: bold;
}
.mt {
  margin-top: 16px;
}
</style>
