<template>
  <div class="app-container">
    <div class="filter-wrap mb-2">
      <el-row style="width: 100%">
        <el-col :xs="24" :sm="24" :md="12" :lg="3">
          <el-select v-model="status" placeholder="缺陷状态">
            <el-option label="all" value="" />
            <el-option
              v-for="s in Object.keys(defectStatus)"
              :key="defectStatus[s].value"
              :label="defectStatus[s].text"
              :value="defectStatus[s].value"
            />
          </el-select>
        </el-col>

        <el-col :xs="24" :sm="24" :md="12" :lg="7" style="margin-left: 10px">
          <el-input
            v-model.trim="desc"
            placeholder="搜索缺陷描述"
            clearable
          />
        </el-col>
        <!-- <el-col :xs="24" :sm="24" :md="12" :lg="1" style="margin-left: 10px">
          <el-button type="primary" icon="el-icon-search">
            搜索
          </el-button>
        </el-col> -->
      </el-row>

    </div>

    <el-table
      :data="tableData"
      border
      fit
      highlight-current-row
      style="width: 100%"
    >
      <el-table-column prop="did" label="ID" width="70" align="center" />
      <el-table-column prop="desc" label="缺陷描述" show-overflow-tooltip>
        <template slot-scope="{row}">
          <span style="margin-left: 10px">{{ row.desc }}</span>
        </template>
      </el-table-column>

      <el-table-column prop="commit" label="commit" width="150" align="center" show-overflow-tooltip>
        <template slot-scope="{row}">
          <el-link :href="row.git_repo" target="_blank" type="primary">{{ row.commit }}</el-link>
        </template>
      </el-table-column>

      <el-table-column prop="projectName" label="项目名称" width="150" align="center" show-overflow-tooltip>
        <template slot-scope="{row}">
          <span style="margin-left: 10px">{{ row.projectName }}</span>
        </template>
      </el-table-column>

      <el-table-column label="git仓库地址" width="150" show-overflow-tooltip align="center">
        <template slot-scope="{row}">
          <el-link
            :href="row.git_repo"
            target="_blank"
            type="primary"
          >{{ row.git_repo }}
          </el-link>
        </template>
      </el-table-column>

      <el-table-column prop="status" label="状态" width="85" align="center" show-overflow-tooltip>
        <template slot-scope="{row}">
          <el-tag
            :type="defectStatus[row.status].color"
            disable-transitions
            effect="plain"
          >{{ defectStatus[row.status].text }}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column label="权限" width="150" show-overflow-tooltip align="center">
        <template slot-scope="{row}">
          <span style="margin-left: 10px">{{ defectAuthority[row.authority_desc].text }}</span>
        </template>
      </el-table-column>

      <el-table-column fixed="right" label="操作" width="150" align="center">
        <template slot-scope="{row}">
          <el-button
            size="small"
            @click="handleEdit(row)"
          >编辑
          </el-button>
          <el-button
            size="small"
            type="danger"
            @click="handleDelete(row.did)"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      :current-page="currentPage"
      :page-size="length"
      :total="total"
      layout="total, jumper, prev, pager, next"
      @current-change="handleCurrentChange"
    />

  </div>
</template>

<script type="text/ecmascript-6">

import * as defectApi from '@/api/defect'

export default {
  name: 'Defect',
  props: { pid: String },
  data: function() {
    return {
      defectStatus: defectApi.defectStatus(),
      defectAuthority: defectApi.defectAuthority(),
      defectList: [],
      status: '',
      desc: '',
      currentPage: 1,
      length: 10
    }
  },
  computed: {
    tableData: function() {
      return this.defectList
        .filter(data => !this.desc || data.desc.toLowerCase().includes(this.desc.toLowerCase()))
        .filter(data => !this.status || data.status === this.status)
        .slice((this.currentPage - 1) * this.length, this.currentPage * this.length)
    },
    total: function() {
      return this.defectList
        .filter(data => !this.desc || data.desc.toLowerCase().includes(this.desc.toLowerCase())).length
    }
  },
  mounted() {
    this.getDefectList()
  },
  methods: {
    handleEdit(row) {
      this.$router.push({ path: '/defect/edit/' + row.id, name: 'edit-defect', params: row })
    },
    handleDelete(did) {
      this.$confirm('此操作将永久删除该缺陷, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'danger'
      }).then(() => {
        this.deleteDefect(did)
      }).catch(() => {
      })
    },
    deleteDefect(did) {
      defectApi.deleteDefect(did).then((response) => {
        console.log(response)
        this.$message.success('删除成功!')
        this.getDefectList()
      })
    },

    initDefectData(defects) {
      this.defectList = []
      for (const key in defects) {
        const data = defects[key]
        const defect = {
          did: data.did,
          desc: data.desc,
          commit: data.commit,
          git_repo: data.git_repo,
          project_id: data.project_id,
          projectName: data.employeeProject.project.name, // 和defect页面的稍稍有点不一样，因为接口返回的数据有点不同
          status: data.status,
          authority_desc: data.authority_desc
        }
        this.defectList.push(defect)
      }
    },
    getDefectList() {
      defectApi.getDefectsByPid(this.pid).then(response => {
        console.log(response.responseMap.Defect, this.pid)
        this.initDefectData(response.responseMap.Defect)
      })
    },
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage
    }
  }
}
</script>
