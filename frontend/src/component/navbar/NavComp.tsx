import React from 'react'
import { Container, Navbar, Nav } from 'react-bootstrap'
import { useNavigate } from 'react-router-dom'

const NavComp = () => {
  const nav = useNavigate();
  const handleOnLogout = () => {
    nav("/")
  }
  return (
    <Navbar className="border ps-5 pe-5">
      <Navbar.Brand onClick={() => nav("/")} style={{ cursor : "pointer"}}>Dashboard</Navbar.Brand>
      <Nav className="me-auto">
        <Nav.Link onClick={() => nav("/payment")}>Payment</Nav.Link>
        <Nav.Link onClick={() => nav("/details")}>Details</Nav.Link>
      </Nav>
      <Nav>
        <Nav.Link onClick={() => handleOnLogout} className='text-success' style={{ fontWeight : "bold" }}>
          Wyloguj
        </Nav.Link>
      </Nav>            
    </Navbar>
  )
}

export default NavComp