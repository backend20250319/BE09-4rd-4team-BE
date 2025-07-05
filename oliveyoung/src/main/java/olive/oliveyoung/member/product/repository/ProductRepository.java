// olive/oliveyoung/member/product/repository/ProductRepository.java
package olive.oliveyoung.member.product.repository;

import olive.oliveyoung.member.product.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Products, Long> { // Products 엔티티의 ID 타입이 Long인지 다시 확인

    // ProductService에서 사용하는 메서드들
    List<Products> findAll(Sort sort); // 모든 상품을 정렬해서 가져오는 메서드
    Optional<Products> findById(Long productId); // ID로 상품을 가져오는 메서드

    // 검색에 사용되는 메서드 (ProductSearchRequestDTO의 keyword에 해당)
    // Sort 파라미터를 받는 오버로딩 버전이 필요합니다.
    List<Products> findByProductNameContainingIgnoreCase(String keyword, Sort sort);

    // TODO: 만약 다른 검색 조건 (예: brandId, categoryName, price range)이 있다면,
    // 그에 해당하는 Repository 메서드도 여기에 정의되어야 합니다.
    // 예: List<Products> findByBrand_BrandId(Long brandId, Sort sort);
    // 예: List<Products> findByDiscountedPriceBetween(Integer minPrice, Integer maxPrice, Sort sort);
    // 예: List<Products> findByCategoryName(String categoryName, Sort sort);
}