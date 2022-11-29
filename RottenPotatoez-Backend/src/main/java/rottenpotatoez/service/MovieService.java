package rottenpotatoez.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rottenpotatoez.dao.MovieRepository;
import rottenpotatoez.dto.MovieDTO;
import rottenpotatoez.model.Genre;
import rottenpotatoez.model.Movie;
import rottenpotatoez.model.Rating;
import rottenpotatoez.utils.Conversions;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class MovieService {

    private MovieRepository movieRepository;

    public Movie createOrEditMovie(MovieDTO movieDTO) {
        Movie movie = validateMovie(movieDTO);
        movieRepository.save(movie);
        return movie;
    }

    public Movie getMovie(UUID id) {
        return movieRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Movie " + id + " not found"));
    }

    public Movie getMovie(String title) {
        return movieRepository.findMovieByTitle(title).orElseThrow(() ->
                new IllegalArgumentException("Movie " + title + " not found"));
    }

    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    public void deleteMovie(UUID id) {
        Movie movie = getMovie(id);
        movieRepository.delete(movie);
    }

    private Movie validateMovie(MovieDTO movieDTO) {

        try{
            Rating.valueOf(movieDTO.getRating());
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("rating must be one of the following: G, PG, PG-13, R, NC-17");
        }

        for(String genre : movieDTO.getGenres()){
            try{
                Genre.valueOf(genre);
            }catch (IllegalArgumentException e){
                throw new IllegalArgumentException("genre must be one of the following: Action, Adventure, " +
                        "Animation, Comedy, Crime, Documentary, Drama, Family, Fantasy, History, Horror, Music," +
                        " Mystery, Romance, Science Fiction, TV Movie, Thriller, War, Western");
            }
        }

        Movie movie = Conversions.convertToModel(movieDTO);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Movie>> violations = validator.validate(movie);
        if(violations.size() > 0){
            ConstraintViolation<Movie> violation = violations.iterator().next();
            throw new IllegalArgumentException(violation.getPropertyPath()+" "+violation.getMessage());
        }
        if((!movieRepository.existsById(movie.getId())
                || (movieRepository.existsById(movie.getId())
                && !getMovie(movie.getId()).getTitle().equals(movie.getTitle())))
                && movieRepository.existsByTitle(movie.getTitle())){
            throw new IllegalArgumentException("Movie with title " + movie.getTitle() + " already exists");
        }
        return movie;
    }

}
