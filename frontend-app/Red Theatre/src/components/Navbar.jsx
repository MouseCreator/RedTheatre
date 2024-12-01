import React, { useState, useEffect } from "react";
import Navbar from "react-bootstrap/Navbar";
import Container from "react-bootstrap/Container";
import Nav from "react-bootstrap/Nav";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Popup from "reactjs-popup";
import Login from "./loginsignup";
import axios from "axios";

function UnifiedNavbar() {
  const [login, setLogin] = useState("Увійти");

  useEffect(() => {
    axios
      .post("https://book-my-show-back-end.onrender.com/check")
      .then((response) => {
        setLogin(response.data);
      })
      .catch((error) => {
        console.error("Error fetching login data:", error);
      });
  }, []);

  return (
    <Navbar bg="light" expand="lg" className="mb-3">
      <Container fluid>
      <Navbar.Collapse id="navbar-nav" className="w-100 d-flex justify-content-between">
  <Navbar.Brand href="#" className="me-auto">
    Червоний театр
  </Navbar.Brand>

  <Nav className="d-flex justify-content-center flex-grow-1">
    <Nav.Link href="/movies">Репертуар</Nav.Link>
    <Nav.Link href="/personnel">Персоналії</Nav.Link>
    <Nav.Link href="/store">Крамничка</Nav.Link>
    <Nav.Link href="/about">Про театр</Nav.Link>
    <Nav.Link href="/gift-certificate">Подарунковий сертифікат</Nav.Link>
  </Nav>

  <Nav className="ms-auto">
    <Nav.Link href="/personal-cabinet">Особистий кабінет</Nav.Link>
  </Nav>
</Navbar.Collapse>

       
  
      </Container>
    </Navbar>
  );
}

export default UnifiedNavbar;
