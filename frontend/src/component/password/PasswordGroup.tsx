import React, { useEffect } from 'react'
import { Button, Form, Stack } from 'react-bootstrap'
import { useState } from 'react'
import PasswordItem from './PasswordItem'
import { PASS_INPUT_LEN } from './constant/constant'

type PasswordGroupType = {
    ranges : string
    onHandleSubmit : (password: string) => void
}
type PasswordStoreType = {
    index : string,
    value : string
}
const PasswordGroup = ({ ranges, onHandleSubmit } :  PasswordGroupType) => {
    const [password,setPassword] = useState<string>('');
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
            <Form.Label>Password</Form.Label>
            <Stack direction='horizontal'>

                {
                        arrayLengthPass.map((item, index) => (
                            passArr.some((el) => el.index === item) ? 
                                <PasswordItem 
                                    isDisabled={false}
                                    onValue={handlePassChange}
                                    index={item}
                                /> :
                                <PasswordItem 
                                    isDisabled={true}
                                    onValue={handlePassChange}
                                    index={item}
                                />
                        ))

                }
            </Stack>
            <Button variant='primary' onClick={onSubmit}>
                Submit
            </Button>
        </>
    )
}

export default PasswordGroup