import router from './router'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import { getToken, getLoginUser } from '@/utils/auth'
import { Route, RouteConfig } from 'vue-router'
import { PermissionModule } from '@/store/modules/permission'
import { UserModule } from './store/modules/user';

NProgress.configure({ showSpinner: false})

const whiteList = ['/login','/404']

router.beforeEach(async(to:Route, from:Route,next:any)=>{
    NProgress.start();
    //获取token
    const hasToken = getToken();
    if(hasToken){
        if (to.path === '/login') {
            next({ path: '/' })
            NProgress.done()
        } else {
            if(UserModule.roles.length == 0){
                await UserModule.GetUserInfo()
                PermissionModule.GenerateRoutes()
                next({ ...to, replace: true })
            } else {
                next()
            }
            next()
        }
    } else {
        if (whiteList.indexOf(to.path) !== -1) {
            next()
        } else {
            next(`/login`)
        }
    }
})

router.afterEach(()=>{
    NProgress.done();
})