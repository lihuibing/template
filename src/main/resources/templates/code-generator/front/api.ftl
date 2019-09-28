import request from '@/utils/request'

/**
* [获取分页数据]
* @author ${authorName}
* @date ${.now?string('yyyy/MM/dd')}
**/
export function get${classInfo.className}s(offset,pageSize){
    return request({
        url:'/${classInfo.className?uncap_first}/pageList?offset='+offset+'&pageSize='+pageSize,
        method:'get',
    })
}

/**
* [根据id获取数据]
* @author ${authorName}
* @date ${.now?string('yyyy/MM/dd')}
**/
export function get${classInfo.className}ById(id){
    return request({
        url:'/${classInfo.className?uncap_first}/load?id='+id,
        method:'get',
    })
}

/**
* [新增]
* @author ${authorName}
* @date ${.now?string('yyyy/MM/dd')}
**/
export function add${classInfo.className}(data){
    return request({
        url:`/${classInfo.className?uncap_first}/insert`,
        method:'post',
        data
    })
}

/**
* [更新]
* @author ${authorName}
* @date ${.now?string('yyyy/MM/dd')}
**/
export function update${classInfo.className}(data){
    return request({
        url:`/${classInfo.className?uncap_first}/update`,
        method:'post',
        data
    })
}

/**
* [删除]
* @author ${authorName}
* @date ${.now?string('yyyy/MM/dd')}
**/
export function delete${classInfo.className}(id){
    return request({
        url:'/${classInfo.className?uncap_first}/delete?id='+id,
        method:'delete',
    })
}

