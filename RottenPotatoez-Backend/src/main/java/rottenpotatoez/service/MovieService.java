package rottenpotatoez.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rottenpotatoez.dao.MovieRepository;
import rottenpotatoez.dto.MovieDTO;
import rottenpotatoez.model.Genre;
import rottenpotatoez.model.Movie;
import rottenpotatoez.model.Rating;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class MovieService {

    private MovieRepository movieRepository;

    public Movie createMovie(MovieDTO movieDTO) {
        Movie movie = new Movie();
        movie.setTitle(movieDTO.getTitle());
        movie.setDescription(movieDTO.getDescription());
        movie.setDuration(movieDTO.getDuration());
        movie.setReleaseDate(movieDTO.getReleaseDate());
        movie.setPicture(movieDTO.getPicture());
        movie.setRating(Rating.valueOf(movieDTO.getRating()));
        movie.setGenres(movieDTO.getGenres().stream()
                .map(genre ->
                        genre.equals("True story") ?
                                Genre.True_Story :
                                Genre.valueOf(genre))
                .collect(Collectors.toList()));
        return movieRepository.save(movie);
    }

    public Movie editMovie(MovieDTO movieDTO) {
        Movie movie = getMovie(movieDTO.getId());
        if(movieDTO.getTitle() != null && !movieDTO.getTitle().isEmpty()) {
            movie.setTitle(movieDTO.getTitle());
        }
        if(movieDTO.getDescription() != null && !movieDTO.getDescription().isEmpty()) {
            movie.setDescription(movieDTO.getDescription());
        }
        if(movieDTO.getDuration() > 0) {
            movie.setDuration(movieDTO.getDuration());
        }
        if(movieDTO.getReleaseDate() != null) {
            movie.setReleaseDate(movieDTO.getReleaseDate());
        }
        if(movieDTO.getPicture() != null && !movieDTO.getPicture().isEmpty()) {
            movie.setPicture(movieDTO.getPicture());
        }
        if(movieDTO.getRating() != null && !movieDTO.getRating().isEmpty()) {
            movie.setRating(Rating.valueOf(movieDTO.getRating()));
        }
        if(movieDTO.getGenres() != null && !movieDTO.getGenres().isEmpty()) {
            movie.setGenres(movieDTO.getGenres().stream()
                    .map(genre ->
                            genre.equals("True story") ?
                                    Genre.True_Story :
                                    Genre.valueOf(genre))
                    .collect(Collectors.toList()));
        }
        return movieRepository.save(movie);
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
