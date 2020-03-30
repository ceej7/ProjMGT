<template>
  <div>
    <div class="tab-container">
      <el-tabs v-model="activeName" tab-position="left">
        <el-tab-pane key="overview" label="基本信息" name="overview">
          <overview :id="id" />
        </el-tab-pane>
        <el-tab-pane key="status" label="项目状态" name="status" />
        <el-tab-pane key="member" label="人员管理" name="member" />
        <el-tab-pane key="function" label="功能管理" name="function" />
        <el-tab-pane key="bug" label="缺陷管理" name="bug" />
        <el-tab-pane key="archive" label="归档管理" name="archive" />

      </el-tabs>
    </div>
  </div>
</template>

<script>
import overview from './Overview'

export default {
  name: 'Index2',
  components: { overview },
  data() {
    return {
      id: this.$route.params.id === undefined ? 1 : this.$route.params.id,
      tabMapOptions: [
        { label: '基本信息', key: 'overview' },
        { label: '项目状态', key: 'status' },
        { label: '人员管理', key: 'member' },
        { label: '功能管理', key: 'function' },
        { label: '缺陷管理', key: 'bug' },
        { label: '归档管理', key: 'archive' }
      ],
      activeName: 'overview',
      createdTimes: 0
    }
  },
  methods: {
    key() {
      return this.$route.path + new Date().toString()
    }
  }
}
</script>

<style scoped>
  .tab-container {
    margin: 20px 10px;
  }
</style>
