<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator'

@Component
export default class App extends Vue {}
</script>
<style>
body{
    font-family: 'Open Sans';
    font-size: 13px;
    color: #393939;
    background-color: #f4f4f4;
}
</style>

