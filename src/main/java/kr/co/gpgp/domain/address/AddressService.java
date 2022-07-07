package kr.co.gpgp.domain.address;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import kr.co.gpgp.domain.delivery.Delivery;
import kr.co.gpgp.domain.user.User;
import kr.co.gpgp.domain.user.UserNotFoundException;
import kr.co.gpgp.repository.user.UserRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddressService {

    final int ADDRESS_CREATE_COUNT_MAX = 10;
    private final UserRepositoryImpl userRepository;
    private final AddressRepository addressRepository;

    @Transactional
    public Address create(Long id, String roadName, String zipCode, String name, String detailed) {

        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        int count = addressRepository.findByUserId(id).size();

        if (count > ADDRESS_CREATE_COUNT_MAX) {
            throw new IllegalArgumentException(user.getName() + " 회원의 주소 생성개수가 초과되어 더이상 주소 생성을 할수없습니다.");
        }

        return addressRepository.save(
                Address.of(user, roadName, zipCode, name, detailed)
        );
    }

    @Transactional
    public void delete(Long id, Long addressId) {
        userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        Address address = addressRepository.findById(addressId).orElseThrow(AddressNotFoundException::new);

        addressRepository.delete(address);
    }

    @Transactional
    public void update(Long userId, Long addressId, String roadName, String zipCode, String name, String detailed) {
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Address addressBefore = addressRepository.findById(addressId).orElseThrow(AddressNotFoundException::new);

        addressBefore.update(roadName, zipCode, name, detailed);
    }

    public List<Address> select(Long userId) {
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        return addressRepository.findByUserId(userId);
    }

    public List<Address> selectSectionId(List<Long> addressIdList) {
        return addressIdList.stream()
                .map(
                        ls -> addressRepository.findById(ls)
                                .orElseThrow(AddressNotFoundException::new)

                )
                .collect(Collectors.toList());
    }

    public Optional<Address> findById(Delivery delivery) {
        return addressRepository.findById(delivery.getAddress().getId());
    }

}
