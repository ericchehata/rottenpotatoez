package rottenpotatoez.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import rottenpotatoez.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
}
