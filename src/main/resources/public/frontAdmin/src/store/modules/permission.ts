import { VuexModule, Module, Mutation, Action, getModule } from 'vuex-module-decorators'
import { RouteConfig } from 'vue-router'
import { constantRoutes } from '@/router'
import store from '@/store'

export interface IPermissionState {
  routes: RouteConfig[]
  dynamicRoutes: RouteConfig[]
}

@Module({ dynamic: true, store, name: 'permission' })
class Permission extends VuexModule implements IPermissionState {
  public routes: RouteConfig[] = []
  public dynamicRoutes: RouteConfig[] = []

  @Mutation
  private SET_ROUTES(routes: RouteConfig[]) {
      console.log(constantRoutes)
      this.routes = constantRoutes.concat(routes)
      this.dynamicRoutes = routes
  }

  @Action({ commit: 'SET_ROUTES' })
  public GenerateRoutes() {
    //   let menuService = new MenuService();
    // let menuData =  menuService.loadUserMenu(userId);
      // let routes = menuService.convertMenu(menuData);
      //   return routes
      let routes : RouteConfig[]= [];
      this.SET_ROUTES(routes)
      return routes
    
  }
}

export const PermissionModule = getModule(Permission)
