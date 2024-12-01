import React, { useState, useEffect } from "react";
import axios from "axios";
import Card from "react-bootstrap/Card";
import Button from "react-bootstrap/Button";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import { useNavigate } from "react-router-dom";

function Movies() {
  const [performances, setPerformances] = useState([]);
  const navigate = useNavigate(); // Initialize navigation

  // Fetch performances from the endpoint
  useEffect(() => {
    axios
      .get("http://localhost:8080/schedule") // Replace with your API endpoint
      .then((response) => {
        setPerformances(response.data); // Assuming the response is an array of `FullPerformanceResponseDTO`
      })
      .catch((error) => {
        console.error("Error fetching performances:", error);
      });
  }, []);

  // Navigate to the booking page
  const handleRedirect = (id) => {
    navigate(`/booking/${id}`);
  };

  return (
    <Container className="mt-4">
      <Row>
        {performances.map((performance) => (
          <Col key={performance.id} md={4} sm={6} className="mb-4">
            <Card>
              <Card.Img
                variant="top"
                src={performance.pictureUrl}
                alt={performance.performanceName}
                style={{ height: "200px", objectFit: "cover" }}
              />
              <Card.Body>
                {/* Redirect when clicking on the performance name */}
                <Card.Title>
                    {performance.performanceName}
                </Card.Title>
                <Card.Subtitle className="mb-2 text-muted">
                  {performance.theatreName}
                </Card.Subtitle>
                <Card.Text>
                  <strong>Director:</strong> {performance.director}
                  <br />
                  <strong>Date:</strong> {performance.date}
                  <br />
                </Card.Text>
                <Button 
                variant="primary"
                onClick={() => handleRedirect(performance.id)}
                >
                Book Tickets</Button>
              </Card.Body>
            </Card>
          </Col>
        ))}
      </Row>
    </Container>
  );
}

export default Movies;
