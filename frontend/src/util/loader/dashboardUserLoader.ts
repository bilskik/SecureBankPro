import { useState } from "react"
import axios from "../../common/axios/axios"
import { getData } from "../../common/api/apiCall"
import { USER_DATA } from "../../common/url/urlMapper";

export const dashboardUserLoader = () => {
    return getData({ URL : USER_DATA, headers : undefined});
}