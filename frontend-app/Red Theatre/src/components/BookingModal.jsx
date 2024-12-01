import React from "react";
import { Modal, Button } from "react-bootstrap";

const BookingModal = ({ title, show, onConfirm, onClose }) => {
  return (
    <Modal show={show} onHide={onClose} centered>
      <Modal.Header closeButton>
        <Modal.Title>{title}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <p>Do you confirm your choice?</p>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="success" onClick={onConfirm}>
          Yes
        </Button>
        <Button variant="danger" onClick={onClose}>
          No
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default BookingModal;
