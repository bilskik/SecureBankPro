import React, { useState } from 'react'
import axios from '../api/axios/axios';
import { Button, Form } from 'react-bootstrap';
import { AUTH_PATH, LOGIN_BEGIN_PATH, REGISTER_PATH } from '../api/url/urlMapper';
import PasswordGroup from '../component/password/PasswordGroup';

const Login = () => {
    const [login, setLogin] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [range, setRange] = useState<string>('');
    const onHandleSubmit = () => {
        const data = {
            login : login, 
            password : password,
        }
        axios.post("/login", data)
            .then((res) => {
                console.log(res)
            })
            .catch((res) => {
                console.log(res)
            })        
    }
    const onHandleTest = () => {
        axios.get("/test")
            .then((res) => {
                console.log(res)
            })
            .catch(() => {

            })
    }

    const handleNext = () => {
        const loginBeginData = {
            username : login
        }
        axios.post(AUTH_PATH + LOGIN_BEGIN_PATH, loginBeginData)
            .then((res) => {
                if(res.data?.range) {
                    setRange(res.data.range);
                }
            }).catch(() => {

            })
    }
    return (
        <Form>
            <h2>Login</h2>
            <Form.Group>
                <Form.Label>Username</Form.Label>
                <Form.Control 
                    type='text' 
                    placeholder='username'
                    value={login}
                    onChange={(e) => setLogin(e.target.value)}
                />
            </Form.Group>

            {
                range ? <PasswordGroup ranges='1:2:4'/> : 
                <Button variant='primary' onClick={handleNext}>
                    Next
                </Button>
            }
            
        </Form>

    )
}

export default Login