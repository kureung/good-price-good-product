package kr.co.gpgp.domain.address;

import java.util.List;
import java.util.Optional;

public interface AddressRepository {

    List<Address> findByUserId(Long userId);

    void delete(Address address);

    Optional<Address> findById(Long addressId);

    Address save(Address toEntity);
    Optional<Address> findByName(String name);

}