import request from '@/utils/request'

export function fetchDashboard() {
  return request({
    url: '/employee/myDashBoard',
    method: 'get'
  })
}

