import React, { useEffect, useState } from 'react'
import { useLoaderData } from 'react-router-dom'
import { UserDetailsType } from '../../util/type/types.shared';
import { Button, Container } from 'react-bootstrap';
import { initDisplayedDetails } from '../../util/init/init';

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
        <>
            <Container className='mt-5'>
                <p style={{ fontWeight : "bold"}}>Credit card number</p>
                <p>{ currDisplayedDetails.creditCardNo }</p>
                <p style={{ fontWeight : "bold"}}>Pesel</p>
                <p>{ currDisplayedDetails.pesel }</p>
                <Button onClick={handleOnShowDetails} variant='success'>
                    Show details
                </Button>
            </Container>
        </>
    )
}

export default UserDetails