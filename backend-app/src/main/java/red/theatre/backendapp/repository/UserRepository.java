package red.theatre.backendapp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import red.theatre.backendapp.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
}
