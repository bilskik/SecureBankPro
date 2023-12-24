import React, { useState } from 'react'
import axios from '../common/axios/axios';
import { Button, Form } from 'react-bootstrap';
import { AUTH_PATH, LOGIN_BEGIN_PATH, LOGIN_FINISH_PATH, REGISTER_PATH } from '../common/url/urlMapper';
import PasswordGroup from '../component/password/PasswordGroup';

const Login = () => {
    const [login, setLogin] = useState<string>("");
    const [range, setRange] = useState<string>("");

    const handleNext = () => {
        const prepareData = {
            username : login
        }
        axios.post(AUTH_PATH + LOGIN_BEGIN_PATH, prepareData)
            .then((res) => {
                if(res.data?.range) {
                    setRange(res.data.range);
                }
            }).catch(() => {

            })
    }
    const onHandleSubmit = (password : string) => {
        const prepareData= {
            username : login,
            password 
        }
        axios.post(AUTH_PATH + LOGIN_FINISH_PATH, prepareData)
            .then((res : any) => {
                console.log(res)
            })
            .catch(() => {

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
                range ? <PasswordGroup ranges={range} onHandleSubmit={onHandleSubmit}/> : 
                <Button variant='primary' onClick={handleNext}>
                    Next
                </Button>
            }
            
        </Form>

    )
}

export default Login