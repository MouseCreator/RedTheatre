package red.theatre.backendapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import red.theatre.backendapp.model.PerformanceDetails;

public interface PerformanceDetailsRepository extends JpaRepository<PerformanceDetails, Long> {
}
