package red.theatre.backendapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import red.theatre.backendapp.model.Figure;

public interface FigureRepository extends JpaRepository<Figure, Long> {
}
