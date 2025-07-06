// olive/oliveyoung/member/product/dto/ProductResponseDTO.java
package olive.oliveyoung.member.product.dto;

import olive.oliveyoung.member.product.entity.Products;
import olive.oliveyoung.member.product.entity.Badges; // Badge 엔티티 임포트
import lombok.Data;

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

    public ProductResponseDTO(Products product) {
        this.productId = product.getProductId();
        this.imageUrl = product.getImageUrl();
        this.productName = product.getProductName();
        this.originalPrice = product.getOriginalPrice();
        this.discountedPrice = product.getDiscountedPrice();
        this.stock = product.getStock();
        this.categoryName = product.getCategoryName();
        this.description = product.getDescription();

        // Brands 엔티티와의 관계에서 브랜드 이름 가져오기
        if (product.getBrand() != null) {
            // ⭐ Brands 엔티티에 'brand_name' 필드가 있으므로 'getBrandName()'을 호출해야 합니다. ⭐
            this.brandName = product.getBrand().getBrandName(); // 여기를 수정!
        } else {
            this.brandName = null;
        }

        if (product.getBadges() != null && !product.getBadges().isEmpty()) {
            this.badgeNames = product.getBadges().stream()
                    .map(Badges::getName)
                    .collect(Collectors.toList());
        } else {
            this.badgeNames = List.of();
        }
    }

    public ProductResponseDTO() {}
}