import React from 'react'
import { Container, Navbar, Nav } from 'react-bootstrap'
import { useNavigate } from 'react-router-dom'

const NavComp = () => {
  const nav = useNavigate();
  const handleOnLogout = () => {
    nav("/")
  }
  return (
    <Navbar>
        <Container>
            <Navbar.Brand onClick={() => nav("/")}>Dashboard</Navbar.Brand>
            <Nav className="me-auto">
              <Nav.Link onClick={() => nav("/payment")}>Payment</Nav.Link>
              <Nav.Link onClick={() => nav("/details")}>Details</Nav.Link>
            </Nav>
            <Nav>
              <Nav.Link onClick={() => handleOnLogout}>Logout</Nav.Link>
            </Nav>            
        </Container>
    </Navbar>
  )
}

export default NavComp