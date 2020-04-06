import request from '@/utils/request'

// 获取我的缺陷列表
export function getDefects(params) {
  return request({
    url: '/defect/myDefect',
    method: 'get',
    params: params
  })
}

// 获取某个项目的缺陷列表
export function getDefectsByPid(pid) {
  return request({
    url: `/defect/getByPid/${pid}`,
    method: 'get'
  })
}

/* 新增一个缺陷
*/
export function createDefect(pid, data) {
  return request({
    url: `/defect/${pid}`,
    method: 'post',
    data
  })
}

// 删除一个缺陷
export function deleteDefect(did) {
  return request({
    url: `/defect/${did}`,
    method: 'delete'
  })
}

// 更新一个缺陷
export function updateDefect(did, data) {
  return request({
    url: `/defect/${did}`,
    method: 'put',
    data
  })
}

// 缺陷状态，仅支持’bug’,’reopen’,’fixed’,’wontfix’,’feature’,’closed’
export function defectStatus() {
  return ({
    'fixed': {
      value: 'fixed',
      color: 'success',
      status: 'success',
      text: 'fixed'
    },
    'closed': {
      value: 'closed',
      color: 'success',
      status: 'success',
      text: 'closed'
    },
    'reopen': {
      value: 'reopen',
      color: 'warning',
      status: 'warning',
      text: 'reopen'
    },
    'wontfix': {
      value: 'wontfix',
      color: 'info',
      status: 'warning',
      text: 'wontfix'
    },
    'bug': {
      value: 'bug',
      color: 'danger',
      status: 'error',
      text: 'bug'
    },
    'feature': {
      value: 'feature',
      color: 'warning',
      status: 'success',
      text: 'feature'
    }
  })
}

// 缺陷权限，注意这里的权限的主语是【缺陷】
// 成员的authority：[noneAuthority/rdLeaderAuthority/qaLeaderAuthority/pmAuthority]
// 拥有[noneAuthority]的成员只能管理自己发起的缺陷
// 拥有[rdLeaderAuthority]的成员只能管理自己发起的缺陷，noneAuthority/rdLeaderAuthority的缺陷
// 拥有[qaLeaderAuthority]的成员只能管理自己发起的缺陷，noneAuthority/qaLeaderAuthority的缺陷
// 拥有[pmAuthority]的成员能管理所有缺陷
export function defectAuthority() {
  return ({
    'noneAuthority': {
      value: 'noneAuthority',
      text: '项目成员均可修改'
    },
    'rdLeaderAuthority': {
      value: 'rdLeaderAuthority',
      text: '项目经理、RD领导和创建者均可以修改'
    },
    'qaLeaderAuthority': {
      value: 'qaLeaderAuthority',
      text: '项目经理、QA领导和创建者均可以修改'
    },
    'pmAuthority': {
      value: 'pmAuthority',
      text: '只有项目经理和创建者可以修改'
    }
  })
}
