// olive/oliveyoung/member/product/service/ProductService.java
package olive.oliveyoung.member.product.service;

import olive.oliveyoung.member.product.entity.Products; // Products 엔티티 임포트
import olive.oliveyoung.member.product.repository.ProductRepository;
import olive.oliveyoung.member.product.dto.ProductResponseDTO; // ProductResponseDTO 임포트
import olive.oliveyoung.member.product.dto.ProductSearchRequestDTO; // ProductSearchRequestDTO 임포트
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors; // Collectors 임포트

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 1. 모든 상품 목록 조회 API
    // 이 메서드는 이제 ProductResponseDTO 리스트를 반환합니다.
    public List<ProductResponseDTO> getAllProducts(String sortBy, String sortDirection) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (sortDirection != null && sortDirection.equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }

        Sort sort = Sort.by(direction, "productId"); // 기본 정렬 기준 (DB에 productId 필드가 있다고 가정)

        // sortBy 파라미터에 따른 정렬 기준 변경
        if (sortBy != null && !sortBy.isEmpty()) {
            switch (sortBy.toLowerCase()) {
                case "popular":
                    sort = Sort.by(direction, "popularCount"); // 엔티티에 'popularCount' 필드가 있다고 가정
                    break;
                case "new":
                    sort = Sort.by(direction, "createdAt"); // 엔티티에 'createdAt' (생성일시) 필드가 있다고 가정
                    break;
                case "sold":
                    sort = Sort.by(direction, "soldCount"); // 엔티티에 'soldCount' (판매량) 필드가 있다고 가정
                    break;
                case "lowprice":
                    sort = Sort.by(direction, "discountedPrice"); // 할인된 가격 필드가 있다고 가정
                    break;
                case "discount":
                    sort = Sort.by(direction, "discountRate"); // 할인율 필드가 있다고 가정
                    break;
                default:
                    // 정의되지 않은 sortBy 값에 대한 기본 처리
                    break;
            }
        }

        // 1단계: Repository에서 Products 엔티티 리스트를 가져옵니다.
        List<Products> products = productRepository.findAll(sort);

        // 2단계: 가져온 Products 엔티티 리스트를 ProductResponseDTO 리스트로 변환하여 반환합니다.
        return products.stream()
                .map(ProductResponseDTO::new) // Products 엔티티를 ProductResponseDTO 생성자로 전달
                .collect(Collectors.toList());
    }

    // 2. 특정 상품 상세 조회 API
    // 이 메서드도 ProductResponseDTO를 반환하도록 변경합니다.
    public Optional<ProductResponseDTO> getProductById(Long productId) {
        return productRepository.findById(productId)
                .map(ProductResponseDTO::new); // 찾은 엔티티를 DTO로 변환
    }

    // 3. 상품 검색 및 필터링 API
    // 이 메서드도 ProductResponseDTO 리스트를 반환하도록 변경합니다.
    public List<ProductResponseDTO> searchProducts(ProductSearchRequestDTO searchRequest) {
        // searchRequest DTO에서 필요한 검색 조건과 정렬 조건을 추출합니다.
        Sort.Direction direction = Sort.Direction.ASC;
        if (searchRequest.getSortDirection() != null && searchRequest.getSortDirection().equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }
        Sort sort = Sort.by(direction, "productId"); // 기본 정렬

        // searchRequest.getSortBy()를 사용하여 sortBy에 따른 정렬 로직 적용
        if (searchRequest.getSortBy() != null && !searchRequest.getSortBy().isEmpty()) {
            switch (searchRequest.getSortBy().toLowerCase()) {
                case "popular":
                    sort = Sort.by(direction, "popularCount");
                    break;
                case "new":
                    sort = Sort.by(direction, "createdAt");
                    break;
                case "sold":
                    sort = Sort.by(direction, "soldCount");
                    break;
                case "lowprice":
                    sort = Sort.by(direction, "discountedPrice");
                    break;
                case "discount":
                    sort = Sort.by(direction, "discountRate");
                    break;
                default:
                    break;
            }
        }

        List<Products> products;
        // 실제 검색 로직 (예시: 키워드 검색만 고려)
        if (searchRequest.getKeyword() != null && !searchRequest.getKeyword().isEmpty()) {
            // ProductRepository에 findByProductNameContainingIgnoreCase(String keyword, Sort sort) 메서드가 있어야 함
            products = productRepository.findByProductNameContainingIgnoreCase(searchRequest.getKeyword(), sort);
        } else {
            products = productRepository.findAll(sort); // 검색 조건이 없으면 모든 상품 반환
        }

        // Products 엔티티 리스트를 ProductResponseDTO 리스트로 변환하여 반환
        return products.stream()
                .map(ProductResponseDTO::new)
                .collect(Collectors.toList());
    }
}