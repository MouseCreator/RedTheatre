import React, { useState, useEffect } from "react";
import { Card, Button, Container, Row, Col, Badge } from "react-bootstrap";
import axios from "axios";

const MyProfile = () => {
  const [tickets, setTickets] = useState([]);

  useEffect(() => {
    axios
      .get("http://localhost:8080/tickets/history") 
      .then((response) => {
        setTickets(response.data); 
      })
      .catch((error) => {
        console.error("Error fetching tickets:", error);
      });
  }, []);

  const handlePay = (id) => {
    axios
      .put(`http://localhost:8080/tickets/pay/${id}`)
      .then(() => {
        setTickets((prevTickets) =>
          prevTickets.map((ticket) =>
            ticket.id === id ? { ...ticket, status: "paid" } : ticket
          )
        );
      })
      .catch((error) => {
        console.error("Error processing payment:", error);
      });
  };

  const handleCancel = (id) => {
    axios
      .delete(`http://localhost:8080/tickets/cancel/${id}`)
      .then(() => {
        setTickets((prevTickets) => prevTickets.filter((ticket) => ticket.id !== id));
      })
      .catch((error) => {
        console.error("Error canceling ticket:", error);
      });
  };

  return (
    <Container className="mt-4">
      <h1 className="text-center mb-4">My Tickets</h1>
      <Row xs={1} md={2} lg={3} className="g-4">
        {tickets.map((ticket) => (
          <Col key={ticket.id}>
            <Card>
              <Card.Body>
                <Card.Title>{ticket.performanceName}</Card.Title>
                <Card.Text>
                  <strong>Theatre:</strong> {ticket.theatreName} <br />
                  <strong>Date:</strong> {ticket.date} <br />
                  <strong>Seats:</strong> {ticket.seatNumbers.join(", ")} <br />
                  <strong>Total Price:</strong> ${ticket.totalPrice} <br />
                  <Badge
                    bg={ticket.status === "paid" ? "success" : "warning"}
                    className="mt-2"
                  >
                    {ticket.status === "paid" ? "Paid" : "Unpaid"}
                  </Badge>
                </Card.Text>
                {ticket.status === "unpaid" && (
                  <div className="d-flex justify-content-between mt-3">
                    <Button
                      variant="success"
                      onClick={() => handlePay(ticket.id)}
                    >
                      Pay
                    </Button>
                    <Button
                      variant="danger"
                      onClick={() => handleCancel(ticket.id)}
                    >
                      Cancel
                    </Button>
                  </div>
                )}
              </Card.Body>
            </Card>
          </Col>
        ))}
      </Row>
    </Container>
  );
};

export default MyProfile;
