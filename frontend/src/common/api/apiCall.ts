import axios from "../axios/axios"

type getDataType = {
    URL : string,
    headers : object | undefined
}
type postDataType = {
    URL : string,
    data : object,
    headers : object
}
const getData = async ({ URL, headers } : getDataType) => {
    const res = await axios.get(URL, headers)
        .then((res : any) => {
            return res.data
        })
        .catch((err : any) => {
            console.log(err)
        })
    return res;
}
const postData = async({ URL, data, headers } : postDataType) => {
    const res = await axios.post(URL, data, headers)
        .then((res : any) => {
            return res.data
        })
        .catch((err : any) => {
            return err
        })
    return res;
}

export { getData, postData }