import BaseApi from "@/framework/BaseApi";
import ModuleInfo from "@/model/${classInfo.className?uncap_first}"

export default class ${classInfo.className}Api extends BaseApi<${classInfo.className}Info>  {
    getBaseUrl(): string {
        return "/${classInfo.className?uncap_first}";
    }
}