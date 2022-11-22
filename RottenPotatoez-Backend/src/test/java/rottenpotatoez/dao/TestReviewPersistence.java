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

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestReviewPersistence {

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
    public void testReviewPersistence() {
        String username = "testUser";
        String password = "testPassword123";
        String firstName = "testFirstName";
        String lastName = "testLastName";
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        String email = "test@mail.ca";
        String userPicture = "userPicture.png";
        boolean isAdmin = false;
        User user = new User(username, password, firstName, lastName, dateOfBirth, email, userPicture, isAdmin);
        userRepository.save(user);

        UUID movieId = UUID.randomUUID();
        String movieTitle = "movieTitle";
        String movieDescription = "movieDescription";
        int duration = 120;
        LocalDate releaseDate = LocalDate.of(2021, 1, 1);
        String moviePicture = "moviePicture.png";
        Rating rating = Rating.G;
        List<Genre> genres = List.of(Genre.Action, Genre.Adventure);
        Movie movie = new Movie(movieId, movieTitle, movieDescription, duration, releaseDate, moviePicture, rating, genres);
        movieRepository.save(movie);

        UUID reviewId = UUID.randomUUID();
        String reviewTitle = "reviewTitle";
        String reviewDescription = "reviewDescription";
        int reviewRating = 5;
        Review review = new Review(reviewId, reviewTitle, reviewDescription, reviewRating, user, movie);
        reviewRepository.save(review);

        review = reviewRepository.findById(reviewId).get();

        assertEquals(reviewId, review.getId());
        assertEquals(reviewTitle, review.getTitle());
        assertEquals(reviewDescription, review.getDescription());
        assertEquals(reviewRating, review.getRating());
        assertEquals(user.getUsername(), review.getUser().getUsername());
        assertEquals(user.getPassword(), review.getUser().getPassword());
        assertEquals(user.getFirstName(), review.getUser().getFirstName());
        assertEquals(user.getLastName(), review.getUser().getLastName());
        assertEquals(user.getDateOfBirth(), review.getUser().getDateOfBirth());
        assertEquals(user.getEmail(), review.getUser().getEmail());
        assertEquals(user.getPicture(), review.getUser().getPicture());
        assertEquals(user.isAdmin(), review.getUser().isAdmin());
        assertEquals(movie.getId(), review.getMovie().getId());
        assertEquals(movie.getTitle(), review.getMovie().getTitle());
        assertEquals(movie.getDescription(), review.getMovie().getDescription());
        assertEquals(movie.getDuration(), review.getMovie().getDuration());
        assertEquals(movie.getReleaseDate(), review.getMovie().getReleaseDate());
        assertEquals(movie.getPicture(), review.getMovie().getPicture());
        assertEquals(movie.getRating(), review.getMovie().getRating());
        for(int i = 0; i < movie.getGenres().size(); i++) {
            assertEquals(movie.getGenres().get(i), review.getMovie().getGenres().get(i));
        }

    }

}
