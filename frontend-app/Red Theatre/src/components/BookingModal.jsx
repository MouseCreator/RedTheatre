import React from "react";
import { Modal, Button } from "react-bootstrap";

const BookingModal = ({ title, show, onConfirm, onClose,subtitle }) => {
  return (
    <Modal show={show} onHide={onClose} centered>
      <Modal.Header closeButton>
        <Modal.Title>{title}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <p>{subtitle}</p>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="success" onClick={onConfirm}>
          Так
        </Button>
        <Button variant="danger" onClick={onClose}>
          НІ
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default BookingModal;
