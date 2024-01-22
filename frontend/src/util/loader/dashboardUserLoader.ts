import { getData } from "../../common/api/apiCall"
import { CSRF_PATH, LOGIN_PAGE, USER_DATA } from "../../common/url/urlMapper";
import { redirect, useNavigate } from "react-router-dom";

export const dashboardUserLoader = async () => {
   await getData({ URL : CSRF_PATH, headers : undefined })
   const data =  getData({ URL : USER_DATA, headers : undefined})
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
