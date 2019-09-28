<template>
  <div class="navbar">
    <hamburger
      id="hamburger-container"
      :is-active="sidebar.opened"
      class="hamburger-container"
      @toggleClick="toggleSideBar"
    />
    <breadcrumb
      id="breadcrumb-container"
      class="breadcrumb-container"
    />
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { AppModule } from '@/store/modules/app'
import { UserModule } from '@/store/modules/user'
import Breadcrumb from '@/components/Breadcrumb/index.vue'
import Hamburger from '@/components/Hamburger/index.vue'

@Component({
  name: 'Navbar',
  components: {
    Breadcrumb,
    Hamburger
  }
})
export default class extends Vue {
  get sidebar() {
    return AppModule.sidebar
  }

  get device() {
    return AppModule.device.toString()
  }

  private toggleSideBar() {
    AppModule.ToggleSideBar(false)
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
// .navbar {
//   height: 40px;
//   line-height: 40px;
//   border-radius: 0px !important;
//   background-color: #f5f5f5;
// }
.navbar {
  height: 45px;
  overflow: hidden;
  position: relative;
  background: #ffffff;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);
  border-top: 1px solid #e5e5e5;
  border-bottom: 1px solid #e5e5e5;

  .hamburger-container {
    line-height: 35px;
    height: 100%;
    float: left;
    padding: 0 10px;
    cursor: pointer;
    transition: background .3s;
    -webkit-tap-highlight-color:transparent;

    &:hover {
      background: rgba(0, 0, 0, .025)
    }
  }

  .breadcrumb-container {
    float: left;
    height: 45px !important;
    line-height: 45px !important;
  }
}
</style>
