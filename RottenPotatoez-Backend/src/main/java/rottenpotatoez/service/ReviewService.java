package rottenpotatoez.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rottenpotatoez.dao.ReviewRepository;
import rottenpotatoez.dto.ReviewDTO;
import rottenpotatoez.model.Review;
import rottenpotatoez.utils.Conversions;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ReviewService {

    private ReviewRepository reviewRepository;
    private UserService userService;
    private MovieService movieService;

    public Review createOrEditReview(ReviewDTO reviewDTO){
        Review review = Conversions.convertToModel(reviewDTO);
        reviewRepository.save(review);
        return review;
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
