package olive.oliveyoung.member.product.repository;

import olive.oliveyoung.member.product.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Products, Long> {

    List<Products> findAll(Sort sort);
    Optional<Products> findById(Long productId);
    List<Products> findByProductNameContainingIgnoreCase(String keyword, Sort sort);

    // TODO: 만약 특정 카테고리의 모든 상품을 가져올 때 정렬이 필요하다면 아래 메서드를 사용할 수 있습니다.
    // List<Products> findByCategoryName(String categoryName, Sort sort);
    // 현재 프론트엔드(SkinTonerProduct.jsx)에서 getAllProducts를 categoryName 없이 호출하고 있으므로
    // 이 메서드는 직접 사용되지 않을 수 있습니다.
    // ProductService의 getAllProducts 로직에서 categoryName을 필터링하려면 이 메서드가 필요할 수 있습니다.
    // 현재는 모든 상품을 가져와서 프론트에서 categoryName을 필터링하는 방식이거나,
    // 아니면 ProductController의 @GetMapping 에서 categoryName을 받아서 ProductService로 전달하는 방식일 수 있습니다.
    // (현재 ProductController의 getAllProducts는 categoryName을 받지 않습니다. 주의!)
}