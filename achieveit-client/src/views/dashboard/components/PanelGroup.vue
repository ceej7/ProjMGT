<template>
  <el-row :gutter="40" class="panel-group">
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleClick('project')">
        <div class="card-panel-icon-wrapper icon-project">
          <svg-icon icon-class="project" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            项目
          </div>
          <count-to :start-val="0" :end-val="panelData.projectCount" :duration="3000" :prefix="countToPrefix" :suffix="countToSuffix" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleClick('defect')">
        <div class="card-panel-icon-wrapper icon-defect">
          <svg-icon icon-class="defect" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            缺陷
          </div>
          <count-to :start-val="0" :end-val="panelData.defectCount" :duration="3000" :prefix="countToPrefix" :suffix="countToSuffix" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleClick('risk')">
        <div class="card-panel-icon-wrapper icon-risk">
          <svg-icon icon-class="risk" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            风险
          </div>
          <count-to :start-val="0" :end-val="panelData.riskCount" :duration="3000" :prefix="countToPrefix" :suffix="countToSuffix" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleClick('manhour')">
        <div class="card-panel-icon-wrapper icon-manhour">
          <svg-icon icon-class="manhour" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            工时
          </div>
          <count-to :start-val="0" :end-val="panelData.manhourCount" :duration="3000" :prefix="countToPrefix" :suffix="countToSuffixManhour" class="card-panel-num" />
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import CountTo from 'vue-count-to'

export default {
  components: {
    CountTo
  },
  // eslint-disable-next-line vue/require-prop-types
  props: ['panelData'],
  data() {
    return {
      countToPrefix: '',
      countToSuffix: ' 个',
      countToSuffixManhour: ' 小时'
    }
  },
  methods: {
    handleClick(type) {
      switch (type) {
        case 'project':
          this.$router.push('/project')
          break
        case 'defect':
          this.$router.push('/defect')
          break
        case 'risk':
          this.$router.push('/risk')
          break
        case 'manhour':
          this.$router.push('/manhour')
          break
        default:
          this.$message({
            message: '不应该出现这个',
            type: 'warning'
          })
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.panel-group {
  margin-top: 18px;

  .card-panel-col {
    margin-bottom: 32px;
  }

  .card-panel {
    height: 108px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);

    &:hover {
      .card-panel-icon-wrapper {
        color: #fff;
      }

      .icon-project {
        background: #40c9c6;
      }

      .icon-defect {
        background: #36a3f7;
      }

      .icon-risk {
        background: #f4516c;
      }

      .icon-manhour {
        background: #34bfa3
      }
    }

    .icon-project {
      color: #40c9c6;
    }

    .icon-defect {
      color: #36a3f7;
    }

    .icon-risk {
      color: #f4516c;
    }

    .icon-manhour {
      color: #34bfa3
    }

    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }

    .card-panel-icon {
      float: left;
      font-size: 48px;
    }

    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;

      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }

      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}

@media (max-width:550px) {
  .card-panel-description {
    display: none;
  }

  .card-panel-icon-wrapper {
    float: none !important;
    width: 100%;
    height: 100%;
    margin: 0 !important;

    .svg-icon {
      display: block;
      margin: 14px auto !important;
      float: none !important;
    }
  }
}
</style>
