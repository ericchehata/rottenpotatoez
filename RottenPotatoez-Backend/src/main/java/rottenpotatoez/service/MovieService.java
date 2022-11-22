package rottenpotatoez.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rottenpotatoez.dao.MovieRepository;
import rottenpotatoez.dto.MovieDTO;
import rottenpotatoez.model.Movie;
import rottenpotatoez.utils.Conversions;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class MovieService {

    private MovieRepository movieRepository;

    public Movie createOrEditMovie(MovieDTO movieDTO) {
        Movie movie = Conversions.convertToModel(movieDTO);
        return movieRepository.save(Conversions.convertToModel(movieDTO));
    }

    public Movie getMovie(UUID id) {
        return movieRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Movie " + id + " not found"));
    }

    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    public void deleteMovie(UUID id) {
        movieRepository.deleteById(id);
    }

}
