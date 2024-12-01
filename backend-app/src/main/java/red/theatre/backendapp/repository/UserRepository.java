package red.theatre.backendapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import red.theatre.backendapp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
