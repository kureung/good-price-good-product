package kr.co.gpgp.repository.address;

import java.util.List;
import kr.co.gpgp.domain.address.Address;
import kr.co.gpgp.domain.address.dto.AddressResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AddressJpaRepository extends JpaRepository<Address,Long> {

    List<AddressResponse> findByUserId(Long userId);

}
