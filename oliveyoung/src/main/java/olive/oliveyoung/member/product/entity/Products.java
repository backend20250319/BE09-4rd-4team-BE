package olive.oliveyoung.member.product.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder; // @Builder 어노테이션 사용을 위해 추가

import java.util.ArrayList; // List 초기화를 위해 추가
import java.util.List; // List 사용을 위해 추가

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder // DTO 변환 시 객체 생성을 편리하게 하기 위해 추가합니다.
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

    // 🚨 기존의 String badgeInfo 필드는 이제 삭제합니다. 🚨
    // @Column(name = "badge_info")
    // private String badgeInfo;

    // ✨ 새로 추가되는 다대다(Many-to-Many) 관계 매핑 필드 ✨
    @ManyToMany(fetch = FetchType.LAZY) // Products와 Badge는 다대다 관계이며, 지연 로딩을 사용합니다.
    @JoinTable(
            name = "product_badges", // 다대다 관계를 매핑할 조인(중간) 테이블의 이름
            joinColumns = @JoinColumn(name = "product_id"), // 현재 엔티티(Products)의 기본 키(productId)가 조인 테이블의 어떤 컬럼(product_id)에 매핑되는지 지정
            inverseJoinColumns = @JoinColumn(name = "badge_id") // 반대쪽 엔티티(Badge)의 기본 키(id)가 조인 테이블의 어떤 컬럼(badge_id)에 매핑되는지 지정
    )
    private List<Badges> badges = new ArrayList<>(); // 이 상품이 가진 뱃지 목록을 저장할 필드입니다. 초기화 필수!

    // Brands 엔티티와의 ManyToOne 관계는 그대로 유지합니다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brands brand; // Brands 엔티티 참조
}