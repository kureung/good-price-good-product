package kr.co.gpgp.domain.address;

import java.util.List;
import kr.co.gpgp.domain.address.dto.AddressRequest;
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

    public Long create(Long userId, AddressRequest addressRequest) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("user ID를 조회할수 없어 주소를 생성할수 없습니다."));

        Address address = addressRepository.save(Address.of(addressRequest));

        return address.getId();
    }

    /** 주소는 삭제할수 있다. */
    public void delete(Long addressId) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("address ID 가 없어 주소를 삭제할수 없습니다."));

        addressRepository.delete(address);
    }

    /** 주소는 수정할수 있다. */
    public void update(Long addressBeforeId, AddressRequest addressRequest) {

        Address addressBefore = addressRepository.findById(addressBeforeId)
                .orElseThrow(() -> new IllegalArgumentException("변경할 Address ID 값을 조회할수 없어 변경을 할수 없습니다."));

        addressBefore.update(Address.of(addressRequest));
    }

    /** 주소는 조회가 가능하다. */
    public List<Address> select(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("조회하려는 User ID 값이 조회할수 없습니다."));
        List<Address> list = addressRepository.findByUserId(userId);

        return list;
    }

}
