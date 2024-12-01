package red.theatre.backendapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import red.theatre.backendapp.model.Performance;

import java.util.List;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {
    List<Performance> findAllByOrderByTimeDateAsc();
}
