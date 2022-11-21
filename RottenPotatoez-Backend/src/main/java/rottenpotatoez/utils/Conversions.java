package rottenpotatoez.utils;

import rottenpotatoez.dto.MovieDTO;
import rottenpotatoez.dto.ReviewDTO;
import rottenpotatoez.dto.UserDTO;
import rottenpotatoez.model.*;

import java.util.UUID;
import java.util.stream.Collectors;

public class Conversions {

    public static User convertToModel(UserDTO user) {
        return new User(user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getDateOfBirth(), user.getEmail(), user.getPicture(), user.isAdmin());
    }

    public static Movie convertToModel(MovieDTO movie){
        return new Movie(movie.getId() != null ? movie.getId() : UUID.randomUUID(), movie.getTitle(), movie.getDescription(),
                movie.getDuration(), movie.getReleaseDate(), movie.getPicture(), Rating.valueOf(movie.getRating()),
                movie.getGenres().stream().map(genre -> genre.equals("True story") ? Genre.True_Story : Genre.valueOf(genre)).collect(Collectors.toList()));
    }

    public static Review convertToModel(ReviewDTO review){
        return new Review(review.getId() != null ? review.getId() : UUID.randomUUID(), review.getTitle(), review.getRating(), review.getDescription(), convertToModel(review.getUser()), convertToModel(review.getMovie()));
    }

    public static UserDTO convertToDTO(User user){
        return new UserDTO(user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getDateOfBirth(), user.getEmail(), user.getPicture(), user.isAdmin());
    }

    public static MovieDTO convertToDTO(Movie movie){
        return new MovieDTO(movie.getId(), movie.getTitle(), movie.getDescription(),
                movie.getDuration(), movie.getReleaseDate(), movie.getPicture(), movie.getRating().toString(),
                movie.getGenres().stream().map(genre -> genre.equals(Genre.True_Story) ? "True story" : genre.toString()).collect(Collectors.toList()));
    }

    public static ReviewDTO convertToDTO(Review review){
        return new ReviewDTO(review.getId(), review.getTitle(), review.getRating(), review.getDescription(), convertToDTO(review.getUser()), convertToDTO(review.getMovie()));
    }
}
