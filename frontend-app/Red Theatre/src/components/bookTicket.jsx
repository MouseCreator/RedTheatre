import "../booking.css";
import React, { useState, useEffect, useRef } from "react";
import gsap from "gsap";
import { Toast, Button } from "react-bootstrap";
import { useNavigate, useParams } from "react-router-dom";
import BookingModal from "./BookingModal";
import axios from "axios";

export default function Bookticket() {
  const { id } = useParams(); // Get the id from the URL
  const [schedule, setSchedule] = useState(null); // Store schedule data
  const [seats, setSeats] = useState([]); // Store seat data
  const [count, setCount] = useState(0);
  const [price, setPrice] = useState(0);
  const [isModalVisible, setIsModalVisible] = useState(false); // Modal visibility

  const [showError, setShowError] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  const ref = useRef(null);
  const navigate = useNavigate();
  let clicked = false;

  // Fetch schedule and seat data
  useEffect(() => {
    fetch(`http://localhost:8080/schedule/${id}`)
      .then((response) => response.json())
      .then((data) => {
        console.log("Fetched Data:", data);
        setSchedule(data);
        setSeats(data.seats || []); // Ensure seats array is set
      })
      .catch((error) => console.error("Error fetching schedule:", error));
  }, [id]);
  

  // Handle seat selection
  const handleSeatClick = (seat) => {
    if (seat.status === "reserved") return; // Ignore if the seat is reserved
    const updatedSeats = seats.map((s) => {
      if (s.id === seat.id) {
        return { ...s, status: s.status === "selected" ? "free" : "selected" };
      }
      return s;
    });
    setSeats(updatedSeats);
    const isSelected = seat.status === "selected";
    setCount(isSelected ? count - 1 : count + 1);
    setPrice(isSelected ? price - seat.price : price + seat.price);
  };

  const openModal = () => {
    console.log("count", count)
    if (count > 0) {
      setIsModalVisible(true);
      console.log(isModalVisible)
    }
  };

  const closeModal = () => {
    setIsModalVisible(false);
  };

  const confirmBooking = () => {
  
    setIsModalVisible(false);

    const bookingData = {
      performanceId: id,
      seats: seats.filter((seat) => seat.status === "selected").map((seat)=>seat.position),
    };

    axios
    .post("http://localhost:8080/booking", bookingData, {
      headers: { "Content-Type": "application/json" },
    })
    .then((response) => {
      console.log("Booking successful:", response.data);
      navigate("/profile");
    })
    .catch((error) => {
      console.error("Error creating booking:", error);
      setErrorMessage(
        error.response?.data?.message || "Failed to create booking."
      );
      setShowError(true);
    });
  };


  const handleCancel = (e) => {
    navigate("/")
  };


  const renderRows = () => {
    const rows = [];
    const seatsPerRow = 10;
    for (let i = 0; i < seats.length; i += seatsPerRow) {
      rows.push(seats.slice(i, i + seatsPerRow));
    }
    return rows;
  };

  if (!schedule) {
    return <p>Loading schedule...</p>;
  }

  return (
    <div>
    <div>
    <div className="performance-details-1">
      <div className="performance-header-1">
        {/* Image Section */}
        <div className="performance-image-container-1">
          <img
            src={schedule.pictureUrl}
            alt={schedule.performanceName}
            className="performance-image-1"
          />
        </div>

        {/* Text Section */}
        <div className="performance-meta-1">
          <h1 className="performance-title-1">{schedule.performanceName}</h1>
          <p>
            <strong>Театр:</strong> {schedule.theatreName}
          </p>
          <p>
            <strong>Режисер:</strong> {schedule.director}
          </p>
          <p>
            <strong>Дата:</strong> {new Date(schedule.date).toLocaleString()}
          </p>
          <p>
            <strong>Акторський склад:</strong> {schedule.actors.join(", ")}
          </p>
        </div>
      </div>
    </div>
  </div>


      
  <div className="seat-booking-1">
  {/* Showcase for Seat Status */}
  <ul className="showcase-1">
    <li>
      <div className="seat-1 selected"></div>
      <small>Обрані вами місця</small>
    </li>
    <li>
      <div className="seat-1 reserved"></div>
      <small>Зайняті місця</small>
    </li>
    <li>
      <div className="seat-1 free"></div>
      <small>Вільні місця</small>
    </li>
  </ul>

  {/* Seat Layout Container */}
  <div className="container-1">
    <div className="movie-screen-1">
      <p>Сцена</p>
    </div>

    {/* Seat Rows */}
    <div className="row-container-1 justify-center">
      {renderRows().map((row, rowIndex) => (
        <div key={rowIndex} className="row-1">
          {row.map((seat) => (
            <div
              key={seat.id}
              className={`seat-1 ${seat.status}`} // Apply seat status dynamically
              onClick={() => handleSeatClick(seat)}
            ></div>
          ))}
        </div>
      ))}
    </div>
  </div>

  {/* Booking Summary Section */}
  <div className="text-wrapper-1">
    <p className="text">
      <strong>Обрані місця:</strong> <span id="count">{count}</span>
    </p>
    <p className="text">
      <strong>Загальна ціна:</strong> $<span id="total">{price}</span>
    </p>
    <button className="book-button-1" onClick={openModal}>
          Забронювати
        </button>
    <button className="book-button-1" onClick={handleCancel}>
      Повернутися до вистав
    
    </button>
  </div>
</div>
{isModalVisible ? <BookingModal
        title="Confirm Booking"
        show={isModalVisible}
        onConfirm={confirmBooking}
        onClose={closeModal}
      /> : null}

<Toast
        onClose={() => setShowError(false)}
        show={showError}
        delay={3000}
        autohide
        className="position-fixed bottom-0 end-0 m-3"
      >
        <Toast.Header>
          <strong className="me-auto">Booking Error</strong>
        </Toast.Header>
        <Toast.Body>{errorMessage}</Toast.Body>
      </Toast>


    </div>
  );
}
