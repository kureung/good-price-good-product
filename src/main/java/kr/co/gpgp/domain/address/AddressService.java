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
@Transactional
@RequiredArgsConstructor
public class AddressService {

    final int ADDRESS_CREATE_COUNT_MAX = 10;
    private final UserRepositoryImpl userRepository;
    private final AddressRepository addressRepository;

    public Address create(Long id, AddressDto addressDto) {

        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        int count = addressRepository.findByUserId(id).size();

        if (count > ADDRESS_CREATE_COUNT_MAX) {
            throw new IllegalArgumentException(user.getName() + " 회원의 주소 생성개수가 초과되어 더이상 주소 생성을 할수없습니다.");
        }

        Address address = addressDto.toEntity(user);
        return addressRepository.save(address);
    }

    public void delete(Long id, Long addressId) {
        userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        Address address = addressRepository.findById(addressId).orElseThrow(AddressNotFoundException::new);

        addressRepository.delete(address);
    }

    public void update(Long userId, AddressDto address) {
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Address addressBefore = addressRepository.findById(address.getId()).orElseThrow(AddressNotFoundException::new);

        addressBefore.update(
                address.getRoadName(),
                address.getZipCode(),
                address.getName(),
                address.getDetailed()
        );
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
