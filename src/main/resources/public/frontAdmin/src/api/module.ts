import BaseApi from "@/framework/BaseApi";
import ModuleInfo from "@/model/module"

export default class ModuleApi extends BaseApi<ModuleInfo>  {
    getBaseUrl(): string {
        return "/module";
    }
}