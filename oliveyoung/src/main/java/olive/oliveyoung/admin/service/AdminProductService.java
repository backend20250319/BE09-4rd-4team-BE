package olive.oliveyoung.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import olive.oliveyoung.admin.repository.AdminProductRequestDTO;
import olive.oliveyoung.member.product.entity.Products;
import olive.oliveyoung.member.product.repository.ProductRepository;


@Service
@RequiredArgsConstructor
public class AdminProductService {

    private final ProductRepository productRepository;

    public Products createProduct(AdminProductRequestDTO dto) {
        Products product = Products.builder()
                .productName(dto.getProductName())
                .categoryName(dto.getCategoryName())
                .stock(dto.getStock())
                .originalPrice(dto.getOriginalPrice())
                .imageUrl(dto.getImageUrl())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .build();
        return productRepository.save(product);
    }
}
