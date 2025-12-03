<template>
  <el-card>
    <template #header>档案信息</template>
    <el-table :data="archives" size="small">
      <el-table-column prop="archiveNo" label="档案编号" />
      <el-table-column prop="archiveLocation" label="存放位置" />
      <el-table-column prop="archiveStatus" label="状态" />
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { fetchArchives } from '@/api/archive'

const archives = ref<any[]>([])

onMounted(async () => {
  archives.value = (await fetchArchives()).content || []
})
</script>
