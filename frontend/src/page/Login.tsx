import React, { useState } from 'react'
import axios from '../common/axios/axios';
import { Button, Container, Form, Row } from 'react-bootstrap';
import { AUTH_PATH, DASHBOARD_PAGE, LOGIN_BEGIN_PATH, LOGIN_FINISH_PATH, REGISTER_PATH } from '../common/url/urlMapper';
import PasswordGroup from '../component/password/PasswordGroup';
import { Person } from 'react-bootstrap-icons';
import { useNavigate } from 'react-router-dom';

const Login = () => {
    const [login, setLogin] = useState<string>("");
    const [range, setRange] = useState<string>("");
    const [err, setErr] = useState({ isError : false, message : '', loginAttempt : 0 })
    const nav = useNavigate();
    
    const handleNext = () => {
        setErr(prev => ({
                ...prev,
                isError : false
        }))
        const prepareData = {
            username : login
        }
        axios.post(AUTH_PATH + LOGIN_BEGIN_PATH, prepareData)
            .then((res : any) => {
                console.log(res)
                if(res.data?.range) {
                    setRange(res.data.range);
                }
            }).catch((err : any) => {
                setErr({
                    isError : true,
                    message : err.response?.data?.username,
                    loginAttempt : 1
                })
            })
    }

    const onHandleSubmit = (password : string) => {
        const prepareData= {
            username : login,
            password 
        }
        axios.post(AUTH_PATH + LOGIN_FINISH_PATH, prepareData)
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
                    message : "Credentials invalid!",
                    loginAttempt : 1
                })
            })
    }

    return (
        <Container className='container-login' fluid>
            <Form className='mt-3 d-flex flex-column justify-content-center'>
                <Row className='m-3'>
                    <Person color='green' size={30}/>
                </Row>
                <h2 style={{textAlign : "center"}}>Login</h2>
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
                    err.isError ? 
                    <Row className='ms-3 me-3'>
                        { err.message } Login attempt : { err.loginAttempt } / 5
                    </Row>
                    :
                    null
                }
                
                {
                    range ? 
                    <PasswordGroup ranges={range} onHandleSubmit={onHandleSubmit}/>
                     : 
                    <Row className='mb-5 ms-3 me-3 mt-4'>
                        <Button variant='success' onClick={handleNext}>
                            Next
                        </Button>
                    </Row>
                }
                
            </Form>
        </Container>
    )
}

export default Login