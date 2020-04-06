<template>
  <div class="container">
    <el-table :data="list" border fit highlight-current-row class="container" @row-click="openProjectDetails">
      <el-table-column
        v-loading="loading"
        align="center"
        label="ID"
        element-loading-text="Loading..."
      >
        <template slot-scope="scope">
          <span>{{ scope.row.pid }}</span>
        </template>
      </el-table-column>

      <el-table-column width="150px" align="center" label="项目开始时间" sortable prop="date">
        <template slot-scope="scope">
          <span>{{ scope.row.starttime | parseTime }}</span>
        </template>
      </el-table-column>

      <el-table-column width="150px" align="center" label="最后更新时间" sortable prop="date">
        <template slot-scope="scope">
          <span>{{ scope.row.endtime | parseTime }}</span>
        </template>
      </el-table-column>

      <el-table-column min-width="200px" align="center" label="项目名">
        <template slot-scope="{row}">
          <span>{{ row.name }}</span>
        <!--<el-tag>{{ row.status }}</el-tag>-->
        </template>
      </el-table-column>

      <el-table-column width="100px" align="center" label="项目经理">
        <template slot-scope="scope">
          <span>{{ scope.row.workflow.pm.name }}</span>
        </template>
      </el-table-column>

      <el-table-column width="100px" align="center" label="上级">
        <template slot-scope="scope">
          <span>{{ scope.row.workflow.sup.name }}</span>
        </template>
      </el-table-column>

      <el-table-column width="100px" align="center" label="公司">
        <template slot-scope="scope">
          <span>{{ scope.row.client.company }}</span>
        </template>
      </el-table-column>

      <!--<el-table-column align="center" label="Readings" width="95">-->
      <!--<template slot-scope="scope">-->
      <!--<span>{{ scope.row.pageviews }}</span>-->
      <!--</template>-->
      <!--</el-table-column>-->

      <el-table-column class-name="status-col" align="center" label="Status">
        <template slot-scope="{row}">
          <el-tag :type="row.workflow.status | statusTypeFilter">
            {{ row.workflow.status | statusFilter }}
          </el-tag>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination">
      <el-pagination
        layout="prev, pager, next"
        :page-count="pageCount"
        @current-change="handleCurrentChange"
      />
    </div>
  </div>
</template>

<script>
import { fetchProjects } from '@/api/project'
// import { parseTime } from '@/utils/index'

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
    parseTime(time) {
      const values = (time || '').split('-')
      if (values.length >= 2) {
        const year = parseInt(values[0], 10)
        const mon = parseInt(values[1], 10)
        const day = parseInt(values[2], 10)

        return year + '年' + mon + '月' + day + '日'
      }
      /* istanbul ignore next */
      return null
    }
  },
  props: {
    type: {
      type: String,
      default: 'all'
    },
    search: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      list: null,
      listQuery: {
        length: 10,
        page: 0,
        name: this.search
      },
      loading: false,
      pageCount: 1
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList(name = '', curPage = 0) {
      this.loading = true
      this.$emit('create') // for test
      if (this.type !== 'all') {
        this.listQuery['status'] = this.type
      }
      this.listQuery.name = name
      this.listQuery.page = curPage
      fetchProjects(this.listQuery,).then(response => {
        this.pageCount = response.responseMap.page_length
        this.list = response.responseMap.Project
        this.loading = false
        console.log(response.responseMap)
      })
    },
    openProjectDetails(row) {
      console.log('you click ' + row.name)
      this.$router.push({
        name: 'project-manage',
        params: {
          pid: row.pid
        }
      })
    }, // `/project/manage?pid=${row.pid}`)

    handleCurrentChange(val) {
      console.log(val)
      this.getList('', val)
    }
  }
}
</script>

<style scoped>
  .container{
    width: 100%;
  }

  .pagination{
    position: relative;
    text-align: right;
  }
</style>
