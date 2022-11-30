package rottenpotatoez.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rottenpotatoez.model.Movie;
import rottenpotatoez.model.Review;
import rottenpotatoez.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
    Optional<Review> findReviewByUserAndMovie(User user, Movie movie);
    List<Review> findReviewByMovie(Movie movie);
    List<Review> findReviewByUser(User user);
    boolean existsByUserAndMovie(User user, Movie movie);
}
