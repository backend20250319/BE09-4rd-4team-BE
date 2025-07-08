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

    // 새로운 상품 상세 조회 메서드 추가 (문자열 식별자 사용)
    public Optional<ProductResponseDTO> getSkinTonerProductByIdentifier(String productIdentifier) {
        Long actualProductId = convertIdentifierToId(productIdentifier);

        if (actualProductId == null) {
            return Optional.empty();
        }

        return productRepository.findById(actualProductId)
                .map(ProductResponseDTO::new);
    }

    // Helper method to convert string identifier to actual product ID
    private Long convertIdentifierToId(String identifier) {
        if ("product1".equalsIgnoreCase(identifier)) {
            return 1L;
        }
        return null;
    }

    // 중복 제거를 위해 정렬 로직을 분리한 헬퍼 메서드
    private Sort createSort(String sortBy, String sortDirection) {
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
                    sortProperty = "salesCount";
                    break;
                case "lowprice":
                    sortProperty = "discountedPrice";
                    direction = Sort.Direction.ASC;
                    break;
                case "highprice":
                    sortProperty = "discountedPrice";
                    direction = Sort.Direction.DESC;
                    break;
                case "discount":
                    sortProperty = "discountRate";
                    break;
                default:
                    // 클라이언트에서 직접 필드명을 넘겨줄 경우, 해당 필드명이 Products 엔티티에 있는지 확인하는
                    // 추가적인 유효성 검사 로직이 필요할 수 있다.
                    sortProperty = sortBy;
                    break;
            }
        }
        return Sort.by(direction, sortProperty);
    }

    public List<ProductResponseDTO> getAllProducts(String sortBy, String sortDirection) {
        Sort sort = createSort(sortBy, sortDirection); // 헬퍼 메서드 호출
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
        // 헬퍼 메서드 호출
        Sort sort = createSort(searchRequest.getSortBy(), searchRequest.getSortDirection());

        List<Products> products;
        if (searchRequest.getKeyword() != null && !searchRequest.getKeyword().isEmpty()) {
            products = productRepository.findByProductNameContainingIgnoreCase(searchRequest.getKeyword(), sort);
        } else {
            products = productRepository.findAll(sort);
        }
        return products.stream()
                .map(ProductResponseDTO::new)
                .collect(Collectors.toList());
    }
}