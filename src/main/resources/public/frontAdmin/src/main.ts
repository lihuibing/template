import Vue from "vue";

import "normalize.css";
import ElementUI from "element-ui";
import SvgIcon from 'vue-svgicon'
import "@/styles/index.scss"


import App from "@/App.vue";
import router from "@/router";
import store from '@/store'
import '@/icons/components'
import "@/permission";
import '@/pwa/register-service-worker'


Vue.use(ElementUI,{ size: 'mini', zIndex: 3000 });
Vue.use(SvgIcon, {
  tagName: 'svg-icon',
  defaultWidth: '1em',
  defaultHeight: '1em'
})

Vue.config.productionTip = false;


new Vue({
    router,
    store,
  render: h => h(App)
}).$mount("#app");
