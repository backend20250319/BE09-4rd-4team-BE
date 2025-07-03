package olive.oliveyoung.member.review.service;

import olive.oliveyoung.member.review.dto.ReviewRequestDto;
import olive.oliveyoung.member.review.dto.ReviewResponseDto;
import olive.oliveyoung.member.review.dto.ReviewUpdateDto;

import java.util.List;

public interface ReviewService {
    ReviewResponseDto createReview(ReviewRequestDto dto);

    String deleteReviewByUserAndNumber(Long userId, Integer userReviewNumber);

    String updateReviewByUserAndNumber(Long userId, Integer userReviewNumber, ReviewUpdateDto dto);

    List<ReviewResponseDto> getReviewsByProduct(Long productId);

    List<ReviewResponseDto> getReviewsByUser(Long userId);
}
