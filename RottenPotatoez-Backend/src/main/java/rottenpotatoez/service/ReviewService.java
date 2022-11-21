package rottenpotatoez.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rottenpotatoez.dao.ReviewRepository;
import rottenpotatoez.dto.ReviewDTO;
import rottenpotatoez.model.Review;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ReviewService {

    private ReviewRepository reviewRepository;
    private UserService userService;
    private MovieService movieService;

    public Review createReview(ReviewDTO reviewDTO){
        Review review = new Review();
        review.setTitle(reviewDTO.getTitle());
        review.setRating(reviewDTO.getRating());
        review.setDescription(reviewDTO.getDescription());
        review.setUser(userService.getUser(reviewDTO.getUser().getUsername()));
        review.setMovie(movieService.getMovie(reviewDTO.getMovie().getId()));
        return reviewRepository.save(review);
    }

    public Review editReview(ReviewDTO reviewDTO){
        Review review = getReview(reviewDTO.getId());
        if(reviewDTO.getTitle() != null && !reviewDTO.getTitle().isEmpty()){
            review.setTitle(reviewDTO.getTitle());
        }
        if(reviewDTO.getRating() > 0 && reviewDTO.getRating() <= 5){
            review.setRating(reviewDTO.getRating());
        }
        if(reviewDTO.getDescription() != null && !reviewDTO.getDescription().isEmpty()){
            review.setDescription(reviewDTO.getDescription());
        }
        return reviewRepository.save(review);
    }

    public Review getReview(UUID id){
        return reviewRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Review "+ id +" not found"));
    }

    public List<Review> getReviews(){
        return reviewRepository.findAll();
    }

    public void deleteReview(UUID id){
        reviewRepository.deleteById(id);
    }
}
