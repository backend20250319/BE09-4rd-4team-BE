package olive.oliveyoung.member.user.repository;

import olive.oliveyoung.member.user.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
