<template>
  <div class="wscn-http404-container">
    <div class="wscn-http404">{{ message }}</div>
  </div>
</template>

<script lang='ts'>
import { Component, Vue } from 'vue-property-decorator'

@Component
export default class NotFound extends Vue {
  get message() {
    return '404 此页面不存在'
  }
}
</script>

<style lang="scss" scoped>
</style>
