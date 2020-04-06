<template>
  <el-card class="box-card" shadow="hover">
    <div slot="header" class="clearfix">
      <span>{{ cardname }}
        <el-avatar
          v-if="type === 'member' && content!==null"
          :src="content.portrait"
          fit="scale-down"
        />
      </span>
      <!--<el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>-->
    </div>
    <div v-if="type === 'member' && content!==null" class="text item">
      <div v-for="(value,key) in memberInfoMap" :key="key" class="info-line">
        {{ value }} : {{ content[key] }}
      </div>
    </div>

    <div v-if="type==='company' && content!==null" class="text item">
      <div v-for="(value,key) in companyInfoMap" :key="key" class="info-line">
        {{ value }} : {{ content[key] }}
      </div>
    </div></el-card>
</template>

<script>
export default {
  name: 'SubFuncCard',
  props: ['cardname', 'type', 'content'],
  data() {
    return {
      memberInfoMap: {
        'name': '姓名👴🏻',
        'email': '邮箱📮',
        'address': '地址🏠',
        'department': '部门',
        'phone': '联系方式',
        'title': '职称'
      },
      companyInfoMap: {
        'name': '客户',
        'company': '公司',
        'grade': '级别',
        'email': '邮箱',
        'phone': '联系方式',
        'address': '地址🏠'
      }
    }
  }
}
</script>

<style scoped>
  .info-line{
    line-height: 1.5;
  }
</style>
