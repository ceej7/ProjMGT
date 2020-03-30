<template>
  <div class="project-container">
    <div class="project-sidebar">
      <el-menu
        default-active="1-4-1"
        class="el-menu-project"
        :collapse="isCollapse"
        background-color="#3f4c60"
        text-color="#fff"
        active-text-color="#ffd04b"
        collapse-transition=true
      >
        <i class="el-icon-s-unfold" @click="changeSidebar" style="color: white;text-align:center"/>

        <!--<el-submenu index="1">-->
        <!--<template slot="title">-->
        <!--<i class="el-icon-location" />-->
        <!--<span slot="title">基本信息</span>-->
        <!--</template>-->
        <!--<el-menu-item-group>-->
        <!--<span slot="title">分组一</span>-->
        <!--<el-menu-item index="1-1">选项1</el-menu-item>-->
        <!--<el-menu-item index="1-2">选项2</el-menu-item>-->
        <!--</el-menu-item-group>-->
        <!--<el-menu-item-group title="分组2">-->
        <!--<el-menu-item index="1-3">选项3</el-menu-item>-->
        <!--</el-menu-item-group>-->
        <!--<el-submenu index="1-4">-->
        <!--<span slot="title">选项4</span>-->
        <!--<el-menu-item index="1-4-1">选项1</el-menu-item>-->
        <!--</el-submenu>-->
        <!--</el-submenu>-->
        <el-menu-item index="1">
          <i class="el-icon-s-order" />
          <span slot="title">基本信息</span>
        </el-menu-item>
        <el-menu-item index="2">
          <i class="el-icon-s-help" />
          <span slot="title">项目状态</span>
        </el-menu-item>
        <el-menu-item index="3">
          <i class="el-icon-s-check" />
          <span slot="title">人员管理</span>
        </el-menu-item>
        <el-menu-item index="3">
          <i class="el-icon-setting" />
          <span slot="title">功能管理</span>
        </el-menu-item>
        <el-menu-item index="3">
          <i class="el-icon-document-delete" />
          <span slot="title">缺陷管理</span>
        </el-menu-item>
        <el-menu-item index="3">
          <i class="el-icon-finished" />
          <span slot="title">归档管理</span>
        </el-menu-item>
      </el-menu>
    </div>
    <div class="project-content">fdf</div>
  </div>
</template>
<script>
export default {
  name: 'Index',
  data() {
    return {
      isCollapse: false
    }
  },
  created() {
    // TODO 修改tagview里的项目名
    this.$route.meta.title = 'sss'
  },
  methods: {
    changeSidebar() {
      this.isCollapse = !this.isCollapse
    }
  }
}
</script>

<style scoped>
  .el-menu-project {
    /*width: 200px;*/
    margin: 10px 20px 10px 10px;
    border-right:0;
  }

  .project-container {
    height: calc(100vh - 84px);
    position: relative;
  }

  .project-sidebar {
    background: #3f4c60;
    max-width: 200px;
    height: 100%;
    float: left;
    overflow: scroll;
  }

  .project-content{
    /*background: #001528;*/
  }

  /*::-webkit-scrollbar*/
  /*{*/
    /*display: none;*/
    /*width: 0;*/
  /*}*/
</style>
