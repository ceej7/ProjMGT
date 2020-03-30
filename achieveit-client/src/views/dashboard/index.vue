<template>
  <div class="dashboard-container">
    <panel-group :panel-data="panelData" />
    <split-pane :min-percent="40" :default-percent="50" split="vertical">
      <template slot="paneL">
        <ProjectTableDashboard :project-list="projectData" />
        <ManhourTableDashboard :manhour-list="manhourData"/>
        <PropertyTableDashboard :property-list="propertyData"/>
      </template>
      <template slot="paneR">
        <DefectTableDashboard :defect-list="defectData"/>
        <RiskTableDashboard :risk-list="riskData" />
      </template>
    </split-pane>
  </div>
</template>

<script>
import PanelGroup from './components/PanelGroup'
import ProjectTableDashboard from './components/ProjectTableDashboard'
import RiskTableDashboard from './components/RiskTableDashboard'
import DefectTableDashboard from './components/DefectTableDashboard'
import ManhourTableDashboard from './components/ManhourTableDashboard'

import { fetchDashboard } from '@/api/dashboard'
import { parseTime } from '@/utils/index'
import splitPane from 'vue-splitpane'
import PropertyTableDashboard from './components/PropertyTableDashboard'

export default {
  name: 'Dashboard',
  components: {
    PropertyTableDashboard,
    ManhourTableDashboard,
    DefectTableDashboard,
    RiskTableDashboard,
    ProjectTableDashboard,
    PanelGroup,
    splitPane
  },
  data() {
    return {
      dashboardData: null,
      panelData: {
        projectCount: 0,
        riskCount: 0,
        defectCount: 0,
        manhourCount: 0
      },
      projectData: [],
      riskData: [],
      defectData: [],
      manhourData: [],
      propertyData: []
    }
  },
  computed: {

  },
  created() {
    this.populateDashboardData()
  },
  activated() {
    // keep-alive下如果要刷新，这里写函数，否则就空着
    this.populateDashboardData()
  },
  methods: {
    populateDashboardData() {
      fetchDashboard().then(response => {
        this.dashboardData = response.responseMap
        const { projects, risks, defects, manhours, properties } = this.dashboardData
        let manhoursTotal = 0
        for (const it of manhours) {
          const endDate = new Date(parseTime(it.endtime))
          const startDate = new Date(parseTime(it.starttime))
          manhoursTotal += Math.floor((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60))
        }
        this.panelData = {
          projectCount: projects.length,
          riskCount: risks.length,
          defectCount: defects.length,
          manhourCount: manhoursTotal
        }
        this.projectData = projects
        for (const it of this.projectData) {
          it.project.endtime = parseTime(it.project.endtime).split(' ')[0]
        }
        this.riskData = risks
        this.defectData = defects
        this.manhourData = manhours
        for (const it of this.manhourData) {
          it.endtime = parseTime(it.endtime)
          it.starttime = parseTime(it.starttime)
        }
        this.propertyData = properties
        for (const it of this.propertyData) {
          it.expire_time = parseTime(it.expire_time)
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
  .dashboard-container {
    padding: 32px;
    background-color: rgb(240, 242, 245);
    position: relative;
    height: 180vh;
  }

</style>
