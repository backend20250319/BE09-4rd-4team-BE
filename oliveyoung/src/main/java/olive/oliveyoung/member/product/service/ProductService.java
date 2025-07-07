package olive.oliveyoung.member.product.service;

import olive.oliveyoung.member.product.entity.Products;
import olive.oliveyoung.member.product.repository.ProductRepository;
import olive.oliveyoung.member.product.dto.ProductResponseDTO;
import olive.oliveyoung.member.product.dto.ProductSearchRequestDTO;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponseDTO> getAllProducts(String sortBy, String sortDirection) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (sortDirection != null && sortDirection.equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }

        String sortProperty = "productId"; // 기본 정렬 필드

        if (sortBy != null && !sortBy.isEmpty()) {
            switch (sortBy.toLowerCase()) {
                case "popular":
                    sortProperty = "salesCount";
                    break;
                case "new":
                    sortProperty = "createdAt";
                    break;
                case "sold":
                    sortProperty = "salesCount"; // 판매량 정렬은 'popular'와 동일할 수 있음
                    break;
                case "lowprice":
                    sortProperty = "discountedPrice"; // 할인된 가격 낮은 순
                    direction = Sort.Direction.ASC; // 낮은 가격은 오름차순으로 정렬
                    break;
                case "highprice": // 고가 순 추가 (선택 사항)
                    sortProperty = "discountedPrice";
                    direction = Sort.Direction.DESC;
                    break;
                case "discount":
                    // ⭐⭐⭐ 가장 중요한 변경: Products 엔티티의 실제 discountRate 필드 사용 ⭐⭐⭐
                    sortProperty = "discountRate";
                    // 할인율은 보통 높은 순서 (내림차순)으로 표시되므로, desc로 강제하거나
                    // 파라미터를 통해 전달받는 'direction'을 그대로 사용합니다.
                    // 현재 코드에서는 파라미터 direction을 따르므로 변경 없음.
                    break;
                default:
                    sortProperty = sortBy; // 클라이언트에서 직접 필드명을 넘겨줄 경우 대비
                    break;
            }
        }

        Sort sort = Sort.by(direction, sortProperty);
        List<Products> products = productRepository.findAll(sort);
        return products.stream()
                .map(ProductResponseDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<ProductResponseDTO> getProductById(Long productId) {
        return productRepository.findById(productId)
                .map(ProductResponseDTO::new);
    }

    public List<ProductResponseDTO> searchProducts(ProductSearchRequestDTO searchRequest) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (searchRequest.getSortDirection() != null && searchRequest.getSortDirection().equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }

        String sortProperty = "productId"; // 기본 정렬 필드
        if (searchRequest.getSortBy() != null && !searchRequest.getSortBy().isEmpty()) {
            switch (searchRequest.getSortBy().toLowerCase()) {
                case "popular":
                    sortProperty = "salesCount";
                    break;
                case "new":
                    sortProperty = "createdAt";
                    break;
                case "sold":
                    sortProperty = "salesCount"; // 판매량 정렬은 'popular'와 동일할 수 있음
                    break;
                case "lowprice":
                    sortProperty = "discountedPrice"; // 할인된 가격 낮은 순
                    direction = Sort.Direction.ASC; // 낮은 가격은 오름차순으로 정렬
                    break;
                case "highprice": // 고가 순 추가 (선택 사항)
                    sortProperty = "discountedPrice";
                    direction = Sort.Direction.DESC;
                    break;
                case "discount":
                    // ⭐⭐⭐ 가장 중요한 변경: Products 엔티티의 실제 discountRate 필드 사용 ⭐⭐⭐
                    sortProperty = "discountRate";
                    break;
                default:
                    sortProperty = searchRequest.getSortBy(); // 클라이언트에서 직접 필드명을 넘겨줄 경우 대비
                    break;
            }
        }
        Sort sort = Sort.by(direction, sortProperty);

        List<Products> products;
        if (searchRequest.getKeyword() != null && !searchRequest.getKeyword().isEmpty()) {
            // ⭐⭐⭐ findByProductNameContainingIgnoreCase 메서드에 Sort 객체 전달 ⭐⭐⭐
            products = productRepository.findByProductNameContainingIgnoreCase(searchRequest.getKeyword(), sort);
        } else {
            products = productRepository.findAll(sort);
        }
        return products.stream()
                .map(ProductResponseDTO::new)
                .collect(Collectors.toList());
    }
}