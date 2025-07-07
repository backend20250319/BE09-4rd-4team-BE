package olive.oliveyoung.member.product.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime; // LocalDateTime 임포트
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "original_price", nullable = false)
    private Integer originalPrice;

    @Column(name = "discounted_price", nullable = false)
    private Integer discountedPrice;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column(name = "image_url")
    private String imageUrl;

    // ⭐️ 새로 추가할 필드: 상품 등록일시 (신상품 순 정렬 기준)
    @Column(name = "created_at", nullable = false, updatable = false) // 생성 시간은 변경되지 않음
    private LocalDateTime createdAt;

    // ⭐️ 새로 추가할 필드: 상품 판매량 (판매 순 정렬 기준)
    @Column(name = "sales_count", nullable = false)
    private Integer salesCount;

    @Column(name = "discount_rate") // 할인율
    private Integer discountRate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_badges",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "badge_id")
    )
    private List<Badges> badges = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brands brand;

    // Entity가 처음 저장될 때(Persist) 자동으로 호출되어 필드를 초기화합니다.
    @PrePersist // 엔티티가 저장되기 전에 실행
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (salesCount == null) {
            salesCount = 0; // 초기 판매량 0
        }
        // ⭐⭐⭐ 할인율 계산 로직 수정: Integer 타입에 맞게 반올림 및 캐스팅 ⭐⭐⭐
        if (originalPrice != null && originalPrice > 0) {
            double calculatedRate = ((double)(originalPrice - discountedPrice) / originalPrice) * 100.0;
            this.discountRate = (int) Math.round(calculatedRate); // Double -> Integer로 반올림하여 캐스팅
        } else {
            this.discountRate = 0; // Integer 타입에 맞게 0으로 초기화
        }
    }

    @PreUpdate // 엔티티가 업데이트되기 전에 실행
    protected void onUpdate() {
        // ⭐⭐⭐ 업데이트 시에도 할인율 재계산: Integer 타입에 맞게 반올림 및 캐스팅 ⭐⭐⭐
        if (originalPrice != null && originalPrice > 0) {
            double calculatedRate = ((double)(originalPrice - discountedPrice) / originalPrice) * 100.0;
            this.discountRate = (int) Math.round(calculatedRate); // Double -> Integer로 반올림하여 캐스팅
        } else {
            this.discountRate = 0; // Integer 타입에 맞게 0으로 초기화
        }
    }

}