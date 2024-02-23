import { useContext, useEffect, useState } from 'react'
import axios from '../../config/axios';
import { AuthContext } from '../../util/context/AuthProvider';
import { CSRF_PATH, LOGIN_PAGE, USER_DATA } from '../../config/urlMapper';
import { useNavigate } from 'react-router-dom';
import DashboardLayout from './DashboardLayout';
import { UserDataType } from '../../util/type/types.shared';

const Dashboard = () => {
    const { authData, updateCSRF, updateAuthentication } = useContext(AuthContext);
    const [user, setUser] = useState<UserDataType>({ accountNo : "", balance : -1});
    const nav = useNavigate();

    useEffect(() => {
        const makeApiCalls = async () => {
            await axios.get<string>(CSRF_PATH)
                .then((res : any) => {
                    const token = res.data.token
                    if(token) {
                        updateCSRF(token)
                    }
                })
                .catch(() => {
                    nav(LOGIN_PAGE)
                })
            axios.get<UserDataType>(USER_DATA)
                .then((res) => {
                        updateAuthentication()
                        setUser(res.data);
                })
                .catch(() => {
                    nav(LOGIN_PAGE);
                })
            }
        makeApiCalls();
    },[])

  return (
        <DashboardLayout user={user}/>
    )
}

export default Dashboard