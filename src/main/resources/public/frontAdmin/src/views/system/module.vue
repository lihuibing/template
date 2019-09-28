<template>
    <div class="app-container">
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <span>模块列表</span>
            </div>
            <div class="filter-container">
                <el-button class="filter-item" type="primary" @click="handleCreate">
                    新增
                </el-button>
            </div>
            <!-- 新增模块弹出框 -->
            <el-dialog title="新增模块" :visible.sync="dialogFormVisible" :before-close="handleClose">
                <el-form :model="moduleForm" :rules="rules" ref="moduleForm">
                    <el-form-item label="名称：" :label-width="formLabelWidth" prop="name">
                        <el-input v-model="moduleForm.name" auto-complete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="描述：" :label-width="formLabelWidth" prop="description">
                        <el-input v-model="moduleForm.description" auto-complete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="所属系统：" :label-width="formLabelWidth" prop="systemId">
                        <el-select v-model="moduleForm.systemId" placeholder="请选择" style="width: 100%;">
                            <el-option
                                    v-for="item in systemData"
                                    :key = "item.id"
                                    :label="item.name"
                                    :value="item.id"
                            ></el-option>
                        </el-select>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="addModule('moduleForm')">确 定</el-button>
                </div>
            </el-dialog>

            <!-- 模块列表 -->
            <el-table v-loading="listLoading" stripe :data="moduleData" element-loading-text="加载中。。。" fit highlight-current-row style="width: 100%">
                <el-table-column label="名称">
                    <template slot-scope="{row}">
                        <template v-if="row.edit">
                            <el-input v-model="row.name" class="edit-input" size="small" />
                        </template>
                        <span v-else>{{ row.name }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="描述">
                    <template slot-scope="{row}">
                        <template v-if="row.edit">
                            <el-input v-model="row.description" class="edit-input" size="small" />
                        </template>
                        <span v-else>{{ row.description }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="所属系统">
                    <template slot-scope="{row}">
                        <template v-if="row.edit">
                            <el-select v-model="row.systemId" size="small" placeholder="请选择" filterable style="width: 100%;">
                                <el-option v-for="item in systemData" :key = "item.id" :label="item.name" :value="item.id"></el-option>
                            </el-select>
                        </template>
                        <span v-else>{{ row.system.name }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="{row}">
                        <el-button @click="row.edit=!row.edit" v-show="!row.edit">编辑</el-button>
                        <el-button v-show="row.edit" @click="updateModule(row)">保存</el-button>
                        <el-button v-show="row.edit" @click="cacelEdit(row)">取消</el-button>
                        <el-button type="danger" @click="handleDelete(row.id)" v-show="!row.edit">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-card>
    </div>

</template>

<script lang="ts">
import { Component, Vue, Watch } from 'vue-property-decorator'
import SystemService from "@/service/SystemService"
import ModuleService from "@/service/ModuleService"
import { ElForm } from 'element-ui/types/form'
import { SuccessMessage,ConfirmMessage } from '@/utils/message'

@Component
export default class Module extends Vue {
    
    //列表加载标志
    private listLoading = true
    //新增窗口是否显示
    private dialogFormVisible = false
    //表单标签长度
    private formLabelWidth = "120px"
    //系统服务service
    private systemService = new SystemService()
    //模块服务service
    private moduleService = new ModuleService()
    //系统数据
    private systemData = []
    //模块数据
    private moduleData = []
    //模块表单
    private moduleForm = {
        name:'',
        description:'',
        systemId:''
    }
    //表单校验规则
    private rules = {
        name:[
            { required: true, message: '请输入模块名称', trigger: 'blur' }
        ],
        systemId:[
            { required: true, message: '请选择所属系统', trigger: 'change' }
        ]
    }

    //获取系统数据
    private async getSystemData(){
        await this.systemService.getListAll()
        this.systemData = this.systemService.listData
    }

    //获取模块数据
    private async getModuleData(){
        await this.moduleService.getListAll()
        this.moduleData = this.moduleService.listData
    }

    //组件创建时触发
    private created(){
        //获取系统数据
        this.getSystemData()
        //获取模块数据
        this.getModuleData()
        this.listLoading = false
    }

    //取消编辑
    private cacelEdit(){
        this.getModuleData()
    }
    //新建模块按钮事件
    private handleCreate(){
        this.dialogFormVisible = true;
    }

    //更新模块
    private updateModule(row:any){
        const moduleInfo = { name:row.name,description:row.description,systemId:row.systemId,id:row.id }
        this.moduleService.updateData(moduleInfo).then((res:any)=>{
            if(res.code  == 20000){
                SuccessMessage('更新成功')
                this.getModuleData()
            }
        })
    }

    //窗口关闭事件
    private handleClose(done:Function){
      (this.$refs['moduleForm'] as ElForm).resetFields();
      done();
    }

    //删除事件
    private handleDelete(id:number){
      ConfirmMessage().then(()=>{
          this.moduleService.deleteData(id).then((res:any)=> {
              if(res.code  == 20000){
                SuccessMessage('删除成功')
                this.getModuleData()
              }
          })
      })
    }

    //添加系统事件
    private addModule(){
      (this.$refs.moduleForm as ElForm).validate(async(valid: boolean) => {
        if(valid) {
          this.moduleService.addData(this.moduleForm).then((res:any)=>{
            if(res.code  == 20000){
              SuccessMessage('新增成功')
            }
            (this.$refs['moduleForm'] as ElForm).resetFields()
            this.dialogFormVisible = false
            this.getModuleData()
          })
        }
      })
    }

}

</script>
