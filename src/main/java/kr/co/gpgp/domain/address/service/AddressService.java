package kr.co.gpgp.domain.address.service;

import kr.co.gpgp.domain.address.entity.Address;
import kr.co.gpgp.domain.address.repository.AddressRepository;
import kr.co.gpgp.domain.user.entity.User;
import kr.co.gpgp.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;

    // 1 . 주소는 생성할수 있다. ㅕ
    public Address create(User user, String roadName, String zipCode, String name, String detailed) {
        return addressRepository.save(
                Address.of(user,
                        roadName,
                        zipCode,
                        name,
                        detailed));
    }

    // 2.  주소는 삭제할수 있다.
    @Transactional
    public void delete(Long addressId) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("[AddressService] 주소 ID 가 없어 주소를 삭제할수 없습니다."));

        addressRepository.delete(address);
    }


    // 3. 주소는 수정할수 있다.
    public void updateRoadName(Address updateAddress, String roadName) {
        updateAddress.updateRoadName(roadName);
    }

    public void updateZipCode(Address updateAddress, String zipCode) {
        updateAddress.updateZipCode(zipCode);
    }

    public void updateName(Address updateAddress, String name) {
        updateAddress.updateName(name);
    }

    public void updateDetailed(Address updateAddress, String detailed) {
        updateAddress.updateDetailed(detailed);
    }


}
