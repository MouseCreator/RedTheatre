import "../booking.css";
import React, { useState, useEffect, useRef } from "react";
import gsap from "gsap";
import { useNavigate, useParams } from "react-router-dom";

export default function Bookticket() {
  const { id } = useParams(); // Get the id from the URL
  const [schedule, setSchedule] = useState(null); // Store schedule data
  const [seats, setSeats] = useState([]); // Store seat data
  const [count, setCount] = useState(0);
  const [price, setPrice] = useState(0);
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

  // Handle booking
  const handleBooking = (e) => {
    if (clicked || count === 0) return;
    clicked = true;
    e.target.innerText = "";
    gsap.to("#button", { width: "50px", duration: 0.5 });
    setTimeout(() => navigate("/"), 3000);

    // Send booking data to the server
    const bookingData = {
      scheduleId: id,
      totalPrice: price,
      seatCount: count,
      selectedSeats: seats.filter((seat) => seat.status === "selected"),
    };

    fetch("http://localhost:8080/booking", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(bookingData),
    });
  };

  const handleCancel = (e) => {
    navigate("/")
  };


  // Render seat rows dynamically
  const renderRows = () => {
    const rows = [];
    const seatsPerRow = 10; // Adjust based on your layout
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
      <strong>Загальна ціна:</strong> ₹<span id="total">{price}</span>
    </p>
    <button className="book-button-1" onClick={handleBooking}>
      Забронювати
      <span ref={ref} className="checkmark">✓</span>
    </button>
    <button className="book-button-1" onClick={handleCancel}>
      Повернутися до вистав
    
    </button>
  </div>
</div>

    </div>
  );
}
