<template>
  <div>
    <div class="tab-container">
      <el-tabs v-model="activeName" tab-position="left">
        <el-tab-pane key="overview" label="基本信息" name="overview">
          <Overview :pid="pid" />
        </el-tab-pane>
        <el-tab-pane key="status" label="项目状态" name="status">
          <Status :pid="pid" />
        </el-tab-pane>
        <el-tab-pane key="member" label="人员管理" name="member" />
        <el-tab-pane key="function" label="功能管理" name="function">
          <Function :pid="pid" />
        </el-tab-pane>
        <el-tab-pane key="bug" label="缺陷管理" name="bug">
          <Defect :pid="pid" />
        </el-tab-pane>
        <el-tab-pane key="device" label="设备管理" name="device" />
        <el-tab-pane key="archive" label="归档管理" name="archive" />
      </el-tabs>
    </div>
  </div>
</template>

<script>
import Overview from './overview/index'
import Status from './status/index'
import Function from './function/index'
import Defect from './defect/index'

export default {
  name: 'Manage',
  components: { Overview, Status, Function, Defect },
  data() {
    return {
      pid: null,
      tabMapOptions: [
        { label: '基本信息', key: 'overview' },
        { label: '项目状态', key: 'status' },
        { label: '人员管理', key: 'member' },
        { label: '功能管理', key: 'function' },
        { label: '缺陷管理', key: 'bug' },
        { label: '设备管理', key: 'device' },
        { label: '归档管理', key: 'archive' }
      ],
      activeName: 'overview',
      createdTimes: 0
    }
  },
  created() {
    this.pid = this.$route.params.pid === undefined ? null : this.$route.params.pid
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
