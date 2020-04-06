<template>
  <div class="app-container">
    <div class="headerspan">
      <span style="font-size:20px;padding-top:20px;display:inline-block;">缺陷详情</span>
      <el-divider />
    </div>
    <el-row>
      <el-col :span="16">
        <el-form
          ref="defectForm"
          :model="defectForm"
          :rules="rules"
          label-width="150px"
          onautocomplete="on"
          class="defectForm"
          span="12"
        >

          <el-form-item label="项目名称" prop="project_id">
            <el-select v-model="defectForm.project_id" class="selector" placeholder="请选择您的项目" :disabled="isEditting">
              <el-option
                v-for="item in projects_doing"
                :key="item.pid"
                :label="item.name"
                :value="item.pid"
              />
            </el-select>
          </el-form-item>

          <el-form-item label="缺陷描述" prop="desc">
            <el-input v-model="defectForm.desc" />
          </el-form-item>

          <el-form-item label="git仓库地址" prop="git_repo">
            <el-input v-model="defectForm.git_repo" />
          </el-form-item>

          <el-form-item label="commit" prop="commit">
            <el-input v-model="defectForm.commit" />
          </el-form-item>

          <el-form-item label="缺陷类型" prop="status">
            <el-select v-model="defectForm.status" class="selector" placeholder="请选择缺陷类型" :disabled="!isEditting">
              <el-option
                v-for="item in allStatus"
                :key="item.value"
                :label="item.text"
                :value="item.value"
              />
            </el-select>
          </el-form-item>

          <el-form-item label="缺陷权限" prop="authority_desc">
            <el-select v-model="defectForm.authority_desc" class="selector" placeholder="请选择缺陷权限">
              <el-option
                v-for="item in authorities"
                :key="item.value"
                :label="item.text"
                :value="item.value"
              />
            </el-select>
          </el-form-item>

          <el-form-item>
            <el-button :loading="loading" type="primary" @click="submitForm('defectForm')">保存</el-button>
            <el-button @click="resetForm('defectForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as defectApi from '@/api/defect'
import * as projectApi from '@/api/project'

export default {
  name: 'DefectNew',
  data() {
    return {
      defectForm: {
        project_id: '',
        desc: '',
        git_repo: '',
        commit: '',
        status: 'bug',
        authority_desc: ''
      },

      rules: {
        project_id: [
          { required: true, message: '请选择项目', trigger: 'change' }
        ],
        desc: [
          { required: true, message: '请输入缺陷描述', trigger: 'blur' },
          { min: 3, message: '长度需要大于 3 个字符', trigger: 'blur' }
        ],
        git_repo: [
          { required: true, message: '请输入git仓库地址', trigger: 'blur' }
        ],
        commit: [
          { required: true, message: '请输入commit', trigger: 'blur' }
        ],
        status: [
          { required: true, message: '需要选择缺陷类型', trigger: 'change' }
        ],
        authority_desc: [
          { required: true, message: '需要选择缺陷权限', trigger: 'change' }
        ]
      },

      projectParams: {
        length: 10,
        page: 0,
        status: 'doing'
      },

      projects_doing: [],
      allStatus: [],
      authorities: [],
      loading: false,
      isEditting: false
    }
  },
  created() {
    this.isEditting = !!this.$route.params.did
    this.populateSelectorData()
  },
  mounted() {
    if (this.isEditting) {
      this.initDefect()
    }
  },
  methods: {
    populateSelectorData() {
      projectApi.fetchProjects(this.projectParams).then(response => {
        this.initProjects(response.responseMap.Project)
      })
      this.initStatus()
      this.initAuthorities()
    },

    initProjects(projects) {
      this.projects_doing = []
      for (const key in projects) {
        const data = projects[key]
        const proj = {
          pid: data.pid,
          name: data.name
        }
        this.projects_doing.push(proj)
      }
    },
    initStatus() {
      this.allStatus = []
      var s = defectApi.defectStatus()
      for (const key in s) {
        this.allStatus.push(s[key])
      }
    },
    initAuthorities() {
      this.authorities = []
      var a = defectApi.defectAuthority()
      for (const key in a) {
        this.authorities.push(a[key])
      }
    },

    // isEditting == true
    initDefect() {
      this.defectForm.did = this.$route.params.did
      this.defectForm.project_id = this.$route.params.project_id
      this.defectForm.desc = this.$route.params.desc
      this.defectForm.git_repo = this.$route.params.git_repo
      this.defectForm.commit = this.$route.params.commit
      this.defectForm.status = this.$route.params.status
      this.defectForm.authority_desc = this.$route.params.authority_desc
    },

    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loading = true
          if (!this.isEditting) { // 新增
            defectApi.createDefect(this.defectForm.project_id, this.defectForm).then(() => {
              this.$message.success('保存成功!')
              this.$router.go(-1)
              this.loading = false
            }).catch(() => {
              this.$message.error('网络错误或意外发生')
            })
          } else { // 编辑
            defectApi.updateDefect(this.defectForm.did, this.defectForm).then(() => {
              this.$message.success('保存成功!')
              this.$router.go(-1)
              this.loading = false
            }).catch(() => {
              this.$message.error('网络错误或意外发生')
            })
          }
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
    }
  }
}
</script>

<style lang="scss" scoped>
  .emptyGif {
    width: 100%;
  }
  .headerspan{
    padding-left: 30px;
  }
  .defectForm{
    width: 100%;
    position: relative;
    padding: 20px;
    .selector{
      width: 400px;
    }
  }
</style>

