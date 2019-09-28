<template>
  <div class="app-container">
      <el-card class="box-card">
           <div slot="header" class="clearfix">
                <span>系统列表</span>
            </div>
            <div class="filter-container">
                <el-button class="filter-item" type="primary" @click="handleCreate">
                    新增
                </el-button>
            </div>

            <!-- 新增系统弹出框 -->
            <el-dialog title="新增系统" :visible.sync="dialogFormVisible" :before-close="handleClose" width="40%">
                <el-form :model="systemForm" :rules="rules" ref="systemForm">
                    <el-form-item label="名称：" :label-width="formLabelWidth" prop="name">
                        <el-input v-model="systemForm.name" auto-complete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="描述：" :label-width="formLabelWidth" prop="description">
                        <el-input v-model="systemForm.description" auto-complete="off"></el-input>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="addSystem('systemForm')">确 定</el-button>
                </div>
            </el-dialog>

            <!-- 系统列表 -->
            <el-table v-loading="listLoading" :data="systemData" stripe element-loading-text="加载中。。。" fit highlight-current-row style="width: 100%">
                <el-table-column prop="name" label="名称" />
                <el-table-column label="描述">
                    <template slot-scope="{row}">
                    <template v-if="row.edit">
                        <el-input v-model="row.description" class="edit-input" size="small" />
                    </template>
                    <span v-else>{{ row.description }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                <template slot-scope="{row}">
                    <el-button @click="row.edit=!row.edit" v-show="!row.edit">编辑</el-button>
                    <el-button v-show="row.edit" @click="updateSystem(row)">保存</el-button>
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
import SystemService from "@/service/SystemService";
import { ElForm } from 'element-ui/types/form';
import { SuccessMessage,ConfirmMessage } from '@/utils/message'

@Component
export default class System extends Vue {
    //列表加载标志
    private listLoading = true
    //新增窗口是否显示
    private dialogFormVisible = false
    //系统服务service
    private systemService = new SystemService()
    //系统数据
    private systemData = []
    //表单校验规则
    private rules = {
        name:[
            { required: true, message: '请输入系统名称', trigger: 'blur' }
        ]
    }
    //系统表单
    private systemForm = {
        name:'',
        description:''
    }
    //表单标签长度
    private formLabelWidth = "80px"

    //组件创建时触发
    private created(){
        //获取系统数据
        this.getSystemData()
        this.listLoading = false
    }

    //获取系统数据
    private async getSystemData(){
        await this.systemService.getListAll()
        this.systemData = this.systemService.listData
    }
    //更新事件
    private updateSystem(row:any){
      const systemInfo = { name:row.name,description:row.description,id:row.id }
      this.systemService.updateData(systemInfo).then((res:any)=>{
        if(res.code  == 20000){
          SuccessMessage('更新成功')
          this.getSystemData()
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
      this.getSystemData()
    }
    //删除事件
    private handleDelete(id:number){
      ConfirmMessage().then(()=>{
          this.systemService.deleteData(id).then((res:any)=> {
              if(res.code  == 20000){
                SuccessMessage('删除成功')
                this.getSystemData()
              }
          })
      })
    }
    //添加系统事件
    private addSystem(){
      (this.$refs.systemForm as ElForm).validate(async(valid: boolean) => {
        if(valid) {
          this.systemService.addData(this.systemForm).then((res:any)=>{
            if(res.code  == 20000){
              SuccessMessage('新增成功')
            }
            (this.$refs['systemForm'] as ElForm).resetFields()
            this.dialogFormVisible = false
            this.getSystemData()

          })
        }
      })
    }
    //窗口关闭事件
    private handleClose(done:Function){
      (this.$refs['systemForm'] as ElForm).resetFields();
      done();
    }
}

</script>
