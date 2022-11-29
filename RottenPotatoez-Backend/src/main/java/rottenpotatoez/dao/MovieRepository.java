package rottenpotatoez.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rottenpotatoez.model.Movie;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MovieRepository extends JpaRepository<Movie, UUID> {
    Optional<Movie> findMovieByTitle(String title);
    boolean existsByTitle(String title);
}
