package olive.oliveyoung.member.review.controller;

import lombok.RequiredArgsConstructor;
import olive.oliveyoung.member.review.dto.ReviewRequestDto;
import olive.oliveyoung.member.review.dto.ReviewResponseDto;
import olive.oliveyoung.member.review.dto.ReviewUpdateDto;
import olive.oliveyoung.member.review.service.ReviewService;
import olive.oliveyoung.member.user.common.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;

    // 📌 1. 리뷰 등록 API
    @PostMapping("/products/{productId}/reviews")
    public ResponseEntity<?> createReview(@PathVariable Long productId,
                                          @RequestBody ReviewRequestDto dto,
                                          @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long reviewId = reviewService.createReview(productId, dto, userDetails.getUsername()); // 또는 getUserId()
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of("success", true, "data", Map.of("reviewId", reviewId))
        );
    }

    // 📌 2. 리뷰 수정 API
    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<?> updateReview(@PathVariable Long reviewId,
                                          @RequestBody ReviewUpdateDto dto,
                                          @AuthenticationPrincipal CustomUserDetails userDetails) {
        reviewService.updateReview(reviewId, dto, userDetails.getUsername());
        return ResponseEntity.ok(new ApiResponse<>(true, "리뷰가 수정되었습니다."));
    }

    // 📌 3. 리뷰 삭제 API
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId,
                                          @AuthenticationPrincipal CustomUserDetails userDetails) {
        reviewService.deleteReview(reviewId, userDetails.getUsername());
        return ResponseEntity.ok(new ApiResponse<>(true, "리뷰가 삭제되었습니다."));
    }

    // 📌 4. 상품별 리뷰 목록 조회
    @GetMapping("/products/{productId}/reviews")
    public ResponseEntity<?> getReviewsByProduct(@PathVariable Long productId) {
        List<ReviewResponseDto> reviews = reviewService.getReviewsByProduct(productId);
        return ResponseEntity.ok(new ApiResponse<>(true, reviews));
    }

    // 📌 5. 유저별 리뷰 목록 조회
    @GetMapping("/users/{userId}/reviews")
    public ResponseEntity<?> getReviewsByUser(@PathVariable String userId) {
        List<ReviewResponseDto> reviews = reviewService.getReviewsByUser(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, reviews));
    }

    // 📌 6. 평균 평점 조회
    @GetMapping("/products/{productId}/reviews/average-rating")
    public ResponseEntity<?> getAverageRating(@PathVariable Long productId) {
        double averageRating = reviewService.getAverageRating(productId);
        return ResponseEntity.ok(new ApiResponse<>(true, new AverageRatingResponse(averageRating)));
    }

    // 응답용 내부 DTO
    static class IdResponse {
        private final Long reviewId;
        public IdResponse(Long reviewId) {
            this.reviewId = reviewId;
        }
        public Long getReviewId() { return reviewId; }
    }

    static class AverageRatingResponse {
        private final double averageRating;
        public AverageRatingResponse(double averageRating) {
            this.averageRating = averageRating;
        }
        public double getAverageRating() { return averageRating; }
    }

    static class ApiResponse<T> {
        private final boolean success;
        private final T data;
        public ApiResponse(boolean success, T data) {
            this.success = success;
            this.data = data;
        }
        public boolean isSuccess() { return success; }
        public T getData() { return data; }
    }
}

