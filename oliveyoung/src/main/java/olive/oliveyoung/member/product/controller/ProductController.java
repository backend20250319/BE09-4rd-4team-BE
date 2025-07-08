package olive.oliveyoung.member.product.controller;

import olive.oliveyoung.member.product.service.ProductService;
import olive.oliveyoung.member.product.dto.ProductResponseDTO;
import olive.oliveyoung.member.product.dto.ProductSearchRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Arrays;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3000") // CORS 설정 (프론트엔드 포트에 맞게)
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // ⭐⭐⭐ 새로운 상품 상세 조회 API 추가 (문자열 식별자 사용) ⭐⭐⭐
    // GET http://localhost:8080/api/products/skintoner/{productIdentifier}
    @GetMapping("/skintoner/{productIdentifier}")
    public ResponseEntity<ProductResponseDTO> getSkinTonerProductByIdentifier(@PathVariable String productIdentifier) {
        return productService.getSkinTonerProductByIdentifier(productIdentifier)
                .map(productDTO -> new ResponseEntity<>(productDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    // 1. 모든 상품 목록 조회 API (정렬 포함)
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection
    ) {
        if (sortBy != null && !isValidSortColumn(sortBy)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (sortDirection != null && !isValidSortDirection(sortDirection)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<ProductResponseDTO> productDTOs = productService.getAllProducts(sortBy, sortDirection);
        return new ResponseEntity<>(productDTOs, HttpStatus.OK);
    }

    // 2. 특정 상품 상세 조회 API (기존 Long ID 사용)
    // 이 엔드포인트는 유지하거나, 필요 없다면 제거할 수 있습니다.
    // 현재 프론트엔드는 /skintoner/{productIdentifier}를 사용하므로, 이 엔드포인트를 직접 호출하지 않을 것입니다.
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long productId) {
        return productService.getProductById(productId)
                .map(productDTO -> new ResponseEntity<>(productDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 3. 상품 검색 및 필터링 API (검색 조건 및 정렬 포함)
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDTO>> searchProducts(@ModelAttribute ProductSearchRequestDTO searchRequest) {
        if (searchRequest.getSortBy() != null && !isValidSortColumn(searchRequest.getSortBy())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (searchRequest.getSortDirection() != null && !isValidSortDirection(searchRequest.getSortDirection())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<ProductResponseDTO> productDTOs = productService.searchProducts(searchRequest);
        return new ResponseEntity<>(productDTOs, HttpStatus.OK);
    }

    // 정렬 컬럼 유효성 검사를 위한 헬퍼 메서드 (수정 없음)
    private boolean isValidSortColumn(String sortBy) {
        if (sortBy == null || sortBy.isEmpty()) {
            return true;
        }
        return Arrays.asList(
                "popular",
                "new",
                "sold",
                "lowprice",
                "discount",
                "productid",
                "name",
                "createdat",
                "salescount",
                "sellingprice",
                "originalprice",
                "stock",
                "viewcount",
                "discountedprice",
                "discountrate"
        ).contains(sortBy.toLowerCase());
    }

    // 정렬 방향 유효성 검사를 위한 헬퍼 메서드 (수정 없음)
    private boolean isValidSortDirection(String sortDirection) {
        if (sortDirection == null || sortDirection.isEmpty()) {
            return true;
        }
        return sortDirection.equalsIgnoreCase("asc") || sortDirection.equalsIgnoreCase("desc");
    }
}