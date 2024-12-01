package red.theatre.backendapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import red.theatre.backendapp.model.PFKey;
import red.theatre.backendapp.model.PerformanceFigure;

public interface PerformanceFigureRepository extends JpaRepository<PerformanceFigure, PFKey> {
}
