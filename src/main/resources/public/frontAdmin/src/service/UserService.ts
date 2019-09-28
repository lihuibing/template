import { userLogin, logout, getUserInfo } from '@/api/login'

export default class UserService{

    public loginData:any;

    /**
     * 登录
     * @param userInfo 用户登录信息
     */
    public async login(userInfo: { username: string, password: string}){
        const username = userInfo.username.trim()
        const { data } = await userLogin(username, userInfo.password)
        this.loginData = data;
    }

    /**
     * 获取登录信息
     * @param user 用户信息
     */
    public getLoginMessage(user:any):string{
        let message:string = '';
        if(user.errorType == '1'){
            message = '账户被锁定, 连续录入错误密码次数超限'
        } else if (user.errorType == '2') {
            message = '密码过期'
        } else if (user.errorType == '3') {
            message = '用户名无效'
        } else if (user.errorType == '4'){
            message = '密码错误，剩余次数' + user.leftTimes;
        }
        return message;
    }

}