import React, { useState } from 'react'
import { Button, Container, Row } from 'react-bootstrap';
import { useLoaderData, useSearchParams } from 'react-router-dom'
import { getData } from '../common/api/apiCall';
import { USER_DETAILS_DATA } from '../common/url/urlMapper';

const Dashboard = () => {
    const [transferHistory, setTransferhistory] = useState<any>();
    const user :any= useLoaderData();
    const loadTransferHistory = () => {
        const transferHistory = getData({ URL : USER_DETAILS_DATA, headers : undefined})
        setTransferhistory(transferHistory);
    }
    return (
        <Container>
            <Row>{ user.accountNo }</Row>
            <Row>{ user.balance }</Row>
            <Button onClick={loadTransferHistory}>
                history
            </Button>
        </Container>
    )
    }

export default Dashboard