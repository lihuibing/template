
import ModuleApi from "@/api/module";
import BaseService from '@/framework/BaseService';
import ModuleInfo from '@/model/module';

export default class extends BaseService<ModuleInfo> {
    getBaseApi(): import("../framework/BaseApi").default<ModuleInfo> {
        return new ModuleApi()
    }
}