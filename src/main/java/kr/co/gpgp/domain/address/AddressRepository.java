package kr.co.gpgp.domain.address;

import java.util.List;
import java.util.Optional;
import kr.co.gpgp.domain.address.dto.AddressResponse;

public interface AddressRepository  {

    List<AddressResponse> findByUserId(Long userId);

    void delete(Address address);

    Optional<Address> findById(Long addressId);

    Address save(Address toEntity);

}