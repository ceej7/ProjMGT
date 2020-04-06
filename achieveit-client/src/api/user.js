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

export function getByTitle(title) {
  return request({
    url: '/employee/getByTitle/' + title,
    method: 'get'
  })
}

export function getAllClient() {
  return request({
    url: '/client/getAll',
    method: 'get'
  })
}
