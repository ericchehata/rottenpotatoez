package rottenpotatoez.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import rottenpotatoez.model.Review;

import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
}
