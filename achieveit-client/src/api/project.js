import request from '@/utils/request'

export function fetchProjects(data) {
  return request({
    url: '/project/myProject',
    method: 'get',
    params: data
  })
}

