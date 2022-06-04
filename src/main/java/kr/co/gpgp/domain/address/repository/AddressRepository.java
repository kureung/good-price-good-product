package kr.co.gpgp.domain.address.repository;

import kr.co.gpgp.domain.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {

}
