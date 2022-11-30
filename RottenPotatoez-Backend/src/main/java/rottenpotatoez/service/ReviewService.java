package rottenpotatoez.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rottenpotatoez.dao.ReviewRepository;
import rottenpotatoez.dto.ReviewDTO;
import rottenpotatoez.model.Review;
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
public class ReviewService {

    private ReviewRepository reviewRepository;
    private UserService userService;
    private MovieService movieService;

    public Review createOrEditReview(ReviewDTO reviewDTO){

        Review review = validateReview(reviewDTO);
        reviewRepository.save(review);
        return review;
    }

    public Review getReview(UUID id){
        return reviewRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Review "+ id +" not found"));
    }

    public List<Review> getReviewsByMovie(UUID movieId){
        return reviewRepository.findReviewByMovie(movieService.getMovie(movieId));
    }

    public List<Review> getReviewsByUser(String username){
        return reviewRepository.findReviewByUser(userService.getUser(username));
    }

    public List<Review> getReviews(){
        return reviewRepository.findAll();
    }

    public void deleteReview(UUID id){
        Review review = getReview(id);
        reviewRepository.delete(review);
    }

    private Review validateReview(ReviewDTO reviewDTO){

        if(reviewDTO.getUser() == null){
            throw new IllegalArgumentException("user must not be null");
        }

        if(reviewDTO.getMovie() == null){
            throw new IllegalArgumentException("movie must not be null");
        }

        if(reviewDTO.getId() == null && reviewRepository.existsByUserAndMovie(
                Conversions.convertToModel(reviewDTO.getUser()),
                Conversions.convertToModel(reviewDTO.getMovie()))){
            throw new IllegalArgumentException("Review for user " + reviewDTO.getUser() +
                    " and movie " + reviewDTO.getMovie() + " already exists");
        }

        if(reviewDTO.getId()!=null && !reviewRepository.existsById(reviewDTO.getId())){
            throw new IllegalArgumentException("Review " + reviewDTO.getId() + " not found");
        }

        Review review = Conversions.convertToModel(reviewDTO);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Review>> violations = validator.validate(review);
        if(violations.size() > 0){
            ConstraintViolation<Review> violation = violations.iterator().next();
            throw new IllegalArgumentException(violation.getPropertyPath()+" "+violation.getMessage());
        }

        return review;
    }
}
