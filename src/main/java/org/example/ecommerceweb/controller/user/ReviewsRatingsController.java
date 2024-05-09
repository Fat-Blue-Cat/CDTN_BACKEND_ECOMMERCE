package org.example.ecommerceweb.controller.user;


import lombok.RequiredArgsConstructor;
import org.example.ecommerceweb.domains.User;
import org.example.ecommerceweb.dto.req.ReviewsRatingsReq;
import org.example.ecommerceweb.service.AuthService;
import org.example.ecommerceweb.service.ReviewsRatingsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user/reviews-ratings")
public class ReviewsRatingsController {
    private final ReviewsRatingsService reviewsRatingsService;
    private final AuthService authService;

    @PostMapping("/create")
    public ResponseEntity<?> createReviewRating(@RequestBody ReviewsRatingsReq reviewsRatingsReq, @RequestHeader(value = "Authorization") String jwt) {
        try {
            User user = authService.findUserByJwt(jwt);
            return new ResponseEntity<>(reviewsRatingsService.save(reviewsRatingsReq, user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateReviewRating(@PathVariable Long id, @RequestBody ReviewsRatingsReq reviewsRatingsReq, @RequestHeader(value = "Authorization") String jwt) {
        try {
            User user = authService.findUserByJwt(jwt);
            return new ResponseEntity<>(reviewsRatingsService.update(reviewsRatingsReq, user,id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReviewRating(@PathVariable Long id) {
        try {
            reviewsRatingsService.delete(id);
            return new ResponseEntity<>("Review deleted successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReviewRating(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(reviewsRatingsService.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

        @GetMapping("/get-all/{productId}")
    public ResponseEntity<?> getAllReviewRatings(@PathVariable Long productId) {
        try {
            return new ResponseEntity<>(reviewsRatingsService.findAllByProductId(productId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
