import React, { useState } from 'react'
import axios from '../axios/axios';

const Login = () => {
    const [login, setLogin] = useState<string>("");
    const [password, setPassword] = useState<string>("");

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
    return (
        <div>
            <form>
                <label id='login-label'>Login</label>
                <input 
                    type='text'
                    placeholder='login'
                    id='login-label'
                    onChange={(e) => setLogin(e.target.value)}
                />
                <label id='password-label'>Password</label>
                <input 
                    type='password'
                    id='password-label' 
                    onChange={(e) => setPassword(e.target.value)}
                />
            </form>
            <button onClick={onHandleSubmit}>Submit</button>

            <button onClick={onHandleTest}>Test</button>
        </div>
    )
}

export default Login