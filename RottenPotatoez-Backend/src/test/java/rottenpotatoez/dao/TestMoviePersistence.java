package rottenpotatoez.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import rottenpotatoez.model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestMoviePersistence {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void tearDown() {
        reviewRepository.deleteAll();
        movieRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testMoviePersistence() {
        UUID movieId = UUID.randomUUID();
        String movieTitle = "movieTitle";
        int duration = 120;
        LocalDate releaseDate = LocalDate.of(2021, 1, 1);
        String moviePicture = "moviePicture.png";
        Rating rating = Rating.G;
        List<Genre> genres = List.of(Genre.Action, Genre.Adventure);
        Movie movie = new Movie(movieId, movieTitle, duration, releaseDate, moviePicture, rating, genres);
        movieRepository.save(movie);

        if(movieRepository.findById(movieId).isEmpty()) fail("Movie not saved");

        movie = movieRepository.findById(movieId).get();

        assertEquals(movieId, movie.getId());
        assertEquals(movieTitle, movie.getTitle());
        assertEquals(duration, movie.getDuration());
        assertEquals(releaseDate, movie.getReleaseDate());
        assertEquals(moviePicture, movie.getPicture());
        assertEquals(rating, movie.getRating());
        for(int i = 0; i < movie.getGenres().size(); i++) {
            assertEquals(movie.getGenres().get(i), movie.getGenres().get(i));
        }

    }

}
