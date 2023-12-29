import { redirect } from "react-router-dom";
import { getData } from "../../common/api/apiCall";
import { AUTH_PATH, LOGIN_PAGE } from "../../common/url/urlMapper";

export const navLoader = () => {
    const data = getData({ URL : AUTH_PATH, headers : undefined })
    console.log(data)
    const res = data.then((value : any) => {
        if(value == null) {
            return redirect(LOGIN_PAGE)
        }
        else {
            return data;
        }
    })
    return res;
 }