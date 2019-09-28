<template>
    <div class="app-container">
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <span>${classInfo.classComment}列表</span>
            </div>
            <div class="filter-container">
                <el-button class="filter-item" type="primary" @click="handleCreate">
                    新增
                </el-button>
            </div>

            <!-- 新增${classInfo.classComment}弹出框 -->
            <el-dialog title="新增${classInfo.classComment}" :visible.sync="dialogFormVisible" :before-close="handleClose" width="40%">
                <el-form :model="${classInfo.className?uncap_first}Form" :rules="rules" ref="${classInfo.className?uncap_first}Form">
                    <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
                        <#list classInfo.fieldList as fieldItem >
                            <#if fieldItem.fieldName != 'id'>
                                <el-form-item label="${fieldItem.fieldComment}：" :label-width="formLabelWidth" prop="name">
                                    <el-input v-model="${classInfo.className?uncap_first}Form.${fieldItem.fieldName}" auto-complete="off"></el-input>
                                </el-form-item>
                            </#if>
                        </#list>
                    </#if>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="add${classInfo.className}('${classInfo.className?uncap_first}Form')">确 定</el-button>
                </div>
            </el-dialog>

            <!-- ${classInfo.classComment}列表 -->
            <el-table v-loading="listLoading" :data="${classInfo.className?uncap_first}Data" stripe element-loading-text="加载中。。。" fit style="width: 100%">
                <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
                    <#list classInfo.fieldList as fieldItem >
                        <#if fieldItem.fieldName != 'id'>
                            <el-table-column label="${fieldItem.fieldComment}">
                                <template slot-scope="{row}">
                                    <template v-if="row.edit">
                                        <el-input v-model="row.${fieldItem.fieldName}" class="edit-input" size="small" />
                                    </template>
                                    <span v-else>{{ row.${fieldItem.fieldName} }}</span>
                                </template>
                            </el-table-column>
                        </#if>
                    </#list>
                </#if>
                <el-table-column label="操作">
                    <template slot-scope="{row}">
                        <el-button @click="row.edit=!row.edit" v-show="!row.edit">编辑</el-button>
                        <el-button v-show="row.edit" @click="update${classInfo.className}(row)">保存</el-button>
                        <el-button v-show="row.edit" @click="cacelEdit(row)">取消</el-button>
                        <el-button type="danger" @click="handleDelete(row.id)" v-show="!row.edit">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-card>
    </div>
</template>

<script lang="ts">
    import { Component, Vue } from 'vue-property-decorator'
    import ${classInfo.className}Service from "@/service/${classInfo.className}Service";
    import { ElForm } from 'element-ui/types/form';
    import { SuccessMessage,ConfirmMessage } from '@/utils/message'

    @Component
    export default class ${classInfo.className} extends Vue {
        //列表加载标志
        private listLoading = true
        //新增窗口是否显示
        private dialogFormVisible = false
        //系统服务service
        private ${classInfo.className?uncap_first}Service = new ${classInfo.className}Service()
        //系统数据
        private ${classInfo.className?uncap_first}Data = []
        //表单校验规则
        private rules = {
            name:[
                { required: true, message: '请输入系统名称', trigger: 'blur' }
            ]
        }
        //系统表单
        private ${classInfo.className?uncap_first}Form = {
            name:'',
            description:''
        }
        //表单标签长度
        private formLabelWidth = "80px"

        //组件创建时触发
        private created(){
            //获取系统数据
            this.get${classInfo.className}Data()
            this.listLoading = false
        }

        //获取系统数据
        private async get${classInfo.className}Data(){
            await this.${classInfo.className?uncap_first}Service.getListAll()
            this.${classInfo.className?uncap_first}Data = this.${classInfo.className?uncap_first}Service.listData
        }
        //更新事件
        private update${classInfo.className}(row:any){
            const ${classInfo.className?uncap_first}Info = { name:row.name,description:row.description,id:row.id }
            this.${classInfo.className?uncap_first}Service.updateData(${classInfo.className?uncap_first}Info).then((res:any)=>{
                if(res.code  == 20000){
                    SuccessMessage('更新成功')
                    this.get${classInfo.className}Data()
                }
            })
        }
        //创建新的系统
        private handleCreate(){
            this.dialogFormVisible = true;
        }
        //取消编辑
        private cacelEdit(row:any){
            row.edit = false
            this.get${classInfo.className}Data()
        }
        //删除事件
        private handleDelete(id:number){
            ConfirmMessage().then(()=>{
                this.${classInfo.className?uncap_first}Service.deleteData(id).then((res:any)=> {
                    if(res.code  == 20000){
                        SuccessMessage('删除成功')
                        this.get${classInfo.className}Data()
                    }
                })
            })
        }
        //添加系统事件
        private add${classInfo.className}(){
            (this.$refs.${classInfo.className?uncap_first}Form as ElForm).validate(async(valid: boolean) => {
                if(valid) {
                    this.${classInfo.className?uncap_first}Service.addData(this.${classInfo.className?uncap_first}Form).then((res:any)=>{
                        if(res.code  == 20000){
                            SuccessMessage('新增成功')
                        }
                        (this.$refs['${classInfo.className?uncap_first}Form'] as ElForm).resetFields()
                        this.dialogFormVisible = false
                        this.get${classInfo.className}Data()

                    })
                }
            })
        }
        //窗口关闭事件
        private handleClose(done:Function){
            (this.$refs['${classInfo.className?uncap_first}Form'] as ElForm).resetFields();
            done();
        }
    }

</script>
