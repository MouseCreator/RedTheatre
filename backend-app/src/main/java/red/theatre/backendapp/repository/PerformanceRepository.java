package red.theatre.backendapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import red.theatre.backendapp.model.Performance;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {
}
