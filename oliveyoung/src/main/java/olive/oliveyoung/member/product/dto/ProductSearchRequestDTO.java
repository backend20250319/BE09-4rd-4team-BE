package olive.oliveyoung.member.product.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchRequestDTO {
    private String keyword;
    private Long brandId;
    private String categoryName;
    private Integer minPrice;
    private Integer maxPrice;
    private String sortBy; // 정렬 기준 (price, popularity, etc.)
    private String sortDirection; // 정렬 방향 (asc, desc)
    // 페이지네이션 및 정렬 관련 필드 추가 가능 (page, size, sortBy, sortDir 등)
}