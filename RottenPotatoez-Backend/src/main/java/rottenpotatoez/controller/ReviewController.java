package rottenpotatoez.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rottenpotatoez.dto.ReviewDTO;
import rottenpotatoez.service.ReviewService;
import rottenpotatoez.utils.Conversions;

import java.util.UUID;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody ReviewDTO reviewDTO) {
        try {
            return ResponseEntity.ok(Conversions.convertToDTO(reviewService.createOrEditReview(reviewDTO)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PatchMapping
    public ResponseEntity<?> editReview(@RequestBody ReviewDTO reviewDTO) {
        try {
            return ResponseEntity.ok(Conversions.convertToDTO(reviewService.createOrEditReview(reviewDTO)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReview(@PathVariable UUID id){
        try{
            return ResponseEntity.ok(Conversions.convertToDTO(reviewService.getReview(id)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getReviews(){
        try{
            return ResponseEntity.ok(reviewService.getReviews()
                    .stream()
                    .map(Conversions::convertToDTO)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<?> getReviewsByMovie(@PathVariable UUID movieId){
        try{
            return ResponseEntity.ok(reviewService.getReviewsByMovie(movieId)
                    .stream()
                    .map(Conversions::convertToDTO)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getReviewsByUser(@PathVariable String username){
        try{
            return ResponseEntity.ok(reviewService.getReviewsByUser(username)
                    .stream()
                    .map(Conversions::convertToDTO)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable UUID id){
        try{
            reviewService.deleteReview(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
