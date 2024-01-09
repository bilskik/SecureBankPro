import React, { useEffect, useState } from 'react'
import axios from '../common/axios/axios';
import { Alert, Button, Container, Form, Row } from 'react-bootstrap';
import { AUTH_PATH, DASHBOARD_PAGE, LOGIN_BEGIN_PATH, LOGIN_FINISH_PATH, REGISTER_PATH } from '../common/url/urlMapper';
import PasswordGroup from '../component/password/PasswordGroup';
import { Person } from 'react-bootstrap-icons';
import { useNavigate } from 'react-router-dom';
import { ErrorType } from '../util/type/types.shared';
import ResetPassword from '../component/password/ResetPassword';

const Login = () => {
    const [login, setLogin] = useState<string>("");
    const [range, setRange] = useState<string>("");
    const [csrf, setCsrf] = useState<string>("");
    const [err, setErr] = useState<ErrorType>({ isError : false, message : ''})
    const [isResetPassword, setIsResetPassword] = useState<boolean>(false);
    const nav = useNavigate();
    
    useEffect(() => {
        axios.get("/auth/csrf")
            .then((res : any) => {
                if(res.data && res.data.token) {
                    setCsrf(res.data.token)
                } 
            })
            .catch((err : any) => {
                console.log(err)
            })
    },[])

    const handleNext = () => {
        setErr(prev => ({
                ...prev,
                isError : false
        }))
        const prepareData = {
            username : login
        }
        axios.post(AUTH_PATH + LOGIN_BEGIN_PATH, prepareData, { headers : getHeaders() })
            .then((res : any) => {
                if(res.data?.range) {
                    setRange(res.data.range);
                }
            }).catch((err : any) => {
                setErr({
                    isError : true,
                    message : err.response?.data?.username
                })
            })
    }

    const onHandleSubmit = (password : string) => {
        const prepareData= {
            username : login,
            password 
        }

        setErr(prev => ({
            ...prev,
            isError : false
        }))

        axios.post(AUTH_PATH + LOGIN_FINISH_PATH, prepareData, { headers : getHeaders() })
            .then((res : any) => {
                setErr(prev => ({
                    ...prev,
                    isError : false
                }))
                setTimeout(() => {
                    nav(DASHBOARD_PAGE)
                },100)
            })
            .catch((err : any) => {
                console.log(err)
                setErr({
                    isError : true,
                    message : "Credentials invalid!"
                })
            })
    }

    const getHeaders = () => {
        return {
            'X-XSRF-TOKEN' : csrf
        }
    }

    return (
        <Container className='container-login' fluid>
            <Form className='mt-3 d-flex flex-column justify-content-center'>
                <Row className='m-3'>
                    <Person color='green' size={30}/>
                </Row>
                <h2 style={{textAlign : "center"}}>Login</h2>
                {
                    isResetPassword ? 
                    <ResetPassword 
                        handlePasswordResetUnShow={() => { setIsResetPassword(false); setRange(''); } }
                        headers={getHeaders()} 
                        login={login}
                    />
                    :
                    <>
                        <Form.Group className='m-3 mt-4'>
                            <Form.Label className="ms-1">Username</Form.Label>
                            <Form.Control 
                                type='text' 
                                placeholder='username'
                                value={login}
                                onChange={(e) => setLogin(e.target.value)}
                            />
                        </Form.Group>
                        {
                            range ? 
                            <PasswordGroup 
                                ranges={range} 
                                onHandleSubmit={onHandleSubmit} 
                                err={err} 
                                handleResetPasswordShow={() => setIsResetPassword(true)}
                            />
                            : 
                            <Row className='mb-5 ms-3 me-3 mt-4'>
                                <Button variant='success' onClick={handleNext}>
                                    Next
                                </Button>
                            </Row>
                        }
                    </>
                }
            </Form>
        </Container>
    )
}

export default Login