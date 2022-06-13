package kr.co.gpgp.repository.address;

import java.util.List;
import java.util.Optional;
import kr.co.gpgp.domain.address.Address;
import kr.co.gpgp.domain.address.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AddressRepositoryImpl implements AddressRepository {

    private final AddressJpaRepository jpaRepository;

    @Override
    public List<Address> findByUserId(Long userId) {
        return jpaRepository.findByUserId(userId);
    }

    @Override
    public void delete(Address address) {
        jpaRepository.delete(address);
    }

    @Override
    public Optional<Address> findById(Long addressId) {
        return jpaRepository.findById(addressId);
    }

    @Override
    public Address save(Address toEntity) {
        return jpaRepository.save(toEntity);
    }

}
