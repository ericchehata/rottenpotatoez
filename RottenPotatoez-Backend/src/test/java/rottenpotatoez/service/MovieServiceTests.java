package rottenpotatoez.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import rottenpotatoez.dao.MovieRepository;
import rottenpotatoez.dto.MovieDTO;
import rottenpotatoez.model.Genre;
import rottenpotatoez.model.Movie;
import rottenpotatoez.utils.Conversions;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTests {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    private static final UUID MOVIE_ID = UUID.randomUUID();
    private static final String MOVIE_TITLE = "Movie Title";
    private static final int MOVIE_DURATION = 120;
    private static final LocalDate MOVIE_RELEASE_DATE = LocalDate.now();
    private static final String MOVIE_PICTURE = "Movie Picture";
    private static final String MOVIE_RATING = "G";
    private static final List<String> MOVIE_GENRES = List.of("Action", "Adventure");
    private static final MovieDTO MOVIE1 = new MovieDTO(MOVIE_ID, MOVIE_TITLE, MOVIE_DURATION,
            MOVIE_RELEASE_DATE, MOVIE_PICTURE, MOVIE_RATING, MOVIE_GENRES);

    private static final UUID MOVIE_ID_2 = UUID.randomUUID();
    private static final String MOVIE_TITLE_2 = "Movie Title 2";
    private static final int MOVIE_DURATION_2 = 120;
    private static final LocalDate MOVIE_RELEASE_DATE_2 = LocalDate.now();
    private static final String MOVIE_PICTURE_2 = "Movie Picture 2";
    private static final String MOVIE_RATING_2 = "R";
    private static final List<String> MOVIE_GENRES_2 = List.of("Comedy");
    private static final MovieDTO MOVIE2 = new MovieDTO(MOVIE_ID_2, MOVIE_TITLE_2, MOVIE_DURATION_2,
            MOVIE_RELEASE_DATE_2, MOVIE_PICTURE_2, MOVIE_RATING_2, MOVIE_GENRES_2);

    private static final String VALID_MOVIE_TITLE = "Valid Movie Title";
    private static final UUID INVALID_ID = UUID.randomUUID();
    private static final String INVALID_TITLE = "Invalid Movie Title";

    @BeforeEach
    public void setMockOutput() {
        lenient().when(movieRepository.findById(any(UUID.class))).thenAnswer((invocation) -> {
            UUID id = invocation.getArgument(0);
            if (id.equals(MOVIE_ID)) {
                return Optional.of(Conversions.convertToModel(MOVIE1));
            } else if (id.equals(MOVIE_ID_2)) {
                return Optional.of(Conversions.convertToModel(MOVIE2));
            } else {
                return Optional.empty();
            }
        });

        lenient().when(movieRepository.findMovieByTitle(anyString())).thenAnswer((invocation) -> {
            String title = invocation.getArgument(0);
            if (title.equals(MOVIE_TITLE)) {
                return movieRepository.findById(MOVIE_ID);
            } else if (title.equals(MOVIE_TITLE_2)) {
                return movieRepository.findById(MOVIE_ID_2);
            } else {
                return Optional.empty();
            }
        });

        lenient().when(movieRepository.findAll()).thenAnswer((invocation) ->
                List.of(movieRepository.findMovieByTitle(MOVIE_TITLE).get(),
                movieRepository.findMovieByTitle(MOVIE_TITLE_2).get()));

        lenient().when(movieRepository.existsByTitle(anyString())).thenAnswer((invocation) ->
                invocation.getArgument(0).equals(MOVIE_TITLE)
                        || invocation.getArgument(0).equals(MOVIE_TITLE_2)
        );

        lenient().when(movieRepository.existsById(any(UUID.class))).thenAnswer((invocation) ->
                invocation.getArgument(0).equals(MOVIE_ID)
                        || invocation.getArgument(0).equals(MOVIE_ID_2)
        );

        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> invocation.getArgument(0);
        lenient().when(movieRepository.save(any(Movie.class))).thenAnswer(returnParameterAsAnswer);
        lenient().doNothing().when(movieRepository).delete(any(Movie.class));
    }

    @Test
    public void createMovieSuccess(){
        MovieDTO movieDTO = new MovieDTO(null, VALID_MOVIE_TITLE, MOVIE_DURATION,
                MOVIE_RELEASE_DATE, MOVIE_PICTURE, MOVIE_RATING, MOVIE_GENRES);
        try{
            Movie movie = movieService.createOrEditMovie(movieDTO);
            assertEquals(movieDTO.getTitle(), movie.getTitle());
            assertEquals(movieDTO.getDuration(), movie.getDuration());
            assertEquals(movieDTO.getReleaseDate(), movie.getReleaseDate());
            assertEquals(movieDTO.getPicture(), movie.getPicture());
            assertEquals(movieDTO.getRating(), movie.getRating().toString());
            for(String genre : movieDTO.getGenres()){
                assertTrue(movie.getGenres().contains(Genre.valueOf(genre)));
            }
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }

    @Test
    public void createMovieTakenTitle(){
        MovieDTO movie = new MovieDTO(null, MOVIE_TITLE, MOVIE_DURATION,
                MOVIE_RELEASE_DATE, MOVIE_PICTURE, MOVIE_RATING, MOVIE_GENRES);
        try{
            movieService.createOrEditMovie(movie);
            fail("Should throw exception");
        } catch (Exception e) {
            assertEquals("Movie with title " + movie.getTitle() + " already exists", e.getMessage());
        }
    }

    @Test
    public void createMovieBlankTitle(){
        MovieDTO movie = new MovieDTO(null, "", MOVIE_DURATION,
                MOVIE_RELEASE_DATE, MOVIE_PICTURE, MOVIE_RATING, MOVIE_GENRES);
        try{
            movieService.createOrEditMovie(movie);
            fail("Should throw exception");
        } catch (Exception e) {
            assertEquals("title must not be blank", e.getMessage());
        }
    }

    @Test
    public void createMovieNullTitle(){
        MovieDTO movie = new MovieDTO(null, null, MOVIE_DURATION,
                MOVIE_RELEASE_DATE, MOVIE_PICTURE, MOVIE_RATING, MOVIE_GENRES);
        try{
            movieService.createOrEditMovie(movie);
            fail("Should throw exception");
        } catch (Exception e) {
            assertEquals("title must not be blank", e.getMessage());
        }
    }


    @Test
    public void createMovieNullReleaseDate(){
        MovieDTO movie = new MovieDTO(null, VALID_MOVIE_TITLE, MOVIE_DURATION,
                null, MOVIE_PICTURE, MOVIE_RATING, MOVIE_GENRES);
        try{
            movieService.createOrEditMovie(movie);
            fail("Should throw exception");
        } catch (Exception e) {
            assertEquals("releaseDate must not be null", e.getMessage());
        }
    }

    @Test
    public void createMovieBlankPicture(){
        MovieDTO movie = new MovieDTO(null, VALID_MOVIE_TITLE, MOVIE_DURATION,
                MOVIE_RELEASE_DATE, "", MOVIE_RATING, MOVIE_GENRES);
        try{
            movieService.createOrEditMovie(movie);
            fail("Should throw exception");
        } catch (Exception e) {
            assertEquals("picture must not be blank", e.getMessage());
        }
    }

    @Test
    public void createMovieNullPicture(){
        MovieDTO movie = new MovieDTO(null, VALID_MOVIE_TITLE, MOVIE_DURATION,
                MOVIE_RELEASE_DATE, null, MOVIE_RATING, MOVIE_GENRES);
        try{
            movieService.createOrEditMovie(movie);
            fail("Should throw exception");
        } catch (Exception e) {
            assertEquals("picture must not be blank", e.getMessage());
        }
    }

    @Test
    public void createMovieBlankRating(){
        MovieDTO movie = new MovieDTO(null, VALID_MOVIE_TITLE, MOVIE_DURATION,
                null, MOVIE_PICTURE, "", MOVIE_GENRES);
        try{
            movieService.createOrEditMovie(movie);
            fail("Should throw exception");
        } catch (Exception e) {
            assertEquals("rating must be one of the following: G, PG, PG-13, R, NC-17", e.getMessage());
        }
    }

    @Test
    public void createMovieNullRating(){
        MovieDTO movie = new MovieDTO(null, VALID_MOVIE_TITLE, MOVIE_DURATION,
                null, MOVIE_PICTURE, "", MOVIE_GENRES);
        try{
            movieService.createOrEditMovie(movie);
            fail("Should throw exception");
        } catch (Exception e) {
            assertEquals("rating must be one of the following: G, PG, PG-13, R, NC-17", e.getMessage());
        }
    }

    @Test
    public void createMovieInvalidRating(){
        MovieDTO movie = new MovieDTO(null, VALID_MOVIE_TITLE, MOVIE_DURATION,
                null, MOVIE_PICTURE, "Invalid Rating", MOVIE_GENRES);
        try{
            movieService.createOrEditMovie(movie);
            fail("Should throw exception");
        } catch (Exception e) {
            assertEquals("rating must be one of the following: G, PG, PG-13, R, NC-17", e.getMessage());
        }
    }

    @Test
    public void createMovieInvalidGenre(){
        MovieDTO movie = new MovieDTO(null, VALID_MOVIE_TITLE, MOVIE_DURATION,
                null, MOVIE_PICTURE, MOVIE_RATING, List.of("Invalid Genre"));
        try{
            movieService.createOrEditMovie(movie);
            fail("Should throw exception");
        } catch (Exception e) {
            assertEquals("genre must be one of the following: Action, Adventure, " +
                    "Animation, Comedy, Crime, Documentary, Drama, Family, Fantasy, History, Horror, Music," +
                    " Mystery, Romance, Science Fiction, TV Movie, Thriller, War, Western", e.getMessage());
        }
    }

    @Test
    public void createMovieEmptyGenres(){
        MovieDTO movie = new MovieDTO(null, VALID_MOVIE_TITLE, MOVIE_DURATION,
                MOVIE_RELEASE_DATE, MOVIE_PICTURE, MOVIE_RATING, List.of());
        try{
            movieService.createOrEditMovie(movie);
            fail("Should throw exception");
        } catch (Exception e) {
            assertEquals("genres must not be empty", e.getMessage());
        }
    }

    @Test
    public void editMovieSuccess(){
        MovieDTO movie = new MovieDTO(MOVIE_ID, VALID_MOVIE_TITLE, 130,
                LocalDate.of(1998,1,1), "new picture", "PG", List.of("Crime", "Drama"));
        try{
            Movie editedMovie = movieService.createOrEditMovie(movie);
            assertEquals(movie.getId(), editedMovie.getId());
            assertEquals(movie.getTitle(), editedMovie.getTitle());
            assertEquals(movie.getDuration(), editedMovie.getDuration());
            assertEquals(movie.getReleaseDate(), editedMovie.getReleaseDate());
            assertEquals(movie.getPicture(), editedMovie.getPicture());
            assertEquals(movie.getRating(), editedMovie.getRating().toString());
            for(String genre : movie.getGenres()){
                assertTrue(editedMovie.getGenres().contains(Genre.valueOf(genre)));
            }
        }catch (Exception e){
            fail("Should not throw exception");
        }
    }

    @Test
    public void editMovieSameTitleSuccess(){
        MovieDTO movie = new MovieDTO(MOVIE_ID, MOVIE_TITLE,130,
                LocalDate.of(1998,1,1), "new picture", "PG", List.of("Crime", "Drama"));
        try{
            Movie editedMovie = movieService.createOrEditMovie(movie);
            assertEquals(movie.getId(), editedMovie.getId());
            assertEquals(movie.getTitle(), editedMovie.getTitle());
            assertEquals(movie.getDuration(), editedMovie.getDuration());
            assertEquals(movie.getReleaseDate(), editedMovie.getReleaseDate());
            assertEquals(movie.getPicture(), editedMovie.getPicture());
            assertEquals(movie.getRating(), editedMovie.getRating().toString());
            for(String genre : movie.getGenres()){
                assertTrue(editedMovie.getGenres().contains(Genre.valueOf(genre)));
            }
        }catch (Exception e){
            fail("Should not throw exception");
        }
    }

    @Test
    public void editMovieTakenTitle(){
        MovieDTO movie = new MovieDTO(MOVIE_ID, MOVIE_TITLE_2,  130,
                LocalDate.of(1998,1,1), "new picture", "PG", List.of("Crime", "Drama"));
        try{
           movieService.createOrEditMovie(movie);
            fail("Should throw exception");
        }catch (Exception e){
            assertEquals("Movie with title " + movie.getTitle() + " already exists", e.getMessage());
        }
    }

    @Test
    public void editMovieNotFound(){
        MovieDTO movie = new MovieDTO(INVALID_ID, MOVIE_TITLE_2,  130,
                LocalDate.of(1998,1,1), "new picture", "PG", List.of("Crime", "Drama"));
        try{
            movieService.createOrEditMovie(movie);
            fail("Should throw exception");
        }catch (Exception e){
            assertEquals("Movie " + INVALID_ID + " not found", e.getMessage());
        }
    }

    @Test
    public void getMovieSuccess(){
        try{
            Movie movie = movieService.getMovie(MOVIE_ID);
            assertEquals(MOVIE_ID, movie.getId());
            assertEquals(MOVIE_TITLE, movie.getTitle());
            assertEquals(MOVIE_DURATION, movie.getDuration());
            assertEquals(MOVIE_RELEASE_DATE, movie.getReleaseDate());
            assertEquals(MOVIE_PICTURE, movie.getPicture());
            assertEquals(MOVIE_RATING, movie.getRating().toString());
            for(String genre : MOVIE_GENRES){
                assertTrue(movie.getGenres().contains(Genre.valueOf(genre)));
            }
        }catch (Exception e){
            fail("Should not throw exception");
        }
    }

    @Test
    public void getMovieNotFound(){
        try{
            movieService.getMovie(INVALID_ID);
            fail("Should throw exception");
        }catch (Exception e){
            assertEquals("Movie " + INVALID_ID + " not found", e.getMessage());
        }
    }

    @Test
    public void getMovieByTitleSuccess(){
        try{
            Movie movie = movieService.getMovie(MOVIE_TITLE);
            assertEquals(MOVIE_ID, movie.getId());
            assertEquals(MOVIE_TITLE, movie.getTitle());
            assertEquals(MOVIE_DURATION, movie.getDuration());
            assertEquals(MOVIE_RELEASE_DATE, movie.getReleaseDate());
            assertEquals(MOVIE_PICTURE, movie.getPicture());
            assertEquals(MOVIE_RATING, movie.getRating().toString());
            for(String genre : MOVIE_GENRES){
                assertTrue(movie.getGenres().contains(Genre.valueOf(genre)));
            }
        }catch (Exception e){
            fail("Should not throw exception");
        }
    }

    @Test
    public void getMovieByTitleNotFound(){
        try{
            movieService.getMovie(INVALID_TITLE);
            fail("Should throw exception");
        }catch (Exception e){
            assertEquals("Movie " + INVALID_TITLE + " not found", e.getMessage());
        }
    }

    @Test
    public void getMoviesSuccess(){
        try{
            List<Movie> movies = movieService.getMovies();
            assertEquals(2, movies.size());
            assertEquals(MOVIE_ID, movies.get(0).getId());
            assertEquals(MOVIE_TITLE, movies.get(0).getTitle());
            assertEquals(MOVIE_DURATION, movies.get(0).getDuration());
            assertEquals(MOVIE_RELEASE_DATE, movies.get(0).getReleaseDate());
            assertEquals(MOVIE_PICTURE, movies.get(0).getPicture());
            assertEquals(MOVIE_RATING, movies.get(0).getRating().toString());
            for(String genre : MOVIE_GENRES){
                assertTrue(movies.get(0).getGenres().contains(Genre.valueOf(genre)));
            }
            assertEquals(MOVIE_ID_2, movies.get(1).getId());
            assertEquals(MOVIE_TITLE_2, movies.get(1).getTitle());
            assertEquals(MOVIE_DURATION_2, movies.get(1).getDuration());
            assertEquals(MOVIE_RELEASE_DATE_2, movies.get(1).getReleaseDate());
            assertEquals(MOVIE_PICTURE_2, movies.get(1).getPicture());
            assertEquals(MOVIE_RATING_2, movies.get(1).getRating().toString());
            for(String genre : MOVIE_GENRES_2){
                assertTrue(movies.get(1).getGenres().contains(Genre.valueOf(genre)));
            }
        } catch (IllegalArgumentException e){
            fail("Should not throw exception");
        }
    }

    @Test
    public void deleteMovieSuccess(){
        try {
            movieService.deleteMovie(MOVIE_ID);
        } catch (Exception e){
            fail("Should not throw exception");
        }
    }

    @Test
    public void deleteMovieNotFound(){
        try {
            movieService.deleteMovie(INVALID_ID);
            fail("Should throw exception");
        } catch (Exception e){
            assertEquals("Movie " + INVALID_ID + " not found", e.getMessage());
        }
    }

}
