package olive.oliveyoung.member.coupon.repository;

import olive.oliveyoung.member.coupon.domain.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Optional<Coupon> findByName(String name);
}
