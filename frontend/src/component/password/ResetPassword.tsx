import React, { useState } from 'react'
import { FormControl, FormGroup, FormLabel,Row, Button } from 'react-bootstrap'
import { Form } from 'react-router-dom'
import axios from '../../common/axios/axios'
import { AUTH_PATH, RESET_PASSWORD_PATH } from '../../common/url/urlMapper'
import { ErrorType } from '../../util/type/types.shared'

type ResetPasswordType = {
  handlePasswordResetUnShow : () => void
  headers : {},
  login : string
}
type UserResetPasswordType = {
  login : string,
  email : string,
  password : string
}

const ResetPassword = ({ handlePasswordResetUnShow, headers, login } : ResetPasswordType ) => {
    const [user, setUser] = useState<UserResetPasswordType>({ login : login, email : "", password : ""});
    const [err, setErr] = useState<ErrorType>({ isError : false, message : "" });
    const [showPasswordInput, setShowPasswordInput] = useState<boolean>(false);

    const handleSendEmail = () => {
      const prepareData = {
          username : user.login,
          email : user.email
      }
      setErr({
        isError : false,
        message : ""
      })
      if(isValidSendEmailData()) {
          axios.post(AUTH_PATH + RESET_PASSWORD_PATH, prepareData, { headers })
            .then((res : any) => {
              setShowPasswordInput(true);
            })
            .catch((res : any) => {
                setErr({
                  isError : true,
                  message : "Bad credentials!"
                })
                setShowPasswordInput(true);
            })
      }
    }

    const isValidSendEmailData = () => {
        if(!user.login || !user.email) {
          setErr({ isError : true, message : "Data cannot be blank!"})
          return false;
        }
        return true;
    }

    const handleResetPassword = () => {
      const prepareData = {
          username : user.login,
          email : user.email,
          password : user.password
      }
      if(isValidResetPasswordData()) {
        axios.post(AUTH_PATH + RESET_PASSWORD_PATH, prepareData, { headers })
          .then((res : any) => {
            //logged In
          })
          .catch((res : any) => {
              setErr({
                isError : true,
                message : "Bad credentials!"
              })
          })
      }
    }

    const isValidResetPasswordData = () => {
      if(isValidSendEmailData() || !user.password) {
          return false;
      }
      return true;
    }

    return (
      <>
          <FormGroup className='m-3 mt-4'>
              <FormLabel className="ms-1">Username</FormLabel>
              <FormControl 
                  type='text' 
                  placeholder='username'
                  value={user.login}
                  onChange={(e) => setUser({ ...user, login : e.target.value })}
              />
          </FormGroup>
          <FormGroup className='m-3 mt-4'>
              <FormLabel className="ms-1">Email</FormLabel>
              <FormControl 
                  type='email' 
                  placeholder='email'
                  value={user.email}
                  onChange={(e) => setUser({ ...user, email : e.target.value })}
              />
          </FormGroup>
          {
              err.isError ? 
              <Row className='ms-1 me-3'>
                  <span style={{ color : "red" }}>{ err.message }</span>
              </Row> : null
          }
          {
            showPasswordInput ? 
            <FormGroup className='m-3 mt-4'>
                <FormLabel className="ms-1">Password</FormLabel>
                <FormControl 
                    type='password' 
                    placeholder='password'
                    value={user.password}
                    onChange={(e) => setUser({ ...user, password : e.target.value })}
                />
            </FormGroup>
            : null
          }
          
          <Row className='mb-5 ms-3 me-3 mt-4'>
              <Button variant='success' onClick={handleSendEmail}>
                  Send email
              </Button>
          </Row>
      </>
    )
}

export default ResetPassword