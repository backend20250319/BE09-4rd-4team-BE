//package olive.oliveyoung.member.product.repository;
//
//import olive.oliveyoung.member.review.entity.Review;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//public interface ReviewRepository extends JpaRepository<Review, Long> {
//
//    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.product.productId = :productId")
//    Float findAverageRatingByProductId(@Param("productId") Long productId);
//
//    @Query("SELECT COUNT(r) FROM Review r WHERE r.product.productId = :productId")
//    Long findReviewCountByProductId(@Param("productId") Long productId);
//
//}