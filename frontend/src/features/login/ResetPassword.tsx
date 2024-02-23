import React, { useState } from 'react'
import { FormControl, FormGroup, FormLabel,Row, Button, Spinner } from 'react-bootstrap'
import axios from '../../config/axios'
import { AUTH_PATH, LOGIN_PAGE, RESET_PASSWORD_BEGIN_PATH, RESET_PASSWORD_FINISH_PATH } from '../../config/urlMapper'
import { ErrorType } from '../../util/type/types.shared'
import { resetPasswordValidator, sendEmailValidator } from '../../util/validator/validators'

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
    const [isLoading, setIsLoading] = useState<boolean>(false);
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

      if(sendEmailValidator(prepareData)) {
          setIsLoading(true);
          axios.post(AUTH_PATH + RESET_PASSWORD_BEGIN_PATH, prepareData, { headers })
            .then((res : any) => {
                setIsLoading(false);
                console.log(`I've sent email to ${user.email}`)
                setShowPasswordInput(true);
            })
            .catch((res : any) => {
                setIsLoading(false);
                setErr({
                  isError : true,
                  message : "Bad credentials!"
                })
            })
      } else {
        setErr({
          isError : true,
          message : "Username and email cannot be blank!"
        })
      }
    }

    const handleResetPassword = () => {
      const prepareData = {
          username : user.login,
          email : user.email,
          password : user.password.trim()
      }
      if(resetPasswordValidator(prepareData)) {
        setIsLoading(true);
        axios.post(AUTH_PATH + RESET_PASSWORD_FINISH_PATH, prepareData, { headers })
          .then((res : any) => {
              setIsLoading(false);
              handlePasswordResetUnShow()
          })
          .catch((res : any) => {
              setIsLoading(false);
              if(res.request.response) {
                setErr({
                  isError : true,
                  message : res.request.response
                })
              } else {
                setErr({
                  isError : true,
                  message : "We've got an unexpected error. Try to restart the page!"
                })
              }
              setShowPasswordInput(true);
          })
      }
    }

    return (
      <>
          { isLoading ? <Spinner className='spinner'/> : null}
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
                <p className='mt-1'>Password should contain at least: upper, lower character, one digit, one special char, at least 13 characters. 
                  Whitespace characters will be removed!</p>
            </FormGroup>
            : null
          }
          
          <Row className='mb-5 ms-3 me-3 mt-4'>
              <Button variant='success' onClick={showPasswordInput ? handleResetPassword : handleSendEmail}>
                  {
                    showPasswordInput ? 'Reset Password' : 'Send Email'
                  }
              </Button>
          </Row>
      </>
    )
}

export default ResetPassword