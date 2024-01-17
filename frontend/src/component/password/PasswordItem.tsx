import { Form } from 'react-bootstrap'

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
                style={{ maxWidth : "35px"}}
            />

        )
}

export default PasswordItem