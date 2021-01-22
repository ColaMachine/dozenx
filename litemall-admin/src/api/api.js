import request from '@/utils/request'
// 获取api列表接口
export function fetchList(query) {
  return request({
    url: '/interfaceInfo/list',
    method: 'get',
    params: { 'params': query }
  })
}
// 删除api
export function deleteApi(id) {
  return request({
    url: '/interfaceInfo/' + id,
    method: 'delete'

  })
}

// 获取api信息
export function getInfo(id) {
  return request({
    url: '/interfaceInfo/' + id,
    method: 'get'

  })
}

// 添加api接口
export function addInterfaceInfo(data) {
  return request({
    url: '/interfaceInfo/add',
    method: 'post',
    data: data
  })
}

// 更新api接口
export function updateInterfaceInfo(data) {
  return request({
    url: '/interfaceInfo/update',
    method: 'put',
    data: data
  })
}

// 更新api接口
export function updateInterfaceInfoModule(data) {
  return request({
    url: '/interfaceInfo/module/update',
    method: 'put',
    data: data
  })
}
// 获取api参数
export function getApiParams(id) {
  return request({
    // url: '/params/getParams?params='+encodeURIComponent(JSON.stringify({"interfaceId":id})),
    url: '/interfaceParam/getParams/' + id,
    method: 'get'
  })
}

// 添加api参数
export function addApiParam(data) {
  return request({
    url: '/interfaceParam/add',
    method: 'post',
    data: data
  })
}

// 更新api参数
export function updateApiParam(data) {
  return request({
    url: '/interfaceParam/update',
    method: 'put',
    data: data
  })
}

// 删除api参数
export function deleteApiParam(id) {
  return request({
    url: '/interfaceParam/delete/' + id,
    method: 'delete'

  })
}

// 删除api参数
export function testInterfaceApi(data) {
  return request({
    url: '/exampleTest/' + id,
    method: 'get'

  })
}

export function addExampleTest(data) {
  return request({
    url: '/exampleTest',
    data: data,
    method: 'post'

  })
}

export function getInterfaceTests(id) {
  return request({
    url: '/exampleTest/list/' + id,

    method: 'get'

  })
}

export function deleteExampleTest(id) {
  return request({
    url: '/exampleTest/' + id,

    method: 'delete'

  })
}

export function interfaceInfoTest(api) {
  return request({
    url: '/interfaceInfo/test',
    data: api,
    method: 'put'

  })
}

export function interfaceInfoModuleTree(api) {
  return request({
    url: '/module/tree',
    method: 'get'

  })
}

export function interfaceInfoModuleAdd(data) {
  return request({
    url: '/module/add',
    data: data,
    method: 'post'
  })
}

export function interfaceInfoModuleDelete(id) {
  return request({
    url: '/module/delete/' + id,

    method: 'delete'
  })
}

export function interfaceInfoModuleUpdate(data) {
  return request({
    url: '/module/update',
    data: data,
    method: 'put'
  })
}

export function interfaceInfoModuleTreeLeaf(api) {
  return request({
    url: '/module/tree/leaf',
    method: 'get'

  })
}

export function interfaceInfoModuleGetLeaf(pid) {
  return request({
    url: '/module/tree/' + pid,
    method: 'get'

  })
}

