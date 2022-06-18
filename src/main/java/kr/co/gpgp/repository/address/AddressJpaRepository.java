package kr.co.gpgp.repository.address;

import java.util.List;
import java.util.Optional;
import kr.co.gpgp.domain.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressJpaRepository extends JpaRepository<Address, Long> {

    List<Address> findByUserId(Long userId);

    Optional<Address> findByName(String name);

}
