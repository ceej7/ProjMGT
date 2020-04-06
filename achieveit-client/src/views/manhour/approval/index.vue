<template>
  <div class="app-container">
    <h2>测试中，请使用Kiki登录</h2>
    <div class="filter-wrap mb-2">
      <el-row style="width: 100%">
        <el-col :xs="24" :sm="24" :md="12" :lg="3">
          <el-select v-model="statusSearch" placeholder="工时状态">
            <el-option label="全部" value="" />
            <el-option
              v-for="s in Object.keys(manhourStatus)"
              :key="manhourStatus[s].value"
              :label="manhourStatus[s].text"
              :value="manhourStatus[s].value"
            />
          </el-select>
        </el-col>

        <el-col :xs="24" :sm="24" :md="12" :lg="7" style="margin-left: 10px">
          <el-input
            v-model.trim="projectName"
            placeholder="搜索项目名称"
            clearable
          />
        </el-col>
      </el-row>
    </div>

    <el-card>
      <m-table
        :data="manhourList"
        :columns="columns"
        :default-sort="{prop: 'mid', order: 'descending'}"
      />
    </el-card>

    <el-pagination
      small
      background
      :current-page="currentPage"
      :page-size="queryParams.length"
      layout="total, jumper, prev, pager, next"
      :total="total"
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
        width: 230,
        label: '所属项目',
        sortable: true
      }, {
        prop: 'employeeName',
        label: '员工姓名'
      }, {
        prop: 'activityName',
        width: 200,
        label: '活动名称'
      }, {
        prop: 'starttime',
        width: 180,
        label: '开始时间',
        sortable: true
      }, {
        prop: 'endtime',
        width: 180,
        label: '结束时间',
        sortable: true
      }, {
        prop: 'statusName',
        label: '状态',
        sortable: true
      }, {
        prop: 'options1',
        label: '审批',
        align: 'center',
        width: 230,
        options: [
          {
            type: 'primary',
            label: '通过',
            event: this.handlePass
          }, {
            type: 'danger',
            label: '不通过',
            event: this.handleSendBack
          }
        ]
      }, {
        prop: 'options2',
        label: '打回',
        align: 'center',
        disabled: true,
        options: [
          {
            type: 'warning',
            label: '打回',
            event: this.handleSendBack
          }
        ]
      }
      ],
      queryParams: {
        fid: '',
        activity_id: '',
        starttime: '',
        endtime: '',
        status: ''
      },
      eid: '10', // todo: 我们仍未知道那天所登录的eid
      statusSearch: '',
      projectName: '',
      currentPage: 1
    }
  },
  computed: {
    tableData: function() {
      return this.manhourList
        .filter(data => !this.projectName || data.projectName.toLowerCase().includes(this.projectName.toLowerCase()))
        .filter(data => !this.statusSearch || data.status === this.statusSearch)
        .slice((this.currentPage - 1) * this.length, this.currentPage * this.length)
    },
    total: function() {
      return this.manhourList
        .filter(data => !this.projectName || data.projectName.toLowerCase().includes(this.projectName.toLowerCase()))
        .filter(data => !this.statusSearch || data.status === this.statusSearch).length
    }
  },
  mounted() {
    this.getManhourList()
  },
  methods: {
    getManhourList() {
      manhourApi.getSubManhour(this.eid).then(response => {
        this.initManhourList(response.responseMap.activities)
      })
    },
    initManhourList(manhours) {
      this.manhourList = []
      for (const key in manhours) {
        const data = manhours[key]
        const m = {
          mid: data.mid,
          fid: data.fid,
          employeeName: data.employeeProject.employee.name,
          function_desc: data.function_desc,
          pid: data.employeeProject.project_id,
          projectName: data.employeeProject.project.name,
          aid: data.activity_id,
          activityName: data.activity.def1 + ' - ' + data.activity.def2,
          starttime: dayjs(data.starttime).format('YYYY-MM-DD HH:mm'),
          endtime: dayjs(data.endtime).format('YYYY-MM-DD HH:mm'),
          date: data.date,
          status: data.status,
          statusName: this.manhourStatus[data.status].text,
          // 每次加载都会重新算一遍
          canSendBack: data.status !== 'unfilled' && dayjs().diff(data.date, 'day') <= 3
        }
        this.manhourList.push(m)
      }
    },
    handlePass(row) {
      this.$confirm('确定通过该工时信息?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'success'
      }).then(() => {
        this.queryParams = {
          fid: row.fid,
          activity_id: row.aid,
          starttime: row.starttime,
          endtime: row.endtime,
          status: 'checked'
        }
        this.editManhour(this.eid, row.mid, this.queryParams)
      }).catch(() => {
      })
    },
    handleReject(row) {
      this.$confirm('确定不通过该工时信息?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'danger'
      }).then(() => {
        this.queryParams = {
          fid: row.fid,
          activity_id: row.aid,
          starttime: row.starttime,
          endtime: row.endtime,
          status: 'unchecked'
        }
        this.editManhour(this.eid, row.mid, this.queryParams)
      }).catch(() => {
      })
    },
    handleSendBack(row) {
      this.$confirm('此操作需要员工重新填写该工时, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.queryParams = {
          fid: row.fid,
          activity_id: row.aid,
          starttime: row.starttime,
          endtime: row.endtime,
          status: 'unfilled'
        }
        this.editManhour(this.eid, row.mid, this.queryParams)
      }).catch(() => {
      })
    },

    editManhour(eid, mid, queryParams) {
      manhourApi.updateManhour(eid, mid, queryParams).then((response) => {
        console.log(response)
        this.$message.success('修改成功!')
        this.getManhourList()
      })
    },
    handleCurrentChange(currentPage) {
      this.queryParams.page = currentPage - 1
    }
  }
}

</script>
