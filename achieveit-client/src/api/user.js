import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/employee/login',
    method: 'post',
    data
  })
}

export function getMyInfo() {
  return request({
    url: '/employee/get',
    method: 'get'
  })
}
