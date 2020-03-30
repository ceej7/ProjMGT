<template>
  <div style="height: calc(100vh - 114px);overflow: scroll">
    <el-container>
      <el-header height="auto">
        <div>
          <h2>{{ project.name }}</h2>
          <div>描述：{{ project.technique }}</div>
        </div>
      </el-header>
      <el-divider />
      <el-main>
        <div>基本信息</div>
        <div v-for="(key,item) in project">
          <h5>{{ item }}: {{ key }}</h5>
        </div>
      </el-main>
    </el-container>
  </div>
</template>
<script>
import { fetchProjects } from '@/api/project'

export default {
  name: 'Overview',
  props: ['id'],
  data() {
    return {
      project: null,
      defaultProps: {
        children: 'children',
        label: 'label'
      }
    }
  },
  created() {
    console.log('overview created')
    fetchProjects({
      length: 1,
      page: 0,
      // type: this.type,
      name: ''
    },).then(response => {
      this.project = response.responseMap.Project[0]
    })
  },
  methods: {
    handleNodeClick(data) {
      console.log(data)
    }
  }
}
</script>

<style scoped>
  .el-header {
    /*background-color: #B3C0D1;*/
    color: #333;
    /*min-height: 100px;*/
    padding: 0 15px;
  }

  .el-aside {
    background-color: #D3DCE6;
    color: #333;
  }

  .el-main {
    background-color: #E9EEF3;
    color: #333;
  }
</style>
