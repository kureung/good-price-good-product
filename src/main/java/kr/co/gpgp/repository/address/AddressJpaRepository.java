package kr.co.gpgp.repository.address;

import kr.co.gpgp.domain.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressJpaRepository extends JpaRepository<Address,Long> {

}
