import React, { useEffect, useState } from 'react'
import { Container, Navbar, Nav } from 'react-bootstrap'
import { Outlet, useNavigate } from 'react-router-dom'
import { DASHBOARD_PAGE, DETAILS_PAGE, LOGIN_BEGIN_PATH, LOGIN_PAGE, LOGOUT_PATH, PAYMENT_PAGE } from '../../common/url/urlMapper';
import { useFetch } from '../../common/api/apiCall';
import axios from '../../common/axios/axios';

import { useCookies } from 'react-cookie';
const NavComp = () => {
  const nav = useNavigate();
  const [cookie, setCookie, removeCookie] = useCookies(['SESSION',"XSRF-TOKEN"])
  const [csrf, setCsrf] = useState<string>("");

  useEffect(() => {
    axios.get("/auth/csrf")
        .then((res : any) => {
            if(res.data && res.data.token) {
                setCsrf(res.data.token)
            } 
        })
        .catch((res : any) => {
            console.log(res)
        })
},[])

  const handleOnLogout = async () => {
    const res = axios.post(LOGOUT_PATH, undefined, { headers : getHeaders() })
        .then((res : any) => {
          removeCookie('SESSION')
          removeCookie('XSRF-TOKEN')
          nav(LOGIN_PAGE)
        })
        .catch((res : any) => {
          console.log(res)
        })
  }

  const getHeaders = () => {
    return {
      'X-XSRF-TOKEN' : csrf
    }
  }

  return (
    <>
      <Navbar className="border ps-5 pe-5">
        <Navbar.Brand onClick={() => nav(DASHBOARD_PAGE)} style={{ cursor : "pointer"}}>Dashboard</Navbar.Brand>
        <Nav className="me-auto">
          <Nav.Link onClick={() => nav(PAYMENT_PAGE)}>Payment</Nav.Link>
          <Nav.Link onClick={() => nav(DETAILS_PAGE)}>Details</Nav.Link>
        </Nav>
        <Nav>
          <Nav.Link onClick={handleOnLogout} className='text-success' style={{ fontWeight : "bold" }}>
            Wyloguj
          </Nav.Link>
        </Nav>            
      </Navbar>
      <Outlet/>
    </>
  )
}

export default NavComp