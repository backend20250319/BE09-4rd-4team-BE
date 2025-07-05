// olive/oliveyoung/member/product/controller/ProductController.java
package olive.oliveyoung.member.product.controller;

// Products 엔티티 임포트가 더 이상 필요하지 않을 수 있지만,
// 다른 로직에서 사용될 수도 있으니 일단은 유지해도 무방합니다.
// import olive.oliveyoung.member.product.entity.Products;

import olive.oliveyoung.member.product.service.ProductService;
import olive.oliveyoung.member.product.dto.ProductResponseDTO; // ProductResponseDTO 임포트
import olive.oliveyoung.member.product.dto.ProductSearchRequestDTO; // ProductSearchRequestDTO 임포트
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// import java.util.stream.Collectors; // DTO 변환을 Service에서 하므로 더 이상 필요 없음

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3000") // CORS 설정 (프론트엔드 포트에 맞게)
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 1. 모든 상품 목록 조회 API (정렬 포함)
    // GET http://localhost:8080/api/products?sortBy=price&sortDirection=asc
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts( // 반환 타입을 List<ProductResponseDTO>로 변경
                                                                    @RequestParam(required = false) String sortBy,
                                                                    @RequestParam(required = false) String sortDirection
    ) {
        // ProductService에서 이미 DTO로 변환된 리스트를 받습니다.
        List<ProductResponseDTO> productDTOs = productService.getAllProducts(sortBy, sortDirection);
        return new ResponseEntity<>(productDTOs, HttpStatus.OK);
    }

    // 2. 특정 상품 상세 조회 API
    // GET http://localhost:8080/api/products/{productId}
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long productId) {
        // ProductService에서 이미 DTO로 변환된 Optional을 받습니다.
        return productService.getProductById(productId)
                .map(productDTO -> new ResponseEntity<>(productDTO, HttpStatus.OK)) // DTO를 바로 사용
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 3. 상품 검색 및 필터링 API (검색 조건 및 정렬 포함)
    // GET http://localhost:8080/api/products/search?keyword=폰&brandId=1&sortBy=price&sortDirection=desc
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDTO>> searchProducts(@ModelAttribute ProductSearchRequestDTO searchRequest) {
        // ProductService에서 이미 DTO로 변환된 리스트를 받습니다.
        List<ProductResponseDTO> productDTOs = productService.searchProducts(searchRequest);
        return new ResponseEntity<>(productDTOs, HttpStatus.OK);
    }

    // TODO: 사용자 측에서 필요한 다른 API 엔드포인트 추가 (예: 상품 리뷰 조회, 장바구니 추가 등)
}