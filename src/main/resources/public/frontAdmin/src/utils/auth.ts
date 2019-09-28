const tokenKey = 'ocr_login_token'
export const getToken = () => sessionStorage.getItem(tokenKey)

export const setToken = (token: string) => sessionStorage.setItem(tokenKey, token)

export const removeToken = () => sessionStorage.removeItem(tokenKey)

const userKey = 'username'

export const setUser = (username: string) => sessionStorage.setItem(userKey, username)

export const getUser = () => sessionStorage.getItem(userKey)

export const removeUser = () => sessionStorage.removeItem(userKey)

const loginUser = 'loginUser'

export const setLoginUser = (user: any) => sessionStorage.setItem(loginUser, user)

export const getLoginUser = (): any => {
    const loginUserStr: any = sessionStorage.getItem(loginUser)
    const user = JSON.parse(loginUserStr);
    return user;
}

export const removeLoginUser = () => sessionStorage.removeItem(loginUser)

