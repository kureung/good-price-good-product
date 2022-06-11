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
@Transactional
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    private final UserRepository userRepository;

    /**
     * 주소는 생성할수 있다.
     *
     * @param userId         주소를 생성할 유저 ID 값
     * @param addressRequest 생성할 주소 값
     * @return Long
     */
    public Long create(Long userId, AddressRequest addressRequest) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("user ID를 조회할수 없어 주소를 생성할수 없습니다."));

        Address address = addressRepository.save(
                addressRequest.toEntity(user));

        return address.getId();
    }

    /**
     * 주소는 삭제할수 있다.
     *
     * @param addressId 주소 ID 값을 가져온다
     */
    public void delete(Long addressId) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("address ID 가 없어 주소를 삭제할수 없습니다."));

        addressRepository.delete(address);
    }

    /**
     * 주소는 수정할수 있다.
     *
     * @param addressBeforeId adress_id 수정할 주소 Id값이다
     * @param addressRequest  수정된 address 값을 가져온다.
     */
    public void update(Long addressBeforeId, AddressRequest addressRequest) {

        Address addressBefore = addressRepository.findById(addressBeforeId)
                .orElseThrow(() -> new IllegalArgumentException("변경할 Address ID 값을 조회할수 없어 변경을 할수 없습니다."));

        addressBefore.update(addressRequest.toEntity(addressBefore.getUser()));
    }

    /**
     * 주소는 조회가 가능하다.
     *
     * @param userId
     * @return List&lt;AddressResponse>
     */
    public List<AddressResponse> select(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("조회하려는 User ID 값이 조회할수 없습니다."));

        return addressRepository.findByUserId(userId);
    }

}
