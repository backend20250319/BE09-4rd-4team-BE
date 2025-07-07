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

}