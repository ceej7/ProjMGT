<template>
  <div class="block">
    <el-col :span="12">
      <el-timeline>
        <el-timeline-item
          v-for="(activity, index) in activities"
          :key="index"
          placement="top"
          :icon="activity.icon"
          :type="activity.type"
          :color="activity.color"
          :size="activity.size"
          :timestamp="activity.timestamp"
        >
          <el-card>
            <h4>{{ activity.content }}</h4>
            <p>曹老师 提交于 2019/4/12 20:46</p>
          </el-card>
        </el-timeline-item>
      </el-timeline>
    </el-col>
    <el-col :span="12">
      <
      <div>
        <img src="@/assets/pivot_dribbble.gif" class="emptyGif">
      </div>
    </el-col>
  </div>
</template>

<script>
export default {
  name: 'Status',
  data() {
    return {
      activities: [{
        name: 'rejected',
        content: '立项拒绝',
        timestamp: '2019-04-02 20:46',
        size: 'large',
        type: 'danger',
        icon: 'el-icon-more'
      }, {
        name: 'applying',
        content: '项目申请中',
        timestamp: '2019-04-02 20:46',
        size: 'large',
        type: 'primary',
        icon: 'el-icon-more'
      }, {
        name: 'approved',
        content: '立项已批准,Config、EPG、QA、PM未进行项目配置',
        timestamp: '2019-04-03 20:46',
        color: '#0bbd87'
      }, {
        name: 'started',
        content: '项目启动',
        timestamp: '2019-04-04 20:46',
        size: 'large',
        type: 'warning'
      }, {
        name: 'delivering',
        content: '项目正在交付',
        timestamp: '2019-04-05 20:46',
        size: 'large',
        type: 'warning'
      },
      {
        name: 'submitted',
        content: '项目已交付',
        timestamp: '2019-04-05 20:46',
        size: 'large',
        type: 'success'
      },
      {
        name: 'achieved',
        content: '项目已归档',
        timestamp: '2019-04-05 20:46',
        type: 'success'
      }]
    }
  }
}
</script>

<style scoped>
  .emptyGif {
    width: 100%;
  }
</style>
