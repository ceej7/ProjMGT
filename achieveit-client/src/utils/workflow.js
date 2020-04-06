function generateBits(flowbits) {
  const bits = []
  for (let i = 0; i < 8; i++) {
    // console.log(1 << i)
    bits.push((flowbits >>> i) & 1)
  }
  return bits
}

export function codeToString(code) {
  console.log(code)
  let ret = ''
  const performanceMap = {
    '-1': '无可执行操作',
    '0': '无可执行操作',
    '1': '无可执行操作',
    '2': '可以前往配置仓库',
    '3': '可以配置EPG',
    '4': '可以配置QA',
    '5': '可以设定成员',
    '6': '可以设定权限',
    '7': '可以设定功能',
    '8': '可以启动项目'
  }
  for (const it of code) {
    ret += performanceMap[it] + ' '
  }
  return ret
}
export function generateWhatToDo(flowbits, role) {
  const bits = generateBits(flowbits)
  switch (role) {
    case 'pm':
      if (canIDo(flowbits, 8) && bits[8] === 0) {
        return [8]
      }
      if ((canIDo(flowbits, 6) && bits[6] === 0) ||
        (canIDo(flowbits, 7) && bits[7] === 0)) {
        return [6, 7]
      }
      if (canIDo(flowbits, 5) && bits[5] === 0) {
        return [5]
      }
      return [-1]
    case 'pm_manager':
      if (bits[0] === 1 && bits[1] === 0) {
        return [5]
      }
      return [-1]
    case 'configurer':
      if (canIDo(flowbits, 2) && bits[2] === 0) {
        return [2]
      }
      return [-1]
    case 'epg_leader':
      if (canIDo(flowbits, 3) && bits[3] === 0) {
        return [3]
      }
      return [-1]
    case 'qa_manager':
      if (canIDo(flowbits, 4) && bits[4] === 0) {
        return [4]
      }
      return [-1]
  }
}

export function canIDo(flowbits, performance_bit) {
  const bits = generateBits(flowbits)

  switch (performance_bit) {
    case 0:
      return false
    case 1:
      return false
    case 2:
    case 3:
    case 4:
      return bits[0] === 1 && bits[1] === 1
    case 5:
      return flowbits === 31
    case 6:
    case 7:
      return bits[0] === 1 && bits[1] === 1 && bits[2] === 1 &&
        bits[3] === 1 && bits[4] === 1 && bits[5] === 1
    case 8:
      return flowbits === 255
    default:
      return false
  }
}
