import React, { useEffect, useState } from 'react'
import { Button, Container, Row, Spinner, Stack } from 'react-bootstrap';
import { Outlet, useLoaderData, useNavigate } from 'react-router-dom'
import { getData, useFetch } from '../common/api/apiCall';
import { TRANSFER_HISTORY_PATH } from '../common/url/urlMapper';
import { TransferType, UserDataType } from '../util/type/types.shared';
import TransferHistory from '../component/transfer/TransferHistory';
import NavComp from '../component/navbar/NavComp';

const Dashboard = () => {
    const { data, isLoading, err, getData} = useFetch({ URL : TRANSFER_HISTORY_PATH, headers : undefined})
    const [transferHistory, setTransferhistory] = useState<TransferType[] | undefined>(data);
    const user = useLoaderData() as UserDataType;
    const nav = useNavigate();

    useEffect(() => {
        if(!isLoading && !err) {
            setTransferhistory(data)
        }
    },[isLoading])
    
    const loadTransferHistory = () => {
        getData();
    }
    
    const handleTransfer = () => {

    }

    return (
        <>
            <NavComp/>
            { 
                isLoading && <Spinner animation='border'/>
            }
            <Container fluid="sm">
                <Container className='border mt-5 p-2' fluid="md">
                    <Row className="ps-2"><h2 style={{ "fontWeight" : "bold"}}>eKonto</h2></Row>
                    <Row className="ps-2">{ user.accountNo }</Row>
                    <Row className="ps-2">Available funds:</Row>
                    <Row className="ps-2">{ user.balance }</Row>
                    <Stack direction='horizontal'>
                        <Button onClick={(e) => nav("/payment")}>
                            Transfer
                        </Button>
                        <Button onClick={loadTransferHistory}>
                            history
                        </Button>
                    </Stack>
                </Container>  

                <TransferHistory transferHistory={transferHistory}/> 
            </Container>
        </>

    )
    }

export default Dashboard