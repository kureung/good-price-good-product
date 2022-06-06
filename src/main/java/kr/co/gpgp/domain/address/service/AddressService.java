package kr.co.gpgp.domain.address.service;

import java.util.List;
import kr.co.gpgp.domain.address.dto.AddressRequest;
import kr.co.gpgp.domain.address.dto.AddressResponse;
import kr.co.gpgp.domain.address.entity.Address;
import kr.co.gpgp.domain.address.repository.AddressRepository;
import kr.co.gpgp.domain.user.entity.User;
import kr.co.gpgp.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressService {


    private final AddressRepository addressRepository;

    private final UserRepository userRepository;

    // 1 . 주소는 생성할수 있다. ㅕ
    public Address create(Long userId, AddressResponse addressResponse) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("user ID를 조회할수 없어 주소를 생성할수 없습니다."));

        Address address = addressRepository.save(
                Address.of(user,
                        addressResponse.getRoadName(),
                        addressResponse.getZipCode(),
                        addressResponse.getName(),
                        addressResponse.getDetailed()));

        return address;
    }

    // 2.  주소는 삭제할수 있다.
    public void delete(Long addressId) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("address ID 가 없어 주소를 삭제할수 없습니다."));

        addressRepository.delete(address);
    }


    // 3. 주소는 수정할수 있다.
    public void update(Long addressBeforeId,  AddressResponse addressResponse) {

        Address addressBefore = addressRepository.findById(addressBeforeId)
                .orElseThrow(() -> new IllegalArgumentException("변경할 Address ID 값을 조회할수 없어 변경을 할수 없습니다."));

        addressBefore.update(addressResponse.toEntity(addressBefore.getUser()));
    }

    //4. 주소는 조회가 가능하다.
    public List<Address> select(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("조회하려는 User ID 값이 조회할수 없습니다."));

        return addressRepository.findByUser(userId);

    }

}
