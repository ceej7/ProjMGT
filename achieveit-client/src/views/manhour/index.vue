<template>
  <div class="app-container">
    <div class="filter-wrap mb-2">
      <div style="display: flex">
        <el-date-picker
          v-model="selectedDate"
          type="date"
          placeholder="日期查询"
          @keyup.enter.native="handleFilter"
        />
        <!-- <el-input v-model="queryParams.date" placeholder="日期查询" clearable style="width: 200px;" class="mr-3" @keyup.enter.native="handleFilter" /> -->
        <el-button icon="el-icon-search" type="primary" size="small" style="margin-left: 10px" @click="handleFilter">
          搜索
        </el-button>
      </div>
    </div>

    <el-card>
      <m-table
        :data="manhourList"
        :columns="columns"
        :default-sort="{prop: 'mid', order: 'descending'}"
      />
    </el-card>

    <el-pagination
      layout="prev, pager, next"
      :page-count="pageCount"
      @current-change="handleCurrentChange"
    />

  </div>
</template>

<script>
import * as manhourApi from '@/api/manhour'
import MTable from '@/components/MTable'
const dayjs = require('dayjs')

export default {
  name: 'ManhourList',
  components: { MTable },
  data() {
    return {
      manhourStatus: manhourApi.manhourStatus(),
      manhourList: [],
      columns: [{
        prop: 'mid',
        label: 'id',
        sortable: true
      }, {
        prop: 'projectName',
        label: '所属项目',
        sortable: true
      },
      {
        prop: 'function_desc',
        label: '功能名称'
      },
      {
        prop: 'activityName',
        label: '活动名称'
      },
      {
        prop: 'starttime',
        label: '开始时间',
        sortable: true
      },
      {
        prop: 'endtime',
        label: '结束时间',
        sortable: true
      },
      {
        prop: 'statusName',
        label: '状态',
        sortable: true
      },
      {
        prop: 'options',
        label: '操作',
        width: 230,
        align: 'center',
        // icon: 'edit',  //有图标则是下拉框
        options: [
          {
            type: 'primary',
            label: '编辑',
            event: this.handleEdit
          },
          {
            type: 'danger',
            label: '删除',
            event: this.handleDelete
          }
        ]
      }
      ],
      queryParams: {
        page: 0,
        length: 10,
        date: null
      },
      selectedDate: null, // 日期filter使用
      pageCount: 1
    }
  },
  mounted() {
    this.handleFilter()
  },
  methods: {
    initManhourData(manhours) {
      this.manhourList = []
      for (const key in manhours) {
        const data = manhours[key]
        const m = {
          eid: data.employeeProject.employee.eid,
          mid: data.mid,
          fid: data.fid,
          function_desc: data.function_desc,
          pid: data.employeeProject.project_id,
          projectName: data.employeeProject.project.name,
          aid: data.activity.aid,
          activityName: data.activity.def1 + ' - ' + data.activity.def2,
          starttime: dayjs(data.starttime).format('YYYY-MM-DD HH:mm'),
          endtime: dayjs(data.endtime).format('YYYY-MM-DD HH:mm'),
          date: data.date,
          status: data.status,
          statusName: this.manhourStatus[data.status].text
        }
        this.manhourList.push(m)
      }
    },

    handleEdit(row) {
      this.$router.push({ path: '/manhour/edit/' + row.mid, name: 'edit-manhour', params: row })
    },

    handleDelete(row) {
      this.$confirm('此操作将永久删除该工时, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'danger'
      }).then(() => {
        this.deleteManhour(row.eid, row.mid)
      }).catch(() => {
      })
    },
    deleteManhour(eid, mid) {
      manhourApi.deleteManhour(eid, mid).then((response) => {
        console.log(response)
        this.$message.success('删除成功!')
        this.handleFilter()
      })
    },

    handleFilter() {
      // 若选择了日期filter，则转变一下日期格式
      if (this.selectedDate) {
        this.queryParams.date = dayjs(this.selectedDate).format('YYYY-MM-DD')
        this.selectedDate = null
      }
      manhourApi.getManhour(this.queryParams).then(response => {
        this.pageCount = response.responseMap.page_length
        this.initManhourData(response.responseMap.Manhour)
      })
    },
    handleCurrentChange(currentPage) {
      this.queryParams.page = currentPage - 1
      this.handleFilter()
    }
  }
}

</script>
