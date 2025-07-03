package olive.oliveyoung.member.review.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
// 지금은 현재 바로 테스트 하기 위해서 따로 엔티티 구성해서 userid productid를 만들어 줬고
// 나중에 병합할 때 제외한 다음에 실제 데이터를 연결
@Entity
@Table(name = "review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Column(nullable = false)
    private Long userId; // 사용자 ID만 저장 (외래키 아님)

    @Column(nullable = false)
    private Long productId; // 상품 ID만 저장

    @Column(nullable = false)
    private Double rating;

    @Lob
    private String content;

    @Column(nullable = false)
    private Integer userReviewNumber; // 유저별 리뷰 번호 (1부터 증가)

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
