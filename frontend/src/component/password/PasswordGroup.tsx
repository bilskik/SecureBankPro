import React, { useEffect } from 'react'
import { Form, Stack } from 'react-bootstrap'
import { useState } from 'react'
import PasswordItem from './PasswordItem'
import { PASS_INPUT_LEN } from './constant/constant'

type PasswordGroupType = {
    ranges : string
}
type PasswordStoreType = {
    index : string,
    value : string
}
const PasswordGroup = ({ ranges } :  PasswordGroupType) => {
    const [password,setPassword] = useState<string>('');
    const [passArr, setPassArr] = useState<PasswordStoreType[]>([{ value : "-1", index : "-1" }]);
    const arrayLengthPass = Array.from({ length : 20 }, (_, i) => String(i))

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
    console.log(passArr)
    return (
        <>
            <Form.Label>Password</Form.Label>
            <Stack direction='horizontal'>

                {
                        arrayLengthPass.map((item, index) => (
                            passArr.some((el) => el.index === item) ? 
                                <PasswordItem 
                                    isDisabled={false}
                                    // setPassword={setPassword}
                                    // value=""
                                    onValue={handlePassChange}
                                    index={item}
                                /> :
                                <PasswordItem 
                                    isDisabled={true}
                                    // setPassword={setPassword}
                                    // value=''
                                    onValue={handlePassChange}
                                    index={item}
                                />
                        ))

                }
            </Stack>
        </>
    )
}

export default PasswordGroup