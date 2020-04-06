<template>
  <div>
    <el-row class="search" type="flex">
      <el-input
        v-model="input"
        size="small"
        placeholder="搜索项目名称"
        clearable
      />
      <el-button
        size="small"
        class="search-item"
        type="primary"
        icon="el-icon-search"
        @click="search"
      >搜索</el-button>
    </el-row>

    <div class="tab-container">
      <el-tabs v-model="activeName" tab-position="left">
        <el-tab-pane v-for="item in tabMapOptions" :key="item.key" :label="item.label" :name="item.key">
          <keep-alive>
            <tab-pane v-if="activeName==item.key" :type="item.key" :ref="item.key" />
          </keep-alive>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
import tabPane from './components/TabPane'

export default {
  name: 'Project',
  components: {
    tabPane
  },
  data() {
    return {
      tabMapOptions: [
        { label: '全部', key: 'all' },
        { label: '进行中', key: 'doing' },
        { label: '申请中', key: 'applying' },
        { label: '已完成', key: 'done' }
      ],
      activeName: 'all',
      createdTimes: 0,
      input: ''
    }
  },
  computed: {},
  watch: {
    activeName(val) {
      this.$router.push(`${this.$route.path}?tab=${val}`)
    }
  },
  created() {
    // init the default selected tab
    const tab = this.$route.query.tab
    if (tab) {
      this.activeName = tab
    }
  },
  activated() {
  },
  methods: {
    search() {
      this.$refs[this.activeName][0].getList(this.input)
    }
  }
}
</script>

<style scoped>
  .tab-container {
    margin: 10px;
  }

  .search{
    margin: 10px 100px;
    width: 300px;
  }

  .search-item{
    margin: 0 10px;
  }
</style>
