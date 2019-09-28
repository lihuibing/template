import axios from 'axios'
import { getToken } from '@/utils/auth'
import { UserModule } from '@/store/modules/user';
import { showFullScreenLoading,tryHideFullScreenLoading } from '@/utils/requestLoading'
import { ErrorMessage } from "@/utils/message";

const service = axios.create({
  baseURL: process.env.VUE_APP_MOCK_API,
  headers:{ 'Content-Type' : 'application/json;charset=utf-8' },
  timeout: 15000,
})

// 请求拦截
service.interceptors.request.use(
  (config:any) => {
    if(getToken()){
      config.headers['X-Token'] = getToken()
    }
    showFullScreenLoading()
    return config
  },
  (error) => {
    Promise.reject(error)
  }
)

//返回数据拦截
service.interceptors.response.use((response) => {
  const res = response.data
  if(res.code === 50000){
    UserModule.FedLogOut().then(() => {
      location.reload()
    })
  }
  if(res.code != 20000){
    ErrorMessage(res.msg)
  }
  tryHideFullScreenLoading()
  return res
},(error) => {
  ErrorMessage(error.message)
  tryHideFullScreenLoading()
  return Promise.reject(error)
})


export default service
