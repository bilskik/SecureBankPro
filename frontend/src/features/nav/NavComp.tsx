import React, { useContext, useEffect, useState } from 'react'
import { Container, Navbar, Nav } from 'react-bootstrap'
import { Outlet, useNavigate } from 'react-router-dom'
import { CSRF_PATH, DASHBOARD_PAGE, DETAILS_PAGE, LOGIN_BEGIN_PATH, LOGIN_PAGE, LOGOUT_PATH, PAYMENT_PAGE } from '../../config/urlMapper';
import axios from '../../config/axios';

import { useCookies } from 'react-cookie';
import { AuthContext } from '../../util/context/AuthProvider';

const NavComp = () => {
  const nav = useNavigate();
  const [cookie, setCookie, removeCookie] = useCookies(['SESSION',"XSRF-TOKEN"])
  const { authData, getCSRFHeader, resetAuthData } = useContext(AuthContext);

  const handleOnLogout = async () => {
    const res = axios.post(LOGOUT_PATH, undefined, { headers : getCSRFHeader() })
        .then(() => {
          removeCookie('SESSION')
          removeCookie('XSRF-TOKEN')
          resetAuthData();
          nav(LOGIN_PAGE)
        })
        .catch(() => {
            
        })
  }

  return (
    authData.isAuthenticated ?  
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
      : null
  )
}

export default NavComp