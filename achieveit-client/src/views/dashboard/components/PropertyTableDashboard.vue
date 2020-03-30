<template>
  <div>
    <el-divider content-position="left"><el-link type="primary" href="#">设备速览</el-link></el-divider>
    <el-table
      :data="tableData"
      stripe
      border
      style="width: 100%; padding-top: 15px; margin-bottom: 5px"
      :default-sort="{prop: 'date', order: 'descending'}"
    >
      <el-table-column prop="id" sortable min-width="10" align="center">
        <template slot-scope="scope">
          {{ scope.row.poid }}
        </template>
      </el-table-column>
      <el-table-column prop="desc" label="描述" min-width="60" align="center">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>设备ID: {{ scope.row.property.pid }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag>{{ scope.row.property.desc }}</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column prop="date" sortable label="到期于" min-width="40" align="center">
        <template slot-scope="scope">
          <i class="el-icon-time" />
          {{ scope.row.expire_time }}
        </template>
      </el-table-column>
      <el-table-column prop="name" label="来自于" min-width="30" align="center">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>项目ID: {{ scope.row.project.pid }}</p>
            <p>领域: {{ scope.row.project.domain }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag>{{ scope.row.project.name | projectNameFilter }}</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column min-width="30" align="center">
        <template slot="header" slot-scope="scope">
          <el-input
            v-model="search"
            size="mini"
            placeholder="搜索项目名称"
          />
        </template>
        <template slot-scope="scope">
          <el-button
            type="primary"
            size="mini"
            @click.native.prevent="clickRow(scope.row.project_id)"
          >
            跳转项目页面
          </el-button>
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
  props: ['propertyList'],
  data() {
    return {
      search: '',
      pageSize: 3,
      currentPage: 1
    }
  },
  computed: {
    tableData: function() {
      return this.propertyList
        .filter(data => !this.search || data.project.name.toLowerCase().includes(this.search.toLowerCase()))
        .slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)
    },
    total: function() {
      return this.propertyList
        .filter(data => !this.search || data.project.name.toLowerCase().includes(this.search.toLowerCase())).length
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
