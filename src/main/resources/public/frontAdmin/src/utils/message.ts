import { Message, MessageBox } from 'element-ui'
export const SuccessMessage = (message:string) => {
    Message({
        message: message,
        type: 'success',
        duration: 5 * 1000
    })
}

export const ErrorMessage = (message:string) => {
    Message({
        message: message,
        type: 'error',
        duration: 5 * 1000
    })
}

export const ConfirmMessage = () => 
    MessageBox.confirm(
        '此操作删除该数据, 是否继续?',
        '提示',
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }
    )