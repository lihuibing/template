<template>
<div>
    <div class="custom-header">
        <img src="../../assets/logo.jpg" height="35" style="margin-top:10px;margin-left:20px;">
        <div style="float:right;margin-top: 20px;margin-right:15px;">
            用户：{{ name }} &nbsp;
            <a style="color: #337ab7;text-decoration: none;cursor: pointer;" @click="loginOut">退出登录</a>
        </div>
    </div>
    <div
        :class="classObj"
        class="app-wrapper"
    >
        <div
        v-if="classObj.mobile && sidebar.opened"
        class="drawer-bg"
        @click="handleClickOutside"
        />
        <sidebar class="sidebar-container" />
        <div
        :class="{hasTagsView: showTagsView}"
        class="main-container"
        >
        <div :class="{'fixed-header': fixedHeader}">
            <navbar />
            <tags-view v-if="showTagsView" />
        </div>
        <app-main />
        </div>
    </div>
    </div>
</template>

<script lang="ts">
import { Component } from 'vue-property-decorator'
import { mixins } from 'vue-class-component'
import { DeviceType, AppModule } from '@/store/modules/app'
import { SettingsModule } from '@/store/modules/settings'
import { getUser, removeToken, removeUser } from "@/utils/auth";
import { AppMain, Navbar, Sidebar, TagsView } from './components'
import ResizeMixin from './mixin/resize'

@Component({
  name: 'Layout',
  components: {
    AppMain,
    Navbar,
    Sidebar,
    TagsView
  }
})
export default class extends mixins(ResizeMixin) {
  get classObj() {
    return {
      hideSidebar: !this.sidebar.opened,
      openSidebar: this.sidebar.opened,
      withoutAnimation: this.sidebar.withoutAnimation,
      mobile: this.device === DeviceType.Mobile
    }
  }

  get name(){
    return getUser();
  }

  get showSettings() {
    return SettingsModule.showSettings
  }

  get showTagsView() {
    return false
  }

  get fixedHeader() {
    return SettingsModule.fixedHeader
  }

  private handleClickOutside() {
    AppModule.CloseSideBar(false)
  }
  
  /**
   * 退出登录
   */
  private loginOut(){
      removeToken()
      removeUser()
      this.$router.push({ path: '/login' })
  }

}
</script>



<style rel="stylesheet/scss" lang="scss" scoped>
  @import "src/styles/mixin.scss";
  @import "src/styles/variables.scss";
  .app-wrapper {
  @include clearfix;
  position: relative;
  height: 100%;
  width: 100%;
}

.drawer-bg {
  background: #000;
  opacity: 0.3;
  width: 100%;
  top: 0;
  height: 100%;
  position: absolute;
  z-index: 999;
}

.main-container {
  min-height: 100%;
  transition: margin-left .28s;
  margin-left: $sideBarWidth;
  position: relative;
}

.sidebar-container {
  transition: width 0.28s;
  width: $sideBarWidth !important;
  height: 100%;
  position: absolute;
  font-size: 0px;
  top: 0;
  bottom: 0;
  left: 0;
  z-index: 1001;
  overflow: hidden;
}

.fixed-header {
  position: fixed;
  top: 0;
  right: 0;
  z-index: 9;
  width: calc(100% - #{$sideBarWidth});
  transition: width 0.28s;
}

.hideSidebar {
  .main-container {
    margin-left: 54px;
  }

  .sidebar-container {
    width: 54px !important;
  }

  .fixed-header {
    width: calc(100% - 54px)
  }
}

/* for mobile response 适配移动端 */
.mobile {
  .main-container {
    margin-left: 0px;
  }

  .sidebar-container {
    transition: transform .28s;
    width: $sideBarWidth !important;
  }

  &.openSidebar {
    position: fixed;
    top: 0;
  }

  &.hideSidebar {
    .sidebar-container {
      pointer-events: none;
      transition-duration: 0.3s;
      transform: translate3d(-$sideBarWidth, 0, 0);
    }
  }

  .fixed-header {
    width: 100%;
  }
}

.withoutAnimation {
  .main-container,
  .sidebar-container {
    transition: none;
  }
}
.custom-header{
    height: 55px;
    background-color: #f7f5f6;
}
img {
    vertical-align: middle;
}
</style>
