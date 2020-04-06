<template>
  <div style="height: calc(100vh - 104px);overflow: scroll">
    <el-container>
      <el-header height="auto">
        <div>
          <h2>{{ project.name }}</h2>
          <!--<div>描述：{{ project.technique }}</div>-->
        </div>
      </el-header>
      <!--<el-divider />-->
      <el-main>
        <!--<div>基本信息</div>-->
        <!--<div-->
        <!--v-for="(name,key) in projectInfoMap"-->
        <!--:key="key"-->
        <!--v-loading="infoLoading"-->
        <!--&gt;-->
        <!--<h5>{{ name }} : {{ project[key] }}</h5>-->
        <!--</div>-->
        <el-collapse class="basic-info" value="1">
          <el-collapse-item title="基本信息" name="1">
            <div
              v-for="(name,key) in projectInfoMap"
              :key="key"
              v-loading="infoLoading"
              style="font-size: 14px;line-height: 2"
            >
              <div v-if="key == 'starttime' || key == 'endtime'">
                  {{ name }} : {{ convertDate(project[key])}}
              </div>
              <div v-else>{{ name }} : {{ project[key] }}</div>
            </div>
          </el-collapse-item>
        </el-collapse>
        <el-row :gutter="24">
          <el-col :xs="16" :sm="16" :lg="12">
            <div class="chart-wrapper">
              <SubFuncCard cardname="客户" type="company" :content="project.client" />
            </div>
          </el-col>
          <el-col
            v-for="(value,key) in cardMap"
            :key="key"
            :xs="16"
            :sm="16"
            :lg="12"
          >
            <div class="chart-wrapper">
              <SubFuncCard :cardname="value.name" type="member" :content="project.workflow[key]" />
            </div>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
  </div>
</template>
<script>
import { fetchProjectByPid } from '@/api/project'
import SubFuncCard from './components/SubFuncCard'
const dayjs = require('dayjs')

export default {
  name: 'Overview',
  components: { SubFuncCard },
  props: {
    pid: { type: String }
  },
  data() {
    return {
      infoLoading: true,
      project: {
        'name': '',
        'client': null,
        'workflow': {
          'pm': null,
          'sup': null,
          'configurer': null,
          'epgleader': null,
          'qamanager': null
        }
      },
      projectInfoMap: {
        'pid': '项目id',
        'technique': '技术',
        'starttime': '项目开始时间',
        'endtime': '项目结束时间',
        'domain': '领域'
      },
      cardMap: {
        'pm': {
          name: '产品经理'
        },
        'sup': {
          name: '上级'
        },
        'configurer': {
          name: '配置员'
        },
        'epgleader': {
          name: 'EPG Leader'
        },
        'qamanager': {
          name: 'QA Leader'
        }
      }

    }
  },
  created() {
    this.fetchProject()
  },
  methods: {
    fetchProject() {
      this.infoLoading = true
      fetchProjectByPid(this.pid).then(response => {
        this.project = response.responseMap.Project
        this.infoLoading = false
      })
    },
    convertDate(date) {
      return dayjs(date).format('YYYY-MM-DD')
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
    /*background-color: #E9EEF3;*/
    /*color: #333;*/
    padding: 0 20px 0 20px;
  }

  .basic-info{
    margin: 10px;
  }

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }

  @media (max-width:1024px) {
    .chart-wrapper {
      padding: 8px;
    }
  }
</style>
