package rottenpotatoez.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import rottenpotatoez.dao.ReviewRepository;
import rottenpotatoez.dto.MovieDTO;
import rottenpotatoez.dto.ReviewDTO;
import rottenpotatoez.dto.UserDTO;
import rottenpotatoez.model.Movie;
import rottenpotatoez.model.Review;
import rottenpotatoez.model.User;
import rottenpotatoez.utils.Conversions;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTests {

    @Mock
    private UserService userService;

    @Mock
    private MovieService movieService;

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    private static final String USER_USERNAME = "username";
    private static final String USER_PASSWORD = "Password123";
    private static final String USER_FIRST_NAME = "firstName";
    private static final String USER_LAST_NAME = "lastName";
    private static final LocalDate USER_DATE_OF_BIRTH = LocalDate.of(2000, 1, 1);
    private static final String USER_EMAIL = "email@mail.ca";
    private static final String USER_PICTURE = "picture.png";
    private static final boolean USER_ADMIN = false;
    private static final UserDTO USER_DTO = new UserDTO(USER_USERNAME, USER_PASSWORD, USER_FIRST_NAME,
            USER_LAST_NAME, USER_DATE_OF_BIRTH, USER_EMAIL, USER_PICTURE, USER_ADMIN);

    private static final String USER2_USERNAME = "username2";
    private static final String USER2_PASSWORD = "Password123";
    private static final String USER2_FIRST_NAME = "firstName2";
    private static final String USER2_LAST_NAME = "lastName2";
    private static final LocalDate USER2_DATE_OF_BIRTH = LocalDate.of(2000, 1, 1);
    private static final String USER2_EMAIL = "email2@mail.ca";
    private static final String USER2_PICTURE = "picture2.png";
    private static final boolean USER2_ADMIN = false;
    private static final UserDTO USER2_DTO = new UserDTO(USER2_USERNAME, USER2_PASSWORD, USER2_FIRST_NAME,
            USER2_LAST_NAME, USER2_DATE_OF_BIRTH, USER2_EMAIL, USER2_PICTURE, USER2_ADMIN);

    private static final UUID MOVIE_ID = UUID.randomUUID();
    private static final String MOVIE_TITLE = "Movie Title";
    private static final int MOVIE_DURATION = 120;
    private static final LocalDate MOVIE_RELEASE_DATE = LocalDate.now();
    private static final String MOVIE_PICTURE = "Movie Picture";
    private static final String MOVIE_RATING = "G";
    private static final List<String> MOVIE_GENRES = List.of("Action", "Adventure");
    private static final MovieDTO MOVIE_DTO = new MovieDTO(MOVIE_ID, MOVIE_TITLE,
            MOVIE_DURATION, MOVIE_RELEASE_DATE, MOVIE_PICTURE, MOVIE_RATING, MOVIE_GENRES);

    private static final UUID MOVIE_ID_2 = UUID.randomUUID();
    private static final String MOVIE_TITLE_2 = "Movie Title 2";
    private static final int MOVIE_DURATION_2 = 120;
    private static final LocalDate MOVIE_RELEASE_DATE_2 = LocalDate.now();
    private static final String MOVIE_PICTURE_2 = "Movie Picture 2";
    private static final String MOVIE_RATING_2 = "R";
    private static final List<String> MOVIE_GENRES_2 = List.of("Comedy");
    private static final MovieDTO MOVIE_DTO_2 = new MovieDTO(MOVIE_ID_2, MOVIE_TITLE_2,
            MOVIE_DURATION_2, MOVIE_RELEASE_DATE_2, MOVIE_PICTURE_2, MOVIE_RATING_2, MOVIE_GENRES_2);


    private static final UUID REVIEW_ID = UUID.randomUUID();
    private static final String REVIEW_TITLE = "Review Title";
    private static final String REVIEW_DESCRIPTION = "Review Description";
    private static final int REVIEW_RATING = 5;

    private static final UUID REVIEW_ID_2 = UUID.randomUUID();
    private static final String REVIEW_TITLE_2 = "Review Title 2";
    private static final String REVIEW_DESCRIPTION_2 = "Review Description 2";
    private static final int REVIEW_RATING_2 = 3;

    private static final UUID INVALID_ID = UUID.randomUUID();

    @BeforeEach
    public void setMockOutput() {

        lenient().when(userService.getUser(anyString())).thenAnswer((invocation) -> {
            if (invocation.getArgument(0).equals(USER_USERNAME)) {
                return Conversions.convertToModel(USER_DTO);
            }else {
                return null;
            }
        });

        lenient().when(movieService.getMovie(any(UUID.class))).thenAnswer((invocation) -> {
            UUID id = invocation.getArgument(0);
            if (id.equals(MOVIE_ID)) {
                return Conversions.convertToModel(MOVIE_DTO);
            } else if (id.equals(MOVIE_ID_2)) {
                return Conversions.convertToModel(MOVIE_DTO_2);
            } else {
                return null;
            }
        });

        lenient().when(reviewRepository.findById(any(UUID.class))).thenAnswer((invocation) -> {
            UUID id = invocation.getArgument(0);
            if (id.equals(REVIEW_ID)) {
                return Optional.of(new Review(REVIEW_ID, REVIEW_TITLE, REVIEW_DESCRIPTION, REVIEW_RATING,
                        userService.getUser(USER_USERNAME), movieService.getMovie(MOVIE_ID)));
            } else if (id.equals(REVIEW_ID_2)) {
                return Optional.of(new Review(REVIEW_ID_2, REVIEW_TITLE_2, REVIEW_DESCRIPTION_2, REVIEW_RATING_2,
                        userService.getUser(USER_USERNAME), movieService.getMovie(MOVIE_ID_2)));
            } else {
                return Optional.empty();
            }
        });

        lenient().when(reviewRepository.findReviewByMovie(any(Movie.class))).thenAnswer((invocation) ->{
            UUID movieId = ((Movie) invocation.getArgument(0)).getId();
            if (movieId.equals(MOVIE_ID)) {
                return List.of(reviewRepository.findById(REVIEW_ID).get());
            } else if (movieId.equals(MOVIE_ID_2)) {
                return List.of(reviewRepository.findById(REVIEW_ID_2).get());
            } else {
                return List.of();
            }
        });

        lenient().when(reviewRepository.findReviewByUser(any(User.class))).thenAnswer((invocation) ->{
            String username = ((User) invocation.getArgument(0)).getUsername();
            if (username.equals(USER_USERNAME)) {
                return List.of(reviewRepository.findById(REVIEW_ID).get(), reviewRepository.findById(REVIEW_ID_2).get());
            } else {
                return List.of();
            }
        });

        lenient().when(reviewRepository.findAll()).thenAnswer((invocation) ->
                List.of(reviewRepository.findById(REVIEW_ID).get(), reviewRepository.findById(REVIEW_ID_2).get()));

        lenient().when(reviewRepository.existsById(any(UUID.class))).thenAnswer((invocation) ->
            invocation.getArgument(0).equals(REVIEW_ID) || invocation.getArgument(0).equals(REVIEW_ID_2)
        );

        lenient().when(reviewRepository.existsByUserAndMovie(any(User.class), any(Movie.class))).thenAnswer((invocation) -> {
            User user = invocation.getArgument(0);
            Movie movie = invocation.getArgument(1);
            return reviewRepository.findReviewByUser(user).stream().anyMatch(review
                    -> review.getMovie().getId().equals(movie.getId()));
        });

        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> invocation.getArgument(0);
        lenient().when(reviewRepository.save(any(Review.class))).thenAnswer(returnParameterAsAnswer);
        lenient().doNothing().when(reviewRepository).delete(any(Review.class));
    }

    @Test
    public void createReviewSuccess(){
        ReviewDTO reviewDTO = new ReviewDTO(null, REVIEW_TITLE, REVIEW_DESCRIPTION,
                REVIEW_RATING, USER2_DTO, MOVIE_DTO);
        try{
            Review review = reviewService.createOrEditReview(reviewDTO);
            assertEquals(reviewDTO.getTitle(), review.getTitle());
            assertEquals(reviewDTO.getDescription(), review.getDescription());
            assertEquals(reviewDTO.getRating(), review.getRating());
            assertEquals(reviewDTO.getUser().getUsername(), review.getUser().getUsername());
            assertEquals(reviewDTO.getMovie().getId(), review.getMovie().getId());
        }catch(IllegalArgumentException e){
            fail("Should not throw exception");
        }
    }

    @Test
    public void createReviewAlreadyExists(){
        ReviewDTO reviewDTO = new ReviewDTO(null, REVIEW_TITLE, REVIEW_DESCRIPTION,
                REVIEW_RATING, USER_DTO, MOVIE_DTO);
        try{
            reviewService.createOrEditReview(reviewDTO);
            fail("Should throw exception");
        }catch(IllegalArgumentException e){
            assertEquals("Review for user " + reviewDTO.getUser() + " and movie " +
                    reviewDTO.getMovie() + " already exists", e.getMessage());
        }
    }

    @Test
    public void createReviewBlankTitle(){
        ReviewDTO reviewDTO = new ReviewDTO(null, "", REVIEW_DESCRIPTION,
                REVIEW_RATING, USER2_DTO, MOVIE_DTO);
        try{
            reviewService.createOrEditReview(reviewDTO);
            fail("Should throw exception");
        }catch(IllegalArgumentException e){
            assertEquals("title must not be blank", e.getMessage());
        }
    }

    @Test
    public void createReviewNullTitle(){
        ReviewDTO reviewDTO = new ReviewDTO(null, null, REVIEW_DESCRIPTION,
                REVIEW_RATING, USER2_DTO, MOVIE_DTO);
        try{
            reviewService.createOrEditReview(reviewDTO);
            fail("Should throw exception");
        }catch(IllegalArgumentException e){
            assertEquals("title must not be blank", e.getMessage());
        }
    }

    @Test
    public void createReviewBlankDescription(){
        ReviewDTO reviewDTO = new ReviewDTO(null, REVIEW_TITLE, "",
                REVIEW_RATING, USER2_DTO, MOVIE_DTO);
        try{
            reviewService.createOrEditReview(reviewDTO);
            fail("Should throw exception");
        }catch(IllegalArgumentException e){
            assertEquals("description must not be blank", e.getMessage());
        }
    }

    @Test
    public void createReviewNullDescription(){
        ReviewDTO reviewDTO = new ReviewDTO(null, REVIEW_TITLE, null,
                REVIEW_RATING, USER2_DTO, MOVIE_DTO);
        try{
            reviewService.createOrEditReview(reviewDTO);
            fail("Should throw exception");
        }catch(IllegalArgumentException e){
            assertEquals("description must not be blank", e.getMessage());
        }
    }

    @Test
    public void createReviewNullInvalidRatingMin(){
        ReviewDTO reviewDTO = new ReviewDTO(null, REVIEW_TITLE, REVIEW_DESCRIPTION,
                0, USER2_DTO, MOVIE_DTO);
        try{
            reviewService.createOrEditReview(reviewDTO);
            fail("Should throw exception");
        }catch(IllegalArgumentException e){
            assertEquals("rating must be greater than or equal to 1", e.getMessage());
        }
    }

    @Test
    public void createReviewNullInvalidRatingMax(){
        ReviewDTO reviewDTO = new ReviewDTO(null, REVIEW_TITLE, REVIEW_DESCRIPTION,
                6, USER2_DTO, MOVIE_DTO);
        try{
            reviewService.createOrEditReview(reviewDTO);
            fail("Should throw exception");
        }catch(IllegalArgumentException e){
            assertEquals("rating must be less than or equal to 5", e.getMessage());
        }
    }

    @Test
    public void createReviewNullUser(){
        ReviewDTO reviewDTO = new ReviewDTO(null, REVIEW_TITLE, REVIEW_DESCRIPTION,
                REVIEW_RATING, null, MOVIE_DTO);
        try{
            reviewService.createOrEditReview(reviewDTO);
            fail("Should throw exception");
        }catch(IllegalArgumentException e){
            assertEquals("user must not be null", e.getMessage());
        }
    }

    @Test
    public void createReviewNullMovie(){
        ReviewDTO reviewDTO = new ReviewDTO(null, REVIEW_TITLE, REVIEW_DESCRIPTION,
                REVIEW_RATING, USER2_DTO, null);
        try{
            reviewService.createOrEditReview(reviewDTO);
            fail("Should throw exception");
        }catch(IllegalArgumentException e){
            assertEquals("movie must not be null", e.getMessage());
        }
    }

    @Test
    public void editReviewSuccess(){
        ReviewDTO reviewDTO = new ReviewDTO(REVIEW_ID, REVIEW_TITLE_2, REVIEW_DESCRIPTION_2,
                REVIEW_RATING_2, USER_DTO, MOVIE_DTO);
        try{
            Review review = reviewService.createOrEditReview(reviewDTO);
            assertEquals(reviewDTO.getTitle(), review.getTitle());
            assertEquals(reviewDTO.getDescription(), review.getDescription());
            assertEquals(reviewDTO.getRating(), review.getRating());
            assertEquals(reviewDTO.getUser().getUsername(), review.getUser().getUsername());
            assertEquals(reviewDTO.getMovie().getId(), review.getMovie().getId());
        }catch(IllegalArgumentException e){
            fail("Should not throw exception");
        }
    }

    @Test
    public void editReviewNotFound(){
        ReviewDTO reviewDTO = new ReviewDTO(INVALID_ID, REVIEW_TITLE_2, REVIEW_DESCRIPTION_2,
                REVIEW_RATING_2, USER_DTO, MOVIE_DTO);
        try{
            reviewService.createOrEditReview(reviewDTO);
        }catch(IllegalArgumentException e){
            assertEquals("Review " + INVALID_ID + " not found", e.getMessage());
        }
    }

    @Test
    public void getReviewSuccess(){
        try{
            Review review = reviewService.getReview(REVIEW_ID);
            assertEquals(REVIEW_ID, review.getId());
            assertEquals(REVIEW_TITLE, review.getTitle());
            assertEquals(REVIEW_DESCRIPTION, review.getDescription());
            assertEquals(REVIEW_RATING, review.getRating());
            assertEquals(USER_USERNAME, review.getUser().getUsername());
            assertEquals(MOVIE_ID, review.getMovie().getId());
        }catch(IllegalArgumentException e){
            fail("Should not throw exception");
        }
    }

    @Test
    public void getReviewNotFound(){
        try{
            reviewService.getReview(INVALID_ID);
        }catch(IllegalArgumentException e){
            assertEquals("Review " + INVALID_ID + " not found", e.getMessage());
        }
    }

    @Test
    public void getReviewsSuccess(){
        try{
            List<Review> reviews = reviewService.getReviews();
            assertEquals(2, reviews.size());
            assertEquals(REVIEW_ID, reviews.get(0).getId());
            assertEquals(REVIEW_TITLE, reviews.get(0).getTitle());
            assertEquals(REVIEW_DESCRIPTION, reviews.get(0).getDescription());
            assertEquals(REVIEW_RATING, reviews.get(0).getRating());
            assertEquals(USER_USERNAME, reviews.get(0).getUser().getUsername());
            assertEquals(MOVIE_ID, reviews.get(0).getMovie().getId());
            assertEquals(REVIEW_ID_2, reviews.get(1).getId());
            assertEquals(REVIEW_TITLE_2, reviews.get(1).getTitle());
            assertEquals(REVIEW_DESCRIPTION_2, reviews.get(1).getDescription());
            assertEquals(REVIEW_RATING_2, reviews.get(1).getRating());
            assertEquals(USER_USERNAME, reviews.get(1).getUser().getUsername());
            assertEquals(MOVIE_ID_2, reviews.get(1).getMovie().getId());
        }catch(IllegalArgumentException e){
            fail("Should not throw exception");
        }
    }

    @Test
    public void getReviewsByUserSuccess(){
        try{
            List<Review> reviews = reviewService.getReviewsByUser(USER_USERNAME);
            assertEquals(2, reviews.size());
            assertEquals(REVIEW_ID, reviews.get(0).getId());
            assertEquals(REVIEW_TITLE, reviews.get(0).getTitle());
            assertEquals(REVIEW_DESCRIPTION, reviews.get(0).getDescription());
            assertEquals(REVIEW_RATING, reviews.get(0).getRating());
            assertEquals(USER_USERNAME, reviews.get(0).getUser().getUsername());
            assertEquals(MOVIE_ID, reviews.get(0).getMovie().getId());
            assertEquals(REVIEW_ID_2, reviews.get(1).getId());
            assertEquals(REVIEW_TITLE_2, reviews.get(1).getTitle());
            assertEquals(REVIEW_DESCRIPTION_2, reviews.get(1).getDescription());
            assertEquals(REVIEW_RATING_2, reviews.get(1).getRating());
            assertEquals(USER_USERNAME, reviews.get(1).getUser().getUsername());
            assertEquals(MOVIE_ID_2, reviews.get(1).getMovie().getId());
        }catch(IllegalArgumentException e){
            fail("Should not throw exception");
        }
    }

    @Test
    public void getReviewsByMovieSuccess(){
        try{
            List<Review> reviews = reviewService.getReviewsByMovie(MOVIE_ID);
            assertEquals(1, reviews.size());
            assertEquals(REVIEW_ID, reviews.get(0).getId());
            assertEquals(REVIEW_TITLE, reviews.get(0).getTitle());
            assertEquals(REVIEW_DESCRIPTION, reviews.get(0).getDescription());
            assertEquals(REVIEW_RATING, reviews.get(0).getRating());
            assertEquals(USER_USERNAME, reviews.get(0).getUser().getUsername());
            assertEquals(MOVIE_ID, reviews.get(0).getMovie().getId());
        }catch(IllegalArgumentException e){
            fail("Should not throw exception");
        }
    }

    @Test
    public void deleteReviewSuccess(){
        try{
            reviewService.deleteReview(REVIEW_ID);
        }catch(IllegalArgumentException e){
            fail("Should not throw exception");
        }
    }

    @Test
    public void deleteReviewNotFound(){
        try{
            reviewService.deleteReview(INVALID_ID);
        }catch(IllegalArgumentException e){
            assertEquals("Review " + INVALID_ID + " not found", e.getMessage());
        }
    }

}
