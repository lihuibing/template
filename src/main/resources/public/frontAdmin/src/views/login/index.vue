<template>
  <div id="content">
      <div class="Log_cont">
          <img src="../../assets/logoyuanse.png" class="logo" />
      </div>
      <div id="loginForm">
          <div class="loginTypeDiv">
              <span class="loginTypeNoSelected">账号登录</span>
          </div>
          <ul>
            <li style="margin-bottom:10px" class="inputField">
                <span class="tiny-textbox-container" style="width: 400px; height: 48px;"> 
                    <input type="text" spellcheck="false" v-model="loginForm.username" class="tiny-input-text" placeholder="账号名/邮箱">
                </span>
            </li>
            <li style="margin-bottom:10px" class="inputField">
                <span class="tiny-textbox-container" style="width: 400px; height: 48px;"> 
                    <input type="password" spellcheck="false"  v-model="loginForm.password" class="tiny-input-text" placeholder="密码">
                </span>
            </li>
          </ul>
          <div v-show="hasError" style="margin-left:30px;">
              <span style="color:red;margin-left:0px;">{{ errorMsg }}</span>
          </div>
          <div id="buttonArea">
            <div id="loginBtn" class="loginBtn" @click="handleLogin" tabindex="0">
                <span id="btn_submit">登录</span>
            </div>
          </div>
      </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Watch } from 'vue-property-decorator'
import { Route } from 'vue-router'
import { Form as ElForm } from 'element-ui'
import { UserModule } from '@/store/modules/user'
import {  constantRoutes } from "@/router";
import UserService from "@/service/UserService";
import { userLogin, logout, getUserInfo } from '@/api/login'
import { setToken,setUser,setLoginUser } from '@/utils/auth'
import { Message, MessageBox } from 'element-ui'

@Component
export default class Login extends Vue {
  private userService = new UserService()
  //登录表单
  private loginForm = {
    username: 'admin',
    password: 'admin'
  }
  //校验是否存在错误
  private hasError = false
  //错误信息
  private errorMsg = ''
  //登录后跳转页面
  private redirect: string | undefined = undefined;

  /**
   * 登录方法
   */
  private async handleLogin() {
      if(!this.loginForm.username){
          this.hasError = true;
          this.errorMsg = '请输入用户名';
      } else if(!this.loginForm.password){
          this.hasError = true;
          this.errorMsg = '请输入密码';
      } else {
          //TODO 用户登录逻辑
          setToken('admin')
          setUser('admin')
          this.$router.push({ path: this.redirect || '/' })
        // await this.userService.login(this.loginForm)
        // let user = this.userService.loginData
        // let message  = this.userService.getLoginMessage(user);
        // if(message == ''){
        //     //保存token到session中
        //     setToken(user.token)
        //     //保存loginId到session中
        //     setUser(user.loginId)
        //     //保存user到sesseion中
        //     let userString = JSON.stringify(user);
        //     setLoginUser(userString)
        //     this.$router.push({ path: this.redirect || '/' })
        // } else {
        //     Message({
        //         message: message,
        //         type: 'error',
        //         duration: 5 * 1000
        //     })
        // }
    }
  }
}
</script>
<style scoped>
#content {
    background-color: #F4F4F4;
    position: relative;
    height: 100%;
    min-height: 670px;
    min-width: 1200px;
    position: relative;
    margin: 0 auto;
    padding: 0 20px;
}
#loginForm {
    width: 480px;
    display: inline-block;
    margin-top: 30px;
    background-color: #fff;
    color: #C1C1C1;
    font-size: 14px;
    margin-left:calc(55%-300px);
    border-color: rgba(228,228,228,1);
    border-radius: 0;
    height:300px;
    box-shadow: 0 8px 16px 0 rgba(0,0,0,.1);
}
.loginTypeDiv {
    color: #666;
    font-size: 14px;
    font-style: normal;
    margin: 30px 30px 0;
    text-align: center;
}

.loginTypeNoSelected {
    border-color: #ccc;
    border-width: 1px;
    width: 100%;
    height: 28px;
    line-height: 18px;
    font-size: 18px;
    padding-bottom: 10px;
    font-family: PingFangSC-Medium;
    text-align: left;
    color: #252B3A;
}
.loginTypeNoSelected, .loginTypeSelected {
    border-style: solid;
    border-top: 0;
    border-left: 0;
    border-right: 0;
    display: inline-block;
    box-sizing: border-box;
}

.inputField {
    margin-bottom: 15px!important;
    font-size: 14px!important;
}

li {
    display: list-item;
    text-align: -webkit-match-parent;
}

ol, ul {
    list-style: none;
}

li {
    line-height: 20px;
    margin-left: -10px;
}

.tiny-textbox-container {
    position: relative;
    display: inline-block;
    height: 26px;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
}

#loginForm .inputField input {
    line-height: 20px;
    border-color: #A0A2A8;
    border-radius: 2px;
}

.tiny-input-text {
    border: 1px solid #ddd;
    color: #333;
    font-size: 14px;
}

.tiny-input-text {
    width: 100%;
    height: 100%;
    border-radius: 4px;
    -webkit-box-shadow: 0 1px 1px 0 #f6f6f6 inset;
    box-shadow: 0 1px 1px 0 #f6f6f6 inset;
    border: 1px solid #c3c3c3;
    color: #666;
    background-color: #fff;
    padding: 2px 8px;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
    outline: none;
    vertical-align: middle;
}

#buttonArea {
    margin: 20px 30px;
    width: 400px;
}

.loginBtn {
    background-color: #F66F6A;
    border: 1px solid #F66F6A;
    border-radius: 3px;
    color: #fff;
    font-size: 18px!important;
    cursor: pointer;
    width: 100%;
}
.loginBtn span {
    text-align: center;
    font-size: large;
    height: 48px;
    margin: 0 auto;
    display: block;
    line-height: 48px;
}

.Log_cont {
    position: relative;
    width: 30%;
    padding-bottom: 0px;
    margin: 0 auto;
    z-index: 2;
}
.Log_title {
    position: relative;
    width: 8%;
    padding-bottom: 0px;
    margin: 0 auto;
    z-index: 2;
}

.Log_cont .logo {
    padding-top: 109px;
    margin-bottom: 0px;
}

.Log_cont img {
    display: block;
    margin: 0 auto;
}

</style>