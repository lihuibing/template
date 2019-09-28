export default interface ${classInfo.className}Info {
<#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
    <#list classInfo.fieldList as fieldItem >
        <#if fieldItem.fieldClass != 'String'>
            ${fieldItem.fieldName}?:number
        <#else >
            ${fieldItem.fieldName}?:${fieldItem.fieldClass?uncap_first}
        </#if>
    </#list>
</#if>
}