import "./App.css";
import Navbar1 from "./components/Navbar";
import 'bootstrap/dist/css/bootstrap.min.css';
import Slider from "./components/Slider";
import Bookticket from "./components/bookTicket";
import Movies from "./components/Movies";
import SignUp from "./components/signup";
import MyProfile from "./components/MyProfile";
import React from 'react';
import { BrowserRouter, Routes, Route } from "react-router-dom";

function App() {
  return (
    <BrowserRouter> {/* Add this wrapper */}
    <Navbar1 />
      <Routes>
        <Route path="/" element={<><Slider /><Movies /></>} />
        <Route path="/movies" element={<Movies />} />
        <Route path="/booking/:id" element={<Bookticket />} />
        <Route path="/profile" element={<MyProfile />} />
        <Route path="/signup" element={<SignUp />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
