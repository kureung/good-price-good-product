package kr.co.gpgp.domain.address.service;

import kr.co.gpgp.domain.address.entity.Address;
import kr.co.gpgp.domain.address.repository.AddressRepository;
import kr.co.gpgp.domain.user.entity.User;

public class AddressService {

    private AddressRepository addressRepository;

    // 1 . 주소는 생성할수 있다. ㅕ
    public void create(User user, String roadName, String zipCode, String name, String detailed) {
        /*
            유효성 검사
         */
        addressRepository.save(
                Address.of(user, roadName, zipCode, name, detailed)
        );
    }

    public void create(Address address) {
        addressRepository.save(
                Address.of(address.getUser(), address.getRoadName(),
                        address.getZipCode(), address.getName(), address.getDetailed())
        );
    }

    // 2.  주소는 삭제할수 있다.
    public void delete(Long user_id,Long address_id) {

        addressRepository.findById(user_id)
                        .orElseThrow(() -> new IllegalArgumentException("[AddressService]-해당 회원을 찾을 수 없습니다."));

        addressRepository.findById(user_id);
        addressRepository.findById(address_id);

        addressRepository.deleteById(address_id);
    }

    // 3. 주소는 수정할수 있다.
    public void update(Long id,Address address) {

    }

}
