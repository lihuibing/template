
import request from '@/utils/request'

/**
 * 封装了通用的api操作类，包含增删改查,其他类继承，只需要实现getBaseUrl方法即可
 */
export default abstract class BaseApi<T> {

    abstract getBaseUrl(): string
    

    public getListAll() {
        return request({
            url: this.getBaseUrl() + '/list',
            method: 'get'
        })
    }

    public addData(_data: T) {
        return request({
            url: this.getBaseUrl() + '/save',
            method: 'post',
            data: _data
        })
    }

    public deleteData(id: number) {
        return request({
            url:this.getBaseUrl() + '/delete',
            method:'DELETE',
            params:{ id }
        })
    }

    public updateData(_data: T) {
        return request({
            url:this.getBaseUrl() + '/update',
            method:'POST',
            data:_data
        })
    }
    
}