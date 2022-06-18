package kr.co.gpgp.domain.address;

import java.util.List;
import kr.co.gpgp.domain.address.Address.AddressDto;
import kr.co.gpgp.domain.user.User;
import kr.co.gpgp.repository.user.UserRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressService {

    private final UserRepositoryImpl userRepository;
    private final AddressRepository addressRepository;
    final int ADDRESS_CREATE_COUNT_MAX = 10;

    //주소는 생성할수 있다.
    public Address create(Long userId, AddressDto addressRequestDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("user ID를 조회할수 없어 주소를 생성할수 없습니다."));

        List<Address> addressList = addressRepository.findByUserId(userId);

        // 10개 까지 주소 혀용
        if (addressList.size() > ADDRESS_CREATE_COUNT_MAX) {
            throw new IllegalArgumentException(user.getName() + " 회원의 주소 생성개수가 초과되어 더이상 주소 생성을 할수없습니다.");
        }

        Address address = AddressDto.toEntity(user, addressRequestDto);
        return addressRepository.save(address);
    }

    /** 주소는 삭제할수 있다. */
    public void delete(Long addressId) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("address ID 가 없어 주소를 삭제할수 없습니다."));

        addressRepository.delete(address);
    }

    /** 주소는 수정할수 있다. */
    public void update(Long addressBeforeId, AddressDto address) {

        Address addressBefore = addressRepository.findById(addressBeforeId)
                .orElseThrow(() -> new IllegalArgumentException("변경할 Address ID 값을 조회할수 없어 변경을 할수 없습니다."));

        addressBefore.update(address);
    }


    /** 주소는 조회가 가능하다. */
    public List<Address> select(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("조회하려는 User ID 값이 없어 주소를 조회할수 없습니다."));

        return addressRepository.findByUserId(userId);
    }

}
