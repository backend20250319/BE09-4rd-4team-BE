package olive.oliveyoung.member.review.service;

import olive.oliveyoung.member.review.dto.ReviewRequestDto;
import olive.oliveyoung.member.review.dto.ReviewResponseDto;
import olive.oliveyoung.member.review.dto.ReviewUpdateDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {
    Long createReview(Long productId, ReviewRequestDto dto, String userId);
    String updateReview(Long reviewId, ReviewUpdateDto dto, String userId);
    String deleteReview(Long reviewId, String userId);
    List<ReviewResponseDto> getReviewsByProduct(Long productId);
    List<ReviewResponseDto> getReviewsByUser(String userId);
    double getAverageRating(Long productId);
}

