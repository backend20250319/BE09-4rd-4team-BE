package olive.oliveyoung.member.product.entity; // 올바른 패키지명으로 수정

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "products") // DB 테이블 이름은 "products" 입니다. ("product"가 아님)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // --- 새로 추가되는 가격 필드 ---
    @Column(name = "original_price", nullable = false) // DB 컬럼명: original_price
    private Integer originalPrice; // Java 필드명: originalPrice (Integer 또는 int)

    @Column(name = "discounted_price", nullable = false) // DB 컬럼명: discounted_price
    private Integer discountedPrice; // Java 필드명: discountedPrice (Integer 또는 int)
    // --- 새로 추가되는 가격 필드 끝 ---

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column(name = "image_url")
    private String imageUrl;

    // --- 새로 추가된 badgeInfo 필드 ---
    @Column(name = "badge_info") // DB 컬럼명: badge_info
    private String badgeInfo; // Java 필드명: badgeInfo (콤마 구분 문자열로 저장)
    // --- 새로 추가된 badgeInfo 필드 끝 ---

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brands brand; // Brands 엔티티 참조
}