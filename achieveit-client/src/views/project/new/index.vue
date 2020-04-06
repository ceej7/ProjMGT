<template>
  <div class="app-container">
    <div class="headerspan">
      <span style="font-size:20px;padding-top:20px;display:inline-block;">创建一个全新的项目</span>
      <el-divider />
    </div>
    <el-row>
      <el-col :span="16">
        <el-form
          ref="projectForm"
          :model="projectForm"
          :rules="rules"
          label-width="150px"
          onautocomplete="on"
          class="projectForm"
          span="12"
        >
          <el-form-item label="项目名称" prop="name">
            <el-input v-model="projectForm.name" />
          </el-form-item>
          <el-form-item label="项目起止时间" prop="date">
            <el-date-picker
              v-model="projectForm.date"
              type="datetimerange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            />
          </el-form-item>
          <el-form-item label="项目技术" prop="technique">
            <el-input v-model="projectForm.technique" />
          </el-form-item>
          <el-form-item label="项目领域" prop="domain">
            <el-input v-model="projectForm.domain" />
          </el-form-item>
          <el-form-item label="项目客户" prop="client">
            <el-select v-model="projectForm.client" class="selector" filterable placeholder="请选择">
              <el-option
                v-for="item in clients"
                :key="item.cid"
                :label="item.name"
                :value="item.cid"
              >
                <span style="float: left">{{ item.name }}</span>
                <span style="float: right; color: #8492a6; font-size: 13px">{{ item.company }}</span>
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="项目配置人员" prop="configurer_eid">
            <el-select v-model="projectForm.configurer_eid" class="selector" filterable placeholder="请选择配置人员">
              <el-option
                v-for="item in configuerers"
                :key="item.eid"
                :label="item.name"
                :value="item.eid"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="项目EPG Leader" prop="epgleader_eid">
            <el-select v-model="projectForm.epgleader_eid" class="selector" filterable placeholder="请选择EPG Leader">
              <el-option
                v-for="item in epgleaders"
                :key="item.eid"
                :label="item.name"
                :value="item.eid"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="项目QA Manager" prop="qamanager_eid">
            <el-select v-model="projectForm.qamanager_eid" class="selector" filterable placeholder="请选择QA Manager">
              <el-option
                v-for="item in qamanagers"
                :key="item.eid"
                :label="item.name"
                :value="item.eid"
              />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button :loading="loading" type="primary" @click="submitForm('projectForm')">立即创建</el-button>
            <el-button @click="resetForm('projectForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-col>
      <el-col :span="8">
        <div>
          <img src="@/assets/fast_company_lab.gif" class="emptyGif">
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getByTitle, getAllClient } from '@/api/user'
import { newProject } from '@/api/project'
import { mapGetters } from 'vuex'

export default {
  name: 'ProjectNew',

  data() {
    return {
      projectForm: {
        name: '',
        date: '',
        technique: '',
        domain: '',
        client: '',
        configurer_eid: '',
        epgleader_eid: '',
        qamanager_eid: ''
      },

      rules: {
        name: [
          { required: true, message: '请输入项目名称', trigger: 'blur' },
          { min: 3, message: '长度需要大于 3 个字符', trigger: 'blur' }
        ],
        date: [
          { type: 'array', required: true, message: '需要填写起止时间', trigger: 'change' }
        ],
        technique: [
          { required: true, message: '请输入项目技术', trigger: 'blur' },
          { min: 3, message: '长度需要大于 3 个字符', trigger: 'blur' }
        ],
        domain: [
          { required: true, message: '请输入项目领域', trigger: 'blur' },
          { min: 3, message: '长度需要大于 3 个字符', trigger: 'blur' }
        ],
        client: [
          { required: true, message: '请填写客户', trigger: 'change' }
        ],
        configurer_eid: [
          { required: true, message: '请填写配置人员', trigger: 'change' }
        ],
        epgleader_eid: [
          { required: true, message: '请填写EPG Leader', trigger: 'change' }
        ],
        qamanager_eid: [
          { required: true, message: '请填写QA Manager', trigger: 'change' }
        ]
      },
      clients: [],
      configuerers: [],
      epgleaders: [],
      qamanagers: [],
      loading: false
    }
  },
  computed: {
    ...mapGetters([
      'eid'
    ])
  },
  created() {
    this.populateSelectorData()
  },
  methods: {
    populateSelectorData() {
      getAllClient().then(response => {
        this.clients = response.responseMap.clients
      })
      getByTitle('configurer').then(response => {
        this.configuerers = response.responseMap.employees
      })
      getByTitle('epg_leader').then(response => {
        this.epgleaders = response.responseMap.employees
      })
      getByTitle('qa_manager').then(response => {
        this.qamanagers = response.responseMap.employees
      })
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loading = true
          this.projectForm.startdate = this.projectForm.date[0]
          this.projectForm.enddate = this.projectForm.date[1]
          newProject(this.projectForm, this.eid).then((response) => {
            this.$router.push({
              name: 'project-manage',
              params: {
                pid: response.responseMap.project.pid
              }
            })
            this.$notify({
              title: '新建项目',
              message: '新建项目成功，跳转到项目详情页面',
              type: 'success',
              duration: 0
            })
            this.loading = false
          }).catch(() => {
            this.$notify.error({
              title: '新建项目',
              message: '网络错误或意外发生，可以稍后重试'
            })
            this.loading = false
          })
        } else {
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
  .projectForm{
    width: 100%;
    position: relative;
    padding: 20px;
    .selector{
      width: 400px;
    }
  }
</style>

