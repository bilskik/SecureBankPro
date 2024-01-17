import React, { useEffect } from 'react'
import { Button, Form, Stack, Row } from 'react-bootstrap'
import { useState } from 'react'
import PasswordItem from './PasswordItem'
import { PASS_INPUT_LEN } from './constant/constant'
import { ErrorType } from '../../util/type/types.shared'
import { useNavigate } from 'react-router-dom'
import { RESET_PASSWORD_PAGE } from '../../common/url/urlMapper'

type PasswordGroupType = {
    ranges : string
    onHandleSubmit : (password: string) => void,
    err : ErrorType,
    handleResetPasswordShow : () => void
}
type PasswordStoreType = {
    index : string,
    value : string
}
const PasswordGroup = ({ ranges, onHandleSubmit, err, handleResetPasswordShow } :  PasswordGroupType) => {
    const [passArr, setPassArr] = useState<PasswordStoreType[]>([{ value : "-1", index : "-1" }]);
    const arrayLengthPass = Array.from({ length : PASS_INPUT_LEN }, (_, i) => String(i))

    useEffect(() => {
        if(ranges) {
            const splitedArr = ranges.split(":")
            setPassArr(splitedArr.map((el : string, index : number) => ({ value : '-' , index : el })))
        }
    },[ranges])

    const handlePassChange = ({ index, value} : { index : string, value : string}) => {
        const updatedPassArr : PasswordStoreType[] = passArr.map((el : PasswordStoreType) => {
            if(el.index === index) {
                el.value = value
            }
            return {
                index : el.index,
                value : el.value
            }
        })
        setPassArr(updatedPassArr)
    }
    const onSubmit = () => {
        const password = passArr.map((el : PasswordStoreType) => {
            return el.value
        })
        onHandleSubmit(password.join(''))
    }
    
    return (
        <>
            <Form.Group className='m-3'>
                <Form.Label>Password</Form.Label>
                <Stack direction='horizontal' className='d-flex flex-wrap'>

                    {
                            arrayLengthPass.map((item, index) => (
                                passArr.some((el) => el.index === item) ? 
                                    <PasswordItem 
                                        isDisabled={false}
                                        onValue={handlePassChange}
                                        index={item}
                                        key={index}
                                    /> :
                                    <PasswordItem 
                                        isDisabled={true}
                                        onValue={handlePassChange}
                                        index={item}
                                        key={index}
                                    />
                            ))

                    }
                </Stack>
            </Form.Group>
            <Row className='me-2'>
                <p style={{ textAlign : "right", cursor : 'pointer'}} onClick={handleResetPasswordShow}>Reset password</p>
            </Row>
            {
                    err.isError ? 
                    <Row className='ms-1 me-3'>
                        <span style={{ color : "red" }}>{ err.message }</span>
                    </Row>
                    :
                    null
            }
            <Row className='mb-5 ms-3 me-3 mt-4'>
                <Button variant='success' onClick={onSubmit}>
                    Submit
                </Button>
            </Row>
        </>
    )
}

export default PasswordGroup