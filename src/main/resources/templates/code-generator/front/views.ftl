<template>
    <div class="app-container">
        <el-table :data="pageList" stripe border style="width: 100%">
            <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
                <#list classInfo.fieldList as fieldItem >
                    <el-table-column prop="${fieldItem.fieldName}" label="${fieldItem.fieldComment}"></el-table-column>
                </#list>
            </#if>
        </el-table>
    </div>
</template>
<script>
    import { get${classInfo.className}s,add${classInfo.className},delete${classInfo.className},update${classInfo.className},get${classInfo.className}ById } from '@/api/${classInfo.className?uncap_first}'

</script>