package olive.oliveyoung.member.product.service;

import olive.oliveyoung.member.product.entity.Products;
import olive.oliveyoung.member.product.repository.ProductRepository;
import olive.oliveyoung.member.product.dto.ProductResponseDTO;
import olive.oliveyoung.member.product.dto.ProductSearchRequestDTO;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
                .map(ProductResponseDTO::new); // ProductResponseDTO에 Products를 인자로 받는 생성자가 있으므로 이 방식은 유효합니다.
    }

    // Helper method to convert string identifier to actual product ID
    private Long convertIdentifierToId(String identifier) {
        if ("product1".equalsIgnoreCase(identifier)) {
            return 1L;
        }
        // 실제 제품 식별자에 따라 로직을 확장해야 합니다.
        // 예를 들어 "product2"면 2L, "product3"면 3L 등으로 매핑.
        return null; // 매핑되는 ID가 없으면 null 반환
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
                    sortProperty = "createdAt"; // Products 엔티티의 필드명 확인 (createdAt)
                    break;
                case "sold": // sold는 popular와 동일하게 salesCount로 정렬하는 것이 일반적
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
                case "productid": // Products 엔티티에 productId 필드가 있으므로 가능
                    sortProperty = "productId";
                    break;
                case "productname": // Products 엔티티에 productName 필드가 있으므로 가능
                    sortProperty = "productName";
                    break;
                default:
                    // 클라이언트에서 직접 필드명을 넘겨줄 경우, 해당 필드명이 Products 엔티티에 있는지 확인하는
                    // 추가적인 유효성 검사 로직이 필요할 수 있다.
                    sortProperty = sortBy; // 그대로 사용 (DB 필드명과 일치해야 함)
                    break;
            }
        }
        return Sort.by(direction, sortProperty);
    }

    public List<ProductResponseDTO> getAllProducts(String sortBy, String sortDirection) {
        Sort sort = createSort(sortBy, sortDirection); // 헬퍼 메서드 호출
        List<Products> products = productRepository.findAll(sort);
        return products.stream()
                .map(ProductResponseDTO::new) // ProductResponseDTO에 Products를 인자로 받는 생성자가 있으므로 이 방식은 유효합니다.
                .collect(Collectors.toList());
    }

    public Optional<ProductResponseDTO> getProductById(Long productId) {
        return productRepository.findById(productId)
                .map(ProductResponseDTO::new); // ProductResponseDTO에 Products를 인자로 받는 생성자가 있으므로 이 방식은 유효합니다.
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
                .map(ProductResponseDTO::new) // ProductResponseDTO에 Products를 인자로 받는 생성자가 있으므로 이 방식은 유효합니다.
                .collect(Collectors.toList());
    }

    // 카테고리별 상품 조회 메서드 (수정)
    public List<ProductResponseDTO> getProductsByCategory(String categoryName) {
        // ProductRepository를 통해 데이터베이스에서 categoryName에 해당하는 상품들을 조회합니다.
        // Products 엔티티를 사용하도록 수정
        List<Products> products = productRepository.findByCategoryName(categoryName);

        // 조회된 Products 엔티티 목록을 ProductResponseDTO 목록으로 변환하여 반환
        return products.stream()
                .map(ProductResponseDTO::new) // Products를 인자로 받는 ProductResponseDTO 생성자가 있으므로, convertToDto 메서드 대신 이 방식 사용
                .collect(Collectors.toList());
    }

    // 상품 상태별 상품 조회 메서드
    public List<ProductResponseDTO> getProductsByState(String productState) {
        List<Products> products = productRepository.findByState(productState);
        return products.stream()
                .map(ProductResponseDTO::new) // Products 엔티티를 ProductResponseDTO로 변환
                .collect(Collectors.toList());
    }

}