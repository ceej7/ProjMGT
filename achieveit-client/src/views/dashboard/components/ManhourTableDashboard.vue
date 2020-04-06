<template>
  <div>
    <el-divider content-position="left"><el-link type="primary" href="#/manhour">工时速览</el-link></el-divider>
    <el-table
      :data="tableData"
      stripe
      border
      style="width: 100%; padding-top: 15px; margin-bottom: 5px"
      :default-sort="{prop: 'grade', order: 'descending'}"
    >
      <el-table-column prop="id" sortable min-width="10" align="center">
        <template slot-scope="scope">
          {{ scope.row.mid }}
        </template>
      </el-table-column>
      <el-table-column prop="date" sortable label="开始时间" min-width="35" align="center">
        <template slot-scope="scope">
          <i class="el-icon-time" />
          {{ scope.row.starttime }}
        </template>
      </el-table-column>
      <el-table-column prop="date" sortable label="结束时间" min-width="35" align="center">
        <template slot-scope="scope">
          <i class="el-icon-time" />
          {{ scope.row.endtime }}
        </template>
      </el-table-column>
      <el-table-column prop="name" label="来自于" min-width="30" align="center">
        <template slot="header" slot-scope="scope">
          <el-input
            v-model="search"
            size="mini"
            placeholder="搜索项目名称"
          />
        </template>
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>项目ID: {{ scope.row.employeeProject.project.pid }}</p>
            <p>领域: {{ scope.row.employeeProject.project.domain }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">{{ scope.row.employeeProject.project.name | projectNameFilter }}</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column prop="grade" sortable label="状态" min-width="30" align="center">
        <template slot-scope="scope">
          <el-tag type="warning">
            {{ scope.row.status }}
          </el-tag>
        </template>
      </el-table-column>
      <!--      <el-table-column min-width="30" align="center">-->
      <!--        <template slot="header" slot-scope="scope">-->
      <!--          <el-input-->
      <!--            v-model="search"-->
      <!--            size="mini"-->
      <!--            placeholder="搜索项目名称"-->
      <!--          />-->
      <!--        </template>-->
      <!--        <template slot-scope="scope">-->
      <!--          <el-button-->
      <!--            type="primary"-->
      <!--            size="mini"-->
      <!--            @click.native.prevent="clickRow(scope.row.project_id)"-->
      <!--          >-->
      <!--            跳转项目页面-->
      <!--          </el-button>-->
      <!--        </template>-->
      <!--      </el-table-column>-->
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
    statusFilter(status) {
      const statusMap = {
        success: 'success',
        pending: 'danger'
      }
      return statusMap[status]
    },
    projectNameFilter(str) {
      return str.substring(0, 30)
    }
  },
  // eslint-disable-next-line vue/require-prop-types
  props: ['manhourList'],
  data() {
    return {
      search: '',
      pageSize: 4,
      currentPage: 1
    }
  },
  computed: {
    tableData: function() {
      return this.manhourList
        .filter(data => !this.search || data.employeeProject.project.name.toLowerCase().includes(this.search.toLowerCase()))
        .slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)
    },
    total: function() {
      return this.manhourList
        .filter(data => !this.search || data.employeeProject.project.name.toLowerCase().includes(this.search.toLowerCase())).length
    }
  },
  created() {

  },
  methods: {
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage
    },
    clickRow(pid) {
      this.$router.push('/project/' + pid)
    }
  }
}
</script>
