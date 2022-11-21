package rottenpotatoez.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import rottenpotatoez.model.Movie;

import java.util.UUID;

public interface MovieRepository extends JpaRepository<Movie, UUID> {

}
