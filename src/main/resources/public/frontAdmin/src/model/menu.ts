
export default interface Menu {
    id?:number,
    name: string,
    description?: string,
    url?: string,
    parentId: number,
    orderId: number,
    moduleId:number
}