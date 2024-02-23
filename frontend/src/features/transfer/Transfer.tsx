import React, { useContext, useEffect, useReducer, useState } from 'react'
import { Button, FloatingLabel, FormControl, FormGroup, Container, Form, Spinner } from 'react-bootstrap'
import { initTransferData } from '../../util/init/init'
import { getData, usePost } from '../../config/apiCall'
import { redirect, useNavigate } from 'react-router-dom'
import { CSRF_PATH, DASHBOARD_PAGE, LOGIN_PAGE, TRANSFER_MONEY_PATH, USER_DATA } from '../../config/urlMapper'
import axios from '../../config/axios'
import { TransferKind, transferReducer } from '../../util/reducer/transferReducer'
import { formAccValidator, formAmountValidator } from '../../util/validator/validators'
import { AuthContext } from '../../util/context/AuthProvider'


const Transfer = () => {
    const [transferData, dispatch] = useReducer(transferReducer, initTransferData)
    const [formFieldValidity, setFormFieldValidity] = useState({ isSenderAccNoValid : false, isReceiverAccNoValid : false, isAmountValid : false, isFormValid : true });
    const [validated, setValidated] = useState<boolean>(false);
    const [isLoading, setIsLoading] = useState<boolean>(false);
    const { authData, getCSRFHeader } = useContext(AuthContext);
    const nav = useNavigate();

    useEffect(() => {
        const res = getData({ URL : USER_DATA, headers : undefined})
        res.then((value : any) => dispatch({ type : TransferKind.SENDER_ACCNO, payload : value?.accountNo }))
    },[])

    useEffect(() => {
      if(!authData.isAuthenticated) {
        nav(LOGIN_PAGE)
      } 
    },[])

    const handleSubmit = (event : React.FormEvent<HTMLFormElement>) => {
      const form = event.currentTarget;
      event.preventDefault();

      const checkIfFormIsValid = () => {
        let isValid = true;
        if(!formAccValidator(transferData.receiverAccNo)) {
          setFormFieldValidity({ ...formFieldValidity , isSenderAccNoValid : false})
          isValid = false;
        }
        if(!formAccValidator(transferData.senderAccNo)) {
          setFormFieldValidity({ ...formFieldValidity , isReceiverAccNoValid : false })
          isValid = false;
        }
        if(!formAmountValidator(transferData.amount)) {
          setFormFieldValidity({ ...formFieldValidity, isAmountValid : false })
          isValid = false;
        }
        if(!form.checkValidity()) {
          isValid = false;
        }
        return isValid;
      }
      const isValid = checkIfFormIsValid();
      if(!isValid) {
        setFormFieldValidity({ ...formFieldValidity, isFormValid : false })
        event.stopPropagation();
        setValidated(true);
      } else {
        setFormFieldValidity({ ...formFieldValidity, isFormValid : true })
        submitTransfer()
      }
    };

    const submitTransfer = async() => {
      setIsLoading(true);
      await axios.post(TRANSFER_MONEY_PATH, transferData, { headers : getCSRFHeader() })
              .then((res) => {
                  setIsLoading(false);
                  nav(DASHBOARD_PAGE)
              })
              .catch((err : any) => {
                setIsLoading(false);
              })
    }

    return (
      <>
        { isLoading ? <Spinner className='spinner'/> : null }
        <Container className='mt-5'>
          <Form noValidate validated={validated} onSubmit={(e) => handleSubmit(e)}>
              <h2>Transfer</h2>
              <FloatingLabel
                controlId='sender-name'
                label="Sender name"
                className='mb-3'
              >
                  <FormControl
                    required
                    type='text'
                    placeholder='Sender name'
                    value={transferData.senderName}
                    onChange={(e) => dispatch({ type : TransferKind.SENDER_NAME, payload : e.target.value})}
                  />
                  <FormControl.Feedback type='invalid' >
                    Cannot be blank!
                  </FormControl.Feedback>
              </FloatingLabel>
              <FloatingLabel
                controlId='from-acc'
                label="From account"
                className='mb-3'
              >
                  <FormControl 
                    isValid={formFieldValidity.isSenderAccNoValid}
                    type='text'
                    placeholder='From account'
                    readOnly
                    value={transferData.senderAccNo}
                  />
                  <FormControl.Feedback type='invalid'>
                    Only: 0-9, 26 numbers 
                  </FormControl.Feedback>
              </FloatingLabel>
              <FloatingLabel
                controlId='receiver-name'
                label="Receiver name"
                className='mb-3'
              >
                  <FormControl
                    required 
                    type='text'
                    placeholder='Receiver name'
                    value={transferData.receiverName}
                    onChange={(e) => dispatch({ type : TransferKind.RECEIVER_NAME, payload : e.target.value})}
                  />
                  <FormControl.Feedback type='invalid'>
                    Cannot be blank!
                  </FormControl.Feedback>
              </FloatingLabel>
              <FloatingLabel
                controlId='to-acc'
                label="To account"
                className='mb-3'
              >
                  <FormControl 
                    required
                    isValid={formFieldValidity.isReceiverAccNoValid} 
                    type='text'
                    placeholder='To account'
                    value={transferData.receiverAccNo}
                    onChange={(e) => dispatch({ type : TransferKind.RECEIVER_ACCNO, payload : e.target.value})}
                  />
                  <FormControl.Feedback type='invalid'>
                    Only: 0-9, 26 numbers 
                  </FormControl.Feedback>
              </FloatingLabel>
              <FloatingLabel
                controlId='transfer-amount'
                label="Transfer amount"
                className='mb-3'
              >
                  <FormControl 
                    required
                    isValid={formFieldValidity.isAmountValid}
                    type='text'
                    placeholder='Transfer amount'
                    value={transferData.amount == 0 ? "" : transferData.amount}
                    onChange={(e) => dispatch({ type : TransferKind.TRANSFER_AMOUNT, payload : e.target.value})}
                  />
                  <FormControl.Feedback type='invalid'>
                    Must be greater than 0! 
                  </FormControl.Feedback>
              </FloatingLabel>
              <FloatingLabel
                controlId='transfer-title'
                label="Transfer title"
                className='mb-3'
              >
                  <FormControl 
                    required 
                    type='text'
                    placeholder='Transfer title'
                    value={transferData.transferTitle}
                    onChange={(e) => dispatch({ type : TransferKind.TRANSFER_TITLE, payload : e.target.value})}
                  />
                  <FormControl.Feedback type='invalid'>
                    Cannot be blank! 
                  </FormControl.Feedback>
              </FloatingLabel>
              { !formFieldValidity.isFormValid ? <p style={{ color : "red" }}>Invalid Form!</p> : null}
              <Button type="submit" variant='success'>
                Send
              </Button> 
          </Form>
        </Container>
      </>
    )
}

export default Transfer