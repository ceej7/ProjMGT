import request from '@/utils/request'

export function getActivities(){
  return request({
    url: 'manhour/activity',
    method: 'get'
  })
}
