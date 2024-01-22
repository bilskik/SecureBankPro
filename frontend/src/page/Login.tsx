import { useEffect, useState } from 'react'
import axios from '../common/axios/axios';
import { Button, Container, Form, Row, Spinner } from 'react-bootstrap';
import { AUTH_PATH, CSRF_PATH, DASHBOARD_PAGE, LOGIN_BEGIN_PATH, LOGIN_FINISH_PATH, REGISTER_PATH } from '../common/url/urlMapper';
import PasswordGroup from '../component/password/PasswordGroup';
import { Person } from 'react-bootstrap-icons';
import { useNavigate } from 'react-router-dom';
import { ErrorType } from '../util/type/types.shared';
import ResetPassword from '../component/password/ResetPassword';
import { loginBeginValidator, loginFinishValidator } from '../util/validator/validators';

const Login = () => {
    const [login, setLogin] = useState<string>("");
    const [range, setRange] = useState<string>("");
    const [csrf, setCsrf] = useState<string>("");
    const [err, setErr] = useState<ErrorType>({ isError : false, message : ''})
    const [isLoading, setIsLoading] = useState<boolean>(false);
    const [isResetPassword, setIsResetPassword] = useState<boolean>(false);
    const nav = useNavigate();
    
    useEffect(() => {
        axios.get(CSRF_PATH)
            .then((res : any) => {
                if(res.data && res.data.token) {
                    setCsrf(res.data.token)
                } 
            })
            .catch((err : any) => {
                setErr({
                    isError : true,
                    message : "Unexpected error. Try to restart page!"
                })
            })
    },[])

    const handleNext = () => {
        setErr(prev => ({
                ...prev,
                isError : false
        }))
        const prepareData = {
            username : login.trim()
        }
        if(loginBeginValidator(login)) {
            setIsLoading(true);
            axios.post(AUTH_PATH + LOGIN_BEGIN_PATH, prepareData, { headers : getHeaders() })
                .then((res : any) => {
                    setIsLoading(false);
                    if(res.data?.range) {
                        setRange(res.data.range);
                    }
                }).catch((err : any) => {
                    setIsLoading(false);
                    setErr({
                        isError : true,
                        message : err.response?.data.username
                    })
                })
        } else {
            setErr({
                isError : true,
                message : "Username cannot be blank!"
            })
        }
        
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
        if(loginFinishValidator(prepareData)) {
            setIsLoading(true);
            axios.post(AUTH_PATH + LOGIN_FINISH_PATH, prepareData, { headers : getHeaders() })
            .then((res : any) => {
                setIsLoading(false);
                setErr(prev => ({
                    ...prev,
                    isError : false
                }))
                setTimeout(() => {
                    nav(DASHBOARD_PAGE)
                },100)
            })
            .catch((err : any) => {
                setIsLoading(false);
                setErr({
                    isError : true,
                    message : "Credentials invalid!"
                })
            })
        } else {
            setErr({
                isError : true,
                message : "Username or password cannot be blank!"
            })
        }
       
    }

    const getHeaders = () => {
        return {
            'X-XSRF-TOKEN' : csrf
        }
    }

    return (
        <Container className='container-login' fluid>
            { isLoading ? <Spinner className='spinner' /> : null }
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
                            { err.isError && !range ?  
                                <Row className='me-3 mt-2'>
                                    <span style={{ color : "red" }}>{ err.message }</span>
                                </Row> : null
                            }
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