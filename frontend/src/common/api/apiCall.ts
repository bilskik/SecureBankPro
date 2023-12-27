import { useEffect, useState } from "react"
import axios from "../axios/axios"

type getDataType = {
    URL : string,
    headers : object | undefined
}
type postDataType = {
    URL : string,
    data : object,
    headers : object | undefined
}
const getData = async ({ URL, headers } : getDataType) => {
    const res : any= await axios.get(URL, headers)
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

const useFetch = ({ URL, headers } : getDataType) => {
    const [data, setData] = useState<any>();
    const [isLoading, setIsLoading] = useState<boolean>(false);
    const [err, setErr] = useState<any>();

    const getData = async() => {
        setIsLoading(true)
        const res = await axios.get(URL, headers)
            .then((res) => {
                setIsLoading(false)
                res.data && setData(res.data)
            })
            .catch((err) => {
                setIsLoading(false)
                setErr(err)
            })
    }

    return { data, isLoading, err, getData };
}

const usePost = ({ URL, data, headers } : postDataType) => {
    const [resData, setResData] = useState<any>();
    const [isLoading, setIsLoading] = useState<boolean>(false);
    const [err, setErr] = useState<any>();

    const postData = async() => {
        setIsLoading(true)
        const res = await axios.post(URL, data, headers)
            .then((res) => {
                setIsLoading(false)
                res.data && setResData(res.data)
            })
            .catch((err) => {
                setIsLoading(false)
                setErr(err)
            })
    }

    return { data, isLoading, err, postData };
}

export { getData, postData, useFetch, usePost }