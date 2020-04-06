<template>
  <div class="app-container">
    <el-table
      v-loading="listLoading"
      :data="tableData"
      stripe
      border
      style="width: 100%; padding-top: 15px; margin-bottom: 5px"
      :default-sort="{prop: 'todo', order: 'descending'}"
    >
      <el-table-column prop="id" sortable label="项目ID" min-width="15" align="center">
        <template slot-scope="scope">
          {{ scope.row.pid }}
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
            <p>技术: {{ scope.row.technique }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag>{{ scope.row.name | projectNameFilter }}</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column prop="todo" sortable class-name="status-col" align="center" min-width="30" label="等待操作">
        <template slot-scope="{row}">
          <el-tag v-for="todo in row.workflow.todo" :type="todo | todoStyleFilter" effect="dark" @click="checkTodoTag(row)">
            {{ todo | todoStringFormat }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="startdate" sortable label="开始日期" min-width="30" align="center">
        <template slot-scope="scope">
          <i class="el-icon-time" />
          {{ scope.row.starttime | formatTime }}
        </template>
      </el-table-column>
      <el-table-column prop="enddate" sortable label="截止日期" min-width="30" align="center">
        <template slot-scope="scope">
          <i class="el-icon-time" />
          {{ scope.row.endtime | formatTime }}
        </template>
      </el-table-column>
      <el-table-column prop="name" label="客户" min-width="30" align="center">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>公司: {{ scope.row.client.company }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag>{{ scope.row.client.name | projectNameFilter }}</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column prop="domain" label="领域" min-width="20" align="center">
        <template slot-scope="scope">
          {{ scope.row.domain }}
        </template>
      </el-table-column>
      <el-table-column class-name="status-col" align="center" min-width="30" label="状态">
        <template slot-scope="{row}">
          <el-tag :type="row.workflow.status | statusTypeFilter">
            {{ row.workflow.status | statusFilter }}
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
import { toManage } from '@/api/project'
import { parseTime } from '@/utils'
import { mapGetters } from 'vuex'
import { generateWhatToDo, codeToString } from '@/utils/workflow'

export default {
  name: 'ApproveList',
  filters: {
    formatTime(time) {
      return parseTime(time)
    },
    todoStringFormat(code) {
      return codeToString([code])
    },
    todoStyleFilter(todo) {
      switch (todo) {
        case -1: return 'info'
        default:
          return 'warning'
      }
    },
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
  data() {
    return {
      search: '',
      pageSize: 10,
      currentPage: 1,
      listLoading: true,
      rolesList: [],
      rawData: [],
      projectList: []
    }
  },
  computed: {
    tableData: function() {
      return this.projectList
        .filter(data => !this.search || data.name.toLowerCase().includes(this.search.toLowerCase()))
        .slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)
    },
    total: function() {
      return this.projectList
        .filter(data => !this.search || data.name.toLowerCase().includes(this.search.toLowerCase())).length
    },
    ...mapGetters([
      'eid',
      'roles'
    ])
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.listLoading = true
      toManage().then(response => {
        this.rawData = response.responseMap.Project
        for (const it of this.rawData) {
          // console.log(it.name, it.workflow.flowbits)
          it.workflow.todo = generateWhatToDo(it.workflow.flowbits, this.roles[0])
          // this.projectForm = this.projectList.filter(data => data.workflow.todo[0] !== -1)
          this.projectList.push(it)
        }

        this.listLoading = false
      })
    },
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage
    },
    checkTodoTag(row) {
      this.$notify({
        title: row.name,
        dangerouslyUseHTMLString: true,
        message: '<p>WorkFlow📒</p>' + codeToString(row.workflow.todo),
        type: 'success',
        duration: 0
      })
      this.$router.push({
        name: 'project-manage',
        params: {
          pid: row.pid
        }
      })
    }
  }
}
</script>
