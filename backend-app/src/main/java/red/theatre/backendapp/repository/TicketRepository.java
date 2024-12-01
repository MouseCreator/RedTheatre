package red.theatre.backendapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import red.theatre.backendapp.model.Ticket;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("select t from Ticket t where t.owner.id = :id")
    List<Ticket> findAllByOwner(Long id);
}
