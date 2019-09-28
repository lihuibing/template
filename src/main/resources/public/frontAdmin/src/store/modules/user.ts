import { VuexModule, Module, MutationAction, Mutation, Action, getModule } from 'vuex-module-decorators'
import { logout, getUserInfo } from '@/api/login'
import { getToken, setToken, removeToken, removeUser } from '@/utils/auth'
import store from '@/store'

export interface IUserState {
  token: string;
  name: string;
  roles: string[];
}

@Module({ dynamic: true, store, name: 'user' })
class User extends VuexModule implements IUserState {
  public token = '';
  public name = '';
  public roles = [];

  @Action({ commit: 'SET_TOKEN' })
  public async Login(userInfo: { username: string, password: string}) {
    const username = userInfo.username.trim()
    // const { data } = await login(username, userInfo.password)
    setToken(username)
    return username
  }

  @Action({ commit: 'SET_TOKEN' })
  public async FedLogOut() {
    removeToken()
    removeUser()
    return ''
  }

  @MutationAction({ mutate: ['roles', 'name'] })
  public async GetUserInfo() {
    return {
      roles: ['admin'],
      name: 'admin'
    }
  }

  @MutationAction({ mutate: ['token', 'roles'] })
  public async LogOut() {
    if (getToken() === undefined) {
      throw Error('LogOut: token is undefined!')
    }
    await logout()
    removeToken()
    return {
      token: '',
      roles: []
    }
  }

  @Mutation
  private SET_TOKEN(token: string) {
    this.token = token
  }
}

export const UserModule = getModule(User)
