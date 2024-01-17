import { redirect } from "react-router-dom";
import { getData } from "../../common/api/apiCall";
import { LOGIN_PAGE, USER_DATA, USER_DETAILS_PATH } from "../../common/url/urlMapper";

export const userDetailsLoader = () => {
    const data = getData({ URL : USER_DETAILS_PATH, headers : undefined })
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