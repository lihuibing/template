import SystemApi from "@/api/system";
import BaseService from "@/framework/BaseService";
import SystemInfo from "@/model/system"

export default class extends BaseService<SystemInfo> {

    getBaseApi(): import("../framework/BaseApi").default<SystemInfo> {
        return new SystemApi();
    }
    
}