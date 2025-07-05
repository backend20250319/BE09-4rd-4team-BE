package olive.oliveyoung.member.product.repository;

import olive.oliveyoung.member.product.entity.Brands; // Brands 엔티티 참조
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brands, Long> {
    // 필요시 브랜드 이름으로 조회하는 메서드 추가
    // Optional<Brands> findByBrandName(String brandName);
}