package olive.oliveyoung.member.review.controller;

import lombok.RequiredArgsConstructor;
import olive.oliveyoung.member.review.dto.ReviewRequestDto;
import olive.oliveyoung.member.review.dto.ReviewResponseDto;
import olive.oliveyoung.member.review.dto.ReviewUpdateDto;
import olive.oliveyoung.member.review.service.ReviewService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping // 리뷰 작성
    public ResponseEntity<ReviewResponseDto> create(@RequestBody ReviewRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.createReview(dto));
    }

    @GetMapping("/product/{productId}") // 상품 별 리뷰
    public ResponseEntity<List<ReviewResponseDto>> getByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(reviewService.getReviewsByProduct(productId));
    }

    @GetMapping("/user/{userId}") // 사용자 별 리뷰
    public ResponseEntity<List<ReviewResponseDto>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUser(userId));
    }

    @PutMapping("/user/{userId}/review/{userReviewNumber}")
    public ResponseEntity<String> updateByUserReviewNumber(
            @PathVariable Long userId,
            @PathVariable Integer userReviewNumber,
            @RequestBody ReviewUpdateDto dto
    ) {
        String message = reviewService.updateReviewByUserAndNumber(userId, userReviewNumber, dto);
        return ResponseEntity.ok(message); // 메시지 반환
    }

    @DeleteMapping("/user/{userId}/review/{userReviewNumber}")
    public ResponseEntity<String> deleteByUserReviewNumber(
            @PathVariable Long userId,
            @PathVariable Integer userReviewNumber
    ) {
        String message = reviewService.deleteReviewByUserAndNumber(userId, userReviewNumber);
        return ResponseEntity.ok(message); // 메시지 반환
    }

}
