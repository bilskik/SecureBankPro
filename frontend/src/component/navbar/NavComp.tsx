import React from 'react'
import { Container, Navbar, Nav } from 'react-bootstrap'
import { useNavigate } from 'react-router-dom'

const NavComp = () => {
  const nav = useNavigate();
  return (
    <Navbar>
        <Container>
            <Navbar.Brand onClick={() => nav("/")}>Dashboard</Navbar.Brand>
            <Nav className="me-auto">
              <Nav.Link onClick={() => nav("/payment")}>Payment</Nav.Link>
            </Nav>
        </Container>
    </Navbar>
  )
}

export default NavComp