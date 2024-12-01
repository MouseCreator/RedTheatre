package red.theatre.backendapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import red.theatre.backendapp.model.Theatre;

public interface TheatreRepository extends JpaRepository<Theatre, Long> {
}
