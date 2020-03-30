import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: 'Dashboard 控制台', icon: 'dashboard' }
    }]
  },

  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path*',
        component: () => import('@/views/redirect/index')
      }
    ]
  },

  {
    path: '/project',
    component: Layout,
    redirect: '/project/list',
    name: 'project-manage',
    meta: { title: '我的项目', icon: 'example' },
    children: [
      {
        path: 'list',
        name: 'project-list',
        component: () => import('@/views/project/index'),
        meta: { title: '我的项目', icon: 'table' }
      },
      {
        path: 'new',
        name: 'project-new',
        component: () => import('@/views/tree/index'),
        meta: { title: '新建项目', icon: 'tree' }
      },
      {
        path: 'manage',
        name: 'project-detail',
        component: () => import('@/views/project/manage/index2'),
        meta: { title: '项目信息' },
        hidden: true,
        children: [
          {
            path: 'overview',
            name: 'project-manage-overview',
            component: () => import('@/views/project/manage/Overview'),
            meta: { title: '项目基本信息', icon: 'table' }
          }
        ]
      },
      {
        path: 'manager',
        name: 'project-detail2',
        component: () => import('@/views/project/manage/index'),
        meta: { title: '项目信息' },
        hidden: true
      }
    ]
  },

  {
    path: '/defect',
    component: Layout,
    redirect: '/defect/list',
    name: 'defect-manage',
    meta: { title: '我的缺陷', icon: 'example' },
    children: [
      {
        path: 'list',
        name: 'bug-list',
        component: () => import('@/views/defect/index'),
        meta: { title: '我的缺陷列表', icon: 'form' }
      }
    ]
  },

  {
    path: '/manhour',
    component: Layout,
    redirect: '/manhour/list',
    name: 'manhour-manage',
    meta: { title: '我的工时', icon: 'example' },
    children: [
      {
        path: 'new',
        name: 'manhour-new',
        component: () => import('@/views/form/index'),
        meta: { title: '上报工时', icon: 'form'}
      },
      {
        path: 'list',
        name: 'manhour-list',
        component: () => import('@/views/form/index'),
        meta: { title: '我的工时列表', icon: 'form' }
      }
    ]
  }
]

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 */
export const asyncRoutes = [
  {
    path: '/approval',
    component: Layout,
    redirect: '/approval/list',
    name: 'approval-manage',
    meta: {
      title: '我的审批',
      icon: 'example',
      role: ['pm_manager', 'configurer', 'pm', 'epg_leader', 'qa_manager']
    },
    children: [
      {
        path: 'list',
        component: () => import('@/views/approval/index'),
        name: 'approval-list',
        meta: { title: '我的审批' }
      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
