package red.theatre.backendapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import red.theatre.backendapp.model.Figure;
import red.theatre.backendapp.model.PFKey;
import red.theatre.backendapp.model.PerformanceFigure;

import java.util.List;

public interface PerformanceFigureRepository extends JpaRepository<PerformanceFigure, PFKey> {
    @Query("select f.figure from PerformanceFigure f where f.performance.id = :id")
    List<Figure> findAllByPerformance(Long id);
}
