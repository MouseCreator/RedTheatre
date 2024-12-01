package red.theatre.backendapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import red.theatre.backendapp.model.Seat;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    @Query("select s from Seat s where s.performance.id = :id")
    List<Seat> findAllByPerformance(Long id);
}
