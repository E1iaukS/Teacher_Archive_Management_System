<template>
  <el-card>
    <template #header>档案流转记录</template>
    <el-table :data="flows" size="small">
      <el-table-column prop="archiveId" label="档案ID" />
      <el-table-column prop="flowType" label="类型" />
      <el-table-column prop="fromLocation" label="来源" />
      <el-table-column prop="toLocation" label="去向" />
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { fetchFlows } from '@/api/flow'

const flows = ref<any[]>([])

onMounted(async () => {
  flows.value = (await fetchFlows()).content || []
})
</script>
