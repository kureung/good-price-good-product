package kr.co.gpgp.domain.address.repository;

import java.util.List;
import kr.co.gpgp.domain.address.entity.Address;
import kr.co.gpgp.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {

    List<Address> findByUserId(Long userId);
;
}
