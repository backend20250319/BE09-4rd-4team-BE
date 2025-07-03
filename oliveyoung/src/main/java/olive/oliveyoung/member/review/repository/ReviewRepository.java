package olive.oliveyoung.member.review.repository;

import olive.oliveyoung.member.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProductId(Long productId);
    List<Review> findByUserId(Long userId);
    int countByUserId(Long userId);
    Optional<Review> findByUserIdAndUserReviewNumber(Long userId, Integer userReviewNumber); // 추가
}
