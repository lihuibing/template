import BaseApi from "./BaseApi";

/**
 * 封装了通用的service操作类，包含增删改查，子类实现getBaseApi方法即可
 */
export default abstract class BaseService<T> {
    abstract getBaseApi(): BaseApi<T>

    public listData: any
    
    public async getListAll() {
        const { data } = await this.getBaseApi().getListAll();
        this.listData = data;
    }

    public addData(_data: T) {
        return this.getBaseApi().addData(_data);
    }

    public deleteData(id: number) {
        return this.getBaseApi().deleteData(id);
    }

    public updateData(_data: T) {
        return this.getBaseApi().updateData(_data);
    }


}
