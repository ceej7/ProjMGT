<template>
  <div>
    <el-divider content-position="left"><el-link type="primary" href="#/project">项目速览</el-link></el-divider>
    <el-table
      :data="tableData"
      stripe
      border
      style="width: 100%; padding-top: 15px; margin-bottom: 5px"
      :default-sort="{prop: 'date', order: 'descending'}"
      @row-click="openProjectDetails"
    >
      <el-table-column prop="id" sortable min-width="15" align="center">
        <template slot-scope="scope">
          {{ scope.row.project_id }}
        </template>
      </el-table-column>
      <el-table-column prop="name" label="项目名称" min-width="50" align="center">
        <template slot="header" slot-scope="scope">
          <el-input
            v-model="search"
            size="mini"
            placeholder="搜索项目名称"
          />
        </template>
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <!--            <p>客户: {{ scope.row.project.client.name }}</p>-->
            <p>技术: {{ scope.row.project.technique }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag>{{ scope.row.project.name | projectNameFilter }}</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column prop="date" sortable label="截止日期" min-width="30" align="center">
        <template slot-scope="scope">
          <i class="el-icon-time" />
          {{ scope.row.project.endtime }}
        </template>
      </el-table-column>
      <el-table-column prop="name" label="客户" min-width="30" align="center">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>公司: {{ scope.row.project.client.company }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag>{{ scope.row.project.client.name | projectNameFilter }}</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column prop="domain" label="领域" min-width="20" align="center">
        <template slot-scope="scope">
          {{ scope.row.project.domain }}
        </template>
      </el-table-column>
      <el-table-column class-name="status-col" align="center" min-width="30" label="状态">
        <template slot-scope="{row}">
          <el-tag :type="row.project.workflow.status | statusTypeFilter">
            {{ row.project.workflow.status | statusFilter }}
          </el-tag>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      small
      background
      :current-page="currentPage"
      :page-size="pageSize"
      layout="total, jumper, prev, pager, next"
      :total="total"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<script>

export default {
  filters: {
    statusTypeFilter(status) {
      const statusMap = {
        rejected: 'danger',
        applying: 'info',
        approved: 'info',
        started: 'success',
        delivering: 'success',
        submitted: 'success',
        achieved: 'success'
      }
      return statusMap[status]
    },
    statusFilter(status) {
      const statusMap = {
        rejected: '立项拒绝',
        applying: '申请中',
        approved: '等待配置',
        started: '项目启动',
        delivering: '项目正在交付',
        submitted: '项目已交付',
        achieved: '项目已归档'
      }
      return statusMap[status]
    },
    projectNameFilter(str) {
      return str.substring(0, 30)
    }
  },
  // eslint-disable-next-line vue/require-prop-types
  props: ['projectList'],
  data() {
    return {
      search: '',
      pageSize: 5,
      currentPage: 1
    }
  },
  computed: {
    tableData: function() {
      return this.projectList
        .filter(data => !this.search || data.project.name.toLowerCase().includes(this.search.toLowerCase()))
        .slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)
    },
    total: function() {
      return this.projectList
        .filter(data => !this.search || data.project.name.toLowerCase().includes(this.search.toLowerCase())).length
    }
  },
  created() {

  },
  methods: {
    openProjectDetails(row) {
      this.$router.push({
        name: 'project-manage',
        params: {
          pid: row.project.pid
        }
      })
    },
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage
    }
  }
}
</script>
