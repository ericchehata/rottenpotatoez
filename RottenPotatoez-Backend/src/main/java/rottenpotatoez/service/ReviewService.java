package rottenpotatoez.service;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class ReviewService {

    private ReviewRepository reviewRepository;
    private UserService userService;
    private MovieService movieService;

    public Review createOrEditReview(ReviewDTO reviewDTO){
        if(reviewDTO.getId() == null && reviewRepository.findReviewByUserAndMovie(
                        Conversions.convertToModel(reviewDTO.getUser()),
                        Conversions.convertToModel(reviewDTO.getMovie())).isPresent()){
            throw new IllegalArgumentException("Review for user " + reviewDTO.getUser() + " and movie " + reviewDTO.getMovie() + " already exists");
        }
        Review review = Conversions.convertToModel(reviewDTO);
        reviewRepository.save(review);
        return review;
    }

    public Review getReview(UUID id){
        return reviewRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Review "+ id +" not found"));
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
        reviewRepository.deleteById(id);
    }
}
