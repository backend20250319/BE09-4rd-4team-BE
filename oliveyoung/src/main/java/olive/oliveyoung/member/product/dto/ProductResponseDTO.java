package olive.oliveyoung.member.product.dto;

import olive.oliveyoung.member.product.entity.Products;
import olive.oliveyoung.member.product.entity.Badges; // Badges 임포트 확인
import lombok.Data;

import java.time.LocalDateTime; // LocalDateTime 임포트
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProductResponseDTO {
    private Long productId;
    private String imageUrl;
    private String productName;
    private Integer originalPrice;
    private Integer discountedPrice;
    private List<String> badgeNames;
    private String brandName;
    private Integer stock;
    private String categoryName;
    private String description;
    // ⭐️ 기존에 추가된 필드들
    private LocalDateTime createdAt; // 신상품 순 정렬을 위한 필드
    private Integer salesCount;    // 판매 순 정렬을 위한 필드

    // ⭐⭐⭐ 최종적으로 클라이언트에게 보낼 할인율 필드 (String 타입, "%" 포함) ⭐⭐⭐
    // "displayDiscountRate"에서 "discountRate"로 이름 변경 완료.
    // "discountRateValue"는 더 이상 DTO에 포함되지 않습니다.
    private String discountRate;


    public ProductResponseDTO(Products product) {
        this.productId = product.getProductId();
        this.imageUrl = product.getImageUrl();
        this.productName = product.getProductName();
        this.originalPrice = product.getOriginalPrice();
        this.discountedPrice = product.getDiscountedPrice();
        this.stock = product.getStock();
        this.categoryName = product.getCategoryName();
        this.description = product.getDescription();
        this.createdAt = product.getCreatedAt();
        this.salesCount = product.getSalesCount();

        // ⭐⭐⭐ Products 엔티티의 Integer 타입 discountRate를 가져와 String으로 포맷팅 ⭐⭐⭐
        // products.getDiscountRate()가 Integer를 반환한다고 가정합니다.
        if (product.getDiscountRate() != null) {
            this.discountRate = product.getDiscountRate() + "%";
        } else {
            this.discountRate = "0%"; // null일 경우 기본값 "0%"
        }

        // discountRateValue 필드는 DTO에서 완전히 제거되었으므로, 여기서 설정할 필요가 없습니다.

        if (product.getBrand() != null) {
            this.brandName = product.getBrand().getBrandName();
        } else {
            this.brandName = null;
        }

        if (product.getBadges() != null && !product.getBadges().isEmpty()) {
            this.badgeNames = product.getBadges().stream()
                    .map(Badges::getName) // Badges 엔티티에 getName() 메서드가 있어야 합니다.
                    .collect(Collectors.toList());
        } else {
            this.badgeNames = List.of(); // 빈 리스트로 초기화
        }
    }

    public ProductResponseDTO() {}
}