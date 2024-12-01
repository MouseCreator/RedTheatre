package red.theatre.backendapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import red.theatre.backendapp.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}