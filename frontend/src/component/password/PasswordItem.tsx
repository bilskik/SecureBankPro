import { isDisabled } from '@testing-library/user-event/dist/utils'
import React from 'react'
import { Form } from 'react-bootstrap'
import { Dispatch } from 'react'
import { SetStateAction } from 'react'

type PasswordItemType = {
    isDisabled : boolean, 
    index : string
    onValue : ({ index, value} : { index : string, value : string}) => void
}

const PasswordItem = ({ isDisabled, onValue, index }  : PasswordItemType ) => {
    const maxL = 1;
    return (
            <Form.Control
                type='password'
                maxLength={maxL}
                disabled={isDisabled}
                onChange={(e) => onValue({ index, value : e.target.value})}
                key={index}
            />

        )
}

export default PasswordItem