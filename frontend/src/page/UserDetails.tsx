import React, { useEffect, useState } from 'react'
import { useLoaderData } from 'react-router-dom'
import { UserDetailsType } from '../util/type/types.shared';
import NavComp from '../component/navbar/NavComp';
import { Button, Container } from 'react-bootstrap';
import { initDisplayedDetails } from '../util/init/init';
const UserDetails = () => {
    const user = useLoaderData() as UserDetailsType;
    const [currDisplayedDetails, setCurrDisplayedDetails] = useState<UserDetailsType>(initDisplayedDetails);

    useEffect(() => {
        setCurrDisplayedDetails(prepareHiddenDetails)
    },[])
    
    const handleOnShowDetails = () => {
        setCurrDisplayedDetails(user);
        setTimeout(() => {
            setCurrDisplayedDetails(prepareHiddenDetails())
        }, 5000)
    }

    const prepareHiddenDetails = () => {
        return {
            creditCardNo : user.creditCardNo.slice(0, -3).replace(/./g, "*"),
            pesel : user.pesel.slice(0, -3).replace(/./g, "*")
        }
    }

    return (
        <Container>
            <NavComp/>
            <p>Credit card number</p>
            <p>{ currDisplayedDetails.creditCardNo }</p>
            <p>Pesel</p>
            <p>{ currDisplayedDetails.pesel }</p>
            <Button onClick={handleOnShowDetails}>
                Show details
            </Button>
        </Container>
    )
}

export default UserDetails