import "../bookticket.css";
import React, { useState, useEffect } from "react";
import Image1 from "../images/movies_screen/screen.png";
import gsap from "gsap";
import { useNavigate, useParams } from "react-router-dom";

export default function Bookticket() {
  const { id } = useParams(); // Get performance ID from URL
  const [performance, setPerformance] = useState(null);
  const [selectedSeats, setSelectedSeats] = useState([]);
  const navigate = useNavigate();

  // Fetch performance details by ID
  useEffect(() => {
    fetch(`http://localhost:8080/schedule/${id}`)
      .then((response) => response.json())
      .then((data) => setPerformance(data))
      .catch((error) => console.error("Error fetching performance:", error));
  }, [id]);

  // Handle seat selection
  const toggleSeatSelection = (seat) => {
    if (seat.status === "reserved") return; // Ignore reserved seats
    const isSelected = selectedSeats.includes(seat.id);
    if (isSelected) {
      setSelectedSeats(selectedSeats.filter((s) => s !== seat.id));
    } else {
      setSelectedSeats([...selectedSeats, seat.id]);
    }
  };

  // Calculate total price
  const totalPrice = selectedSeats.length * (performance?.seats[0]?.price || 0);

  // Handle booking animation and navigation
  const handleBooking = () => {
    if (selectedSeats.length === 0) return;
    gsap.to("#button", { width: "50px", duration: 0.5 });
    setTimeout(() => navigate("/"), 3000);
  };

  if (!performance) return <p>Loading...</p>;

  return (
    <div className="container">
      <div className="movie-details">
        <h2>{performance.performanceName}</h2>
        <p><strong>Theatre:</strong> {performance.theatreName}</p>
        <p><strong>Date:</strong> {performance.date}</p>
        <p><strong>Director:</strong> {performance.director}</p>
        <p><strong>Actors:</strong> {performance.actors.join(", ")}</p>
      </div>

      <ul className="showcase">
        <li>
          <div className="seat selected"></div>
          <small>Selected</small>
        </li>
        <li>
          <div className="seat occupied"></div>
          <small>Occupied</small>
        </li>
      </ul>

      <div className="movie-screen">
        <img src={Image1} alt="screen" />
        <p>All eyes this way please!</p>
      </div>

      <div className="seat-container">
        {performance.seats.map((seat) => (
          <div
            key={seat.id}
            className={`seat ${
              seat.status === "reserved" ? "occupied" : selectedSeats.includes(seat.id) ? "selected" : ""
            }`}
            onClick={() => toggleSeatSelection(seat)}
          ></div>
        ))}
      </div>

      <div className="text-wrapper">
        <p className="text">
          Selected Seats: <span id="count">{selectedSeats.length}</span>
        </p>
        <p className="text">
          Total Price ₹<span id="total">{totalPrice}</span>
        </p>
        <div onClick={handleBooking} id="button">
          <span id="text">Book</span>
        </div>
      </div>
    </div>
  );
}
