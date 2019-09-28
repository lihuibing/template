import ModuleApi from "@/api/${classInfo.className?uncap_first}";
import BaseService from '@/framework/BaseService';
import ModuleInfo from '@/model/${classInfo.className?uncap_first}';

export default class extends BaseService<${classInfo.className}Info> {
    getBaseApi(): import("../framework/BaseApi").default<${classInfo.className}Info> {
        return new ${classInfo.className}Api()
     }
}