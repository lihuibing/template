import BaseApi from "@/framework/BaseApi"
import SystemInfo from "@/model/system"

export default class SystemApi extends BaseApi<SystemInfo>  {
    getBaseUrl(): string {
        return "/system"
    }
    
}