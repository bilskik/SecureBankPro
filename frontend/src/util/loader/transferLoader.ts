import { redirect } from "react-router-dom";
import { getData } from "../../common/api/apiCall";
import { AUTH_PATH, LOGIN_PAGE, USER_DATA } from "../../common/url/urlMapper";

export const transferLoader = () => {
    const data = getData({ URL : AUTH_PATH, headers : undefined })
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