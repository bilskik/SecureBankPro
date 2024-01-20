import React, { useEffect, useReducer, useState } from 'react'
import { Button, FloatingLabel, FormControl, FormGroup, Container, Form, Spinner } from 'react-bootstrap'
import { initTransferData } from '../util/init/init'
import { getData, usePost } from '../common/api/apiCall'
import { redirect, useNavigate } from 'react-router-dom'
import { CSRF_PATH, DASHBOARD_PAGE, TRANSFER_MONEY_PATH, USER_DATA } from '../common/url/urlMapper'
import axios from '../common/axios/axios'
import { TransferKind, transferReducer } from '../util/reducer/transferReducer'


const Transfer = () => {
    const [transferData, dispatch] = useReducer(transferReducer, initTransferData)
    const [validated, setValidated] = useState<boolean>(false);
    const [csrf, setCsrf] = useState<string>("");
    const [isLoading, setIsLoading] = useState<boolean>(false);
    const nav = useNavigate();

    useEffect(() => {
        const res = getData({ URL : USER_DATA, headers : undefined})
        res.then((value : any) => dispatch({ type : TransferKind.SENDER_ACCNO, payload : value?.accountNo }))
    },[])

    useEffect(() => {
      axios.get(CSRF_PATH)
          .then((res : any) => {
              if(res.data && res.data.token) {
                  setCsrf(res.data.token)
              } 
          })
          .catch((res : any) => {
              console.log(res)
          })
    },[])

    const handleSubmit = (event : React.FormEvent<HTMLFormElement>) => {
      const form = event.currentTarget;
      const fromAccRegex = /^\d{26}$/;
      event.preventDefault();
      if (!form.checkValidity() || !fromAccRegex.test(transferData.receiverAccNo) || transferData.amount < 1) {
        event.stopPropagation();
        setValidated(true);
      } else {
        submitTransfer()
      }
    };

    const submitTransfer = async() => {
      setIsLoading(true);
      await axios.post(TRANSFER_MONEY_PATH, transferData, { headers : getHeaders() })
              .then((res) => {
                  setIsLoading(false);
                  nav(DASHBOARD_PAGE)
              })
              .catch((err : any) => {
                setIsLoading(false);
                console.log(err)
              })
    }

    const getHeaders = () => {
      return {
        'X-XSRF-TOKEN' : csrf
      }
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
              </FloatingLabel>
              <FloatingLabel
                controlId='from-acc'
                label="From account"
                className='mb-3'
              >
                  <FormControl 
                    required 
                    type='text'
                    placeholder='From account'
                    readOnly
                    value={transferData.senderAccNo}
                  />
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
              </FloatingLabel>
              <FloatingLabel
                controlId='to-acc'
                label="To account"
                className='mb-3'
              >
                  <FormControl 
                    required 
                    type='text'
                    placeholder='To account'
                    value={transferData.receiverAccNo}
                    onChange={(e) => dispatch({ type : TransferKind.RECEIVER_ACCNO, payload : e.target.value})}
                  />
                  <FormControl.Feedback type='invalid'>
                    [0-9][26] 
                  </FormControl.Feedback>
              </FloatingLabel>
              <FloatingLabel
                controlId='transfer-amount'
                label="Transfer amount"
                className='mb-3'
              >
                  <FormControl 
                    required 
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
              </FloatingLabel>
              <Button type="submit" variant='success'>
                Send
              </Button> 
          </Form>
        </Container>
      </>
    )
}

export default Transfer