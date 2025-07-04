package olive.oliveyoung.admin.controller;

import lombok.RequiredArgsConstructor;
import olive.oliveyoung.admin.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

        private final ProductService productService;

        //상품등록
        @PostMapping
        public ResponseEntity<Product> create(@RequestBody ProductDto productDto){
            return  ResponseEntity.status(HttpStatus.CREATED).body(productService.save(productDto));
        }

        //상품조회
        @GetMapping
        public List<Product> list() {
            return productService.findAll();
        }


        //상품삭제
        @DeleteMapping("/{id}")
        public ResponseEntity<void> delete(@PathVariable Long id){
            productService.delete(id);
            return ResponseEntity.noContent().build();
        }
}
