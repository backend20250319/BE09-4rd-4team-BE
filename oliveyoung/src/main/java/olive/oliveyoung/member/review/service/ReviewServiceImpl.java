package olive.oliveyoung.member.review.service;

import lombok.RequiredArgsConstructor;
import olive.oliveyoung.member.review.dto.ReviewRequestDto;
import olive.oliveyoung.member.review.dto.ReviewResponseDto;
import olive.oliveyoung.member.review.dto.ReviewUpdateDto;
import olive.oliveyoung.member.review.entity.Review;
import olive.oliveyoung.member.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public ReviewResponseDto createReview(ReviewRequestDto dto) {
        int reviewCount = reviewRepository.countByUserId(dto.getUserId());
        int userReviewNumber = reviewCount + 1;

        Review review = Review.builder()
                .userId(dto.getUserId())
                .productId(dto.getProductId())
                .rating(dto.getRating())
                .content(dto.getContent())
                .userReviewNumber(userReviewNumber)
                .build();

        return toResponseDto(reviewRepository.save(review));
    }

    @Override
    public String deleteReviewByUserAndNumber(Long userId, Integer userReviewNumber) {
        Review review = reviewRepository.findByUserIdAndUserReviewNumber(userId, userReviewNumber)
                .orElseThrow(() -> new RuntimeException("해당 사용자의 리뷰를 찾을 수 없습니다."));

        Long productId = review.getProductId(); // 상품 번호 추출
        reviewRepository.delete(review);

        return productId + "번 상품의 리뷰가 삭제되었습니다.";
    }

    @Override
    public String updateReviewByUserAndNumber(Long userId, Integer userReviewNumber, ReviewUpdateDto dto) {
        Review review = reviewRepository.findByUserIdAndUserReviewNumber(userId, userReviewNumber)
                .orElseThrow(() -> new RuntimeException("해당 사용자의 리뷰를 찾을 수 없습니다."));

        review.setRating(dto.getRating());
        review.setContent(dto.getContent());

        reviewRepository.save(review);

        return review.getProductId() + "번 상품의 리뷰가 수정되었습니다.";
    }


    @Override
    public List<ReviewResponseDto> getReviewsByProduct(Long productId) {
        return reviewRepository.findByProductId(productId).stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponseDto> getReviewsByUser(Long userId) {
        return reviewRepository.findByUserId(userId).stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    private ReviewResponseDto toResponseDto(Review review) {
        return ReviewResponseDto.builder()
                .reviewId(review.getReviewId())
                .userId(review.getUserId())
                .productId(review.getProductId())
                .rating(review.getRating())
                .content(review.getContent())
                .userReviewNumber(review.getUserReviewNumber())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
