package olive.oliveyoung.member.product.controller;

import olive.oliveyoung.member.product.service.ProductService;
import olive.oliveyoung.member.product.dto.ProductResponseDTO;
import olive.oliveyoung.member.product.dto.ProductSearchRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Arrays; // Arrays.asList 사용을 위해 import 추가

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3000") // CORS 설정 (프론트엔드 포트에 맞게)
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 1. 모든 상품 목록 조회 API (정렬 포함)
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection
    ) {
        // 정렬 및 방향 파라미터 유효성 검사 (잠재적 문제 방지)
        // sortBy가 제공된 경우에만 유효성 검사
        if (sortBy != null && !isValidSortColumn(sortBy)) {
            // 유효하지 않은 sortBy 값에 대해 BAD_REQUEST 반환
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // sortDirection이 제공된 경우에만 유효성 검사
        if (sortDirection != null && !isValidSortDirection(sortDirection)) {
            // 유효하지 않은 sortDirection 값에 대해 BAD_REQUEST 반환
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<ProductResponseDTO> productDTOs = productService.getAllProducts(sortBy, sortDirection);
        return new ResponseEntity<>(productDTOs, HttpStatus.OK);
    }

    // 2. 특정 상품 상세 조회 API
    // GET http://localhost:8080/api/products/{productId}
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long productId) {
        return productService.getProductById(productId)
                .map(productDTO -> new ResponseEntity<>(productDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 3. 상품 검색 및 필터링 API (검색 조건 및 정렬 포함)
    // GET http://localhost:8080/api/products/search?keyword=폰&sortBy=sold&sortDirection=desc
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDTO>> searchProducts(@ModelAttribute ProductSearchRequestDTO searchRequest) {
        // 검색 요청에서도 정렬 및 방향 파라미터 유효성 검사
        if (searchRequest.getSortBy() != null && !isValidSortColumn(searchRequest.getSortBy())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (searchRequest.getSortDirection() != null && !isValidSortDirection(searchRequest.getSortDirection())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<ProductResponseDTO> productDTOs = productService.searchProducts(searchRequest);
        return new ResponseEntity<>(productDTOs, HttpStatus.OK);
    }

    // 정렬 컬럼 유효성 검사를 위한 헬퍼 메서드 (보안상 중요!)
    private boolean isValidSortColumn(String sortBy) {
        if (sortBy == null || sortBy.isEmpty()) {
            return true; // 정렬 기준이 없으면 유효하다고 판단
        }
        // 이 목록에 ProductService의 switch 문에서 처리하는 'sortBy' 키워드와
        // Products 엔티티의 실제 필드명 (카멜케이스 소문자)을 모두 포함해야 함.
        return Arrays.asList(
                "popular",      // '인기순' 키워드
                "new",          // '신상품순' 키워드
                "sold",         // '판매순' 키워드
                "lowprice",     // '낮은 가격순' 키워드
                "discount",     // '할인율 순' 키워드
                "productid",    // `productId` 필드
                "name",
                "createdat",    // `createdAt` 필드
                "salescount",   // `salesCount` 필드
                "sellingprice", // `sellingPrice` 필드
                "originalprice",// `originalPrice` 필드
                "stock",
                "viewcount",
                "discount",
                "discountedprice",
                "discountrate"
        ).contains(sortBy.toLowerCase());
    }

    // 정렬 방향 유효성 검사를 위한 헬퍼 메서드
    private boolean isValidSortDirection(String sortDirection) {
        if (sortDirection == null || sortDirection.isEmpty()) {
            return true; // 정렬 방향이 없으면 유효하다고 판단
        }
        return sortDirection.equalsIgnoreCase("asc") || sortDirection.equalsIgnoreCase("desc");
    }
}