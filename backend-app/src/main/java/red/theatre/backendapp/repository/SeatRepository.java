package red.theatre.backendapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import red.theatre.backendapp.model.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
