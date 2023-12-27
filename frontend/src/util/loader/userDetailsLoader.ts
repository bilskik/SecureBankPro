import { getData } from "../../common/api/apiCall";
import { USER_DETAILS_PATH } from "../../common/url/urlMapper";

export const userDetailsLoader = () => {
    return getData({ URL : USER_DETAILS_PATH, headers : undefined});
}