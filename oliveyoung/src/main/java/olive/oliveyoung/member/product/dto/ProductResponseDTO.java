// olive/oliveyoung/member/product/dto/ProductResponseDTO.java
package olive.oliveyoung.member.product.dto;

import olive.oliveyoung.member.product.entity.Products; // Products 엔티티 임포트
import lombok.Data; // Lombok 사용 (@Getter, @Setter 등을 자동으로 생성)

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data // @Getter, @Setter, @ToString, @EqualsAndHashCode 등을 자동으로 생성합니다.
public class ProductResponseDTO {
    private Long productId;
    private String imageUrl;      // DTO 필드: 이미지 URL
    private String productName;   // DTO 필드: 상품 이름
    private Integer originalPrice; // DTO 필드: 원래 가격
    private Integer discountedPrice; // DTO 필드: 할인된 가격
    private List<String> badge;   // DTO 필드: 배지 정보 (List<String>)
    private String brandName;     // DTO 필드: 브랜드 이름 (String)

    // 중요: Products 엔티티를 받아서 ProductResponseDTO를 생성하는 생성자
    public ProductResponseDTO(Products product) {
        this.productId = product.getProductId();
        // Products 엔티티의 'imageUrl' 필드에 대한 Getter 호출
        this.imageUrl = product.getImageUrl();

        // Products 엔티티의 'productName' 필드에 대한 Getter 호출
        this.productName = product.getProductName();

        // Products 엔티티의 'originalPrice' 필드에 대한 Getter 호출
        this.originalPrice = product.getOriginalPrice();

        // Products 엔티티의 'discountedPrice' 필드에 대한 Getter 호출
        this.discountedPrice = product.getDiscountedPrice();

        // Brands 엔티티와의 관계에서 브랜드 이름 가져오기
        // product.getBrand()는 Brands 객체를 반환하므로, Brands 객체의 getBrandName()을 호출해야 합니다.
        if (product.getBrand() != null) {
            // ⭐ Brands 엔티티에 'getBrandName()'이라는 메서드가 반드시 있어야 합니다.
            //    (Brands.java 파일에 private String brandName; 필드가 있고 @Getter가 붙어있다면 자동으로 생깁니다.)
            this.brandName = product.getBrand().getBrandName();
        } else {
            this.brandName = null; // 브랜드 정보가 없는 경우 null로 설정
        }

        // Products 엔티티의 'badgeInfo'가 콤마로 구분된 String을 반환한다고 가정
        if (product.getBadgeInfo() != null && !product.getBadgeInfo().isEmpty()) {
            this.badge = Arrays.asList(product.getBadgeInfo().split(","))
                    .stream()
                    .map(String::trim) // 각 배지 문자열의 앞뒤 공백 제거
                    .collect(Collectors.toList());
        } else {
            this.badge = List.of(); // 배지 정보가 없으면 빈 리스트 반환
        }
    }

    // 기본 생성자 (JSON 직렬화/역직렬화를 위해 필요)
    public ProductResponseDTO() {}
}