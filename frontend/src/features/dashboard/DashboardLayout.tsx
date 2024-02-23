import React, { useEffect, useState } from 'react'
import { Button, Container, Row, Spinner, Stack, Col } from 'react-bootstrap';
import { Outlet, useLoaderData, useNavigate } from 'react-router-dom'
import { useFetch } from '../../config/apiCall';
import { PAYMENT_PAGE, TRANSFER_HISTORY_PATH } from '../../config/urlMapper';
import { TransferType, UserDataType } from '../../util/type/types.shared';
import TransferHistory from '../transfer/TransferHistory';

const DashboardLayout = ({ user } : { user : UserDataType }) => {
    const { data, isLoading, err, getData } = useFetch({ URL : TRANSFER_HISTORY_PATH, headers : undefined })
    const [transferHistory, setTransferhistory] = useState<TransferType[] | undefined>(data);
    const nav = useNavigate();

    useEffect(() => {
        if(!isLoading && !err) {
            setTransferhistory(data)
        }
    },[isLoading])
    
    const loadTransferHistory = () => {
        getData();
    }

    return (
        <>
            <Outlet/>
            { 
                isLoading && <Spinner animation='border' className='spinner'/>
            }
            <Container>
                <Container className='border mt-5 p-2 container-dashboard' fluid>
                    <Row className="ms-2 mb-3">
                        <h2 style={{ "fontWeight" : "bold"}}>eKonto</h2>
                    </Row>
                    <Row className='ms-2'>Account number</Row>
                    <Row className="ms-2 mb-3">{ user.accountNo }</Row>
                    <Row className="ms-2">Available funds:</Row>
                    <Row className="ms-2 mb-3" style={{ fontWeight : "bold", fontSize : "1.2rem"}}>{ user.balance } PLN</Row>
                    <Stack direction='horizontal'>
                        <Button onClick={(e) => nav(PAYMENT_PAGE)} variant='success' className='me-2'>
                            Transfer
                        </Button>
                        <Button onClick={loadTransferHistory} variant='success'>
                            History
                        </Button>
                    </Stack>
                </Container>
                <TransferHistory transferHistory={transferHistory}/> 
            </Container>
        </>

    )
    }

export default DashboardLayout