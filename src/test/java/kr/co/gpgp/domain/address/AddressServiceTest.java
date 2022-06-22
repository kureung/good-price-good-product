package kr.co.gpgp.domain.address;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.util.List;
import kr.co.gpgp.domain.user.Role;
import kr.co.gpgp.domain.user.User;
import kr.co.gpgp.repository.address.AddressJpaRepository;
import kr.co.gpgp.repository.user.UserJpaRepository;
import kr.co.gpgp.web.api.address.AddressRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest
public class AddressServiceTest {

    @SpyBean
    AddressService addressService;

    @Autowired
    AddressJpaRepository addressRepository;

    @Autowired
    UserJpaRepository userRepository;

    private User user;
    private Address address;

    @BeforeEach
    void setups() {
        user = User.of("asdf", "kgh22@gmail.com", Role.USER,"url");
        address = Address.of(user, "12345667899", "12345", "1번째", "detailed");

        userRepository.save(user);
    }

    @Test
    void 주소_생성_성공() {
        AddressRequest addressRequest = AddressRequest.of(1L,
                address.getRoadName(),
                address.getZipCode(),
                address.getName(),
                address.getDetailed());

        AddressDto addressDto = AddressDto.of(1L, addressRequest.getId(), addressRequest.getRoadName(), addressRequest.getZipCode(), addressRequest.getName(), addressRequest.getDetailed());

        addressService.create(1L,addressDto);

        Assertions.assertAll(
                () -> assertThat(address).isNotNull(),
                () -> assertThat(address.getDetailed()).isEqualTo(addressRequest.getDetailed()),
                () -> assertThat(address.getName()).isEqualTo(addressRequest.getName()),
                () -> assertThat(address.getRoadName()).isEqualTo(addressRequest.getRoadName()),
                () -> assertThat(address.getZipCode()).isEqualTo(addressRequest.getZipCode())
        );
    }

    @Test
    void 주소_생성시_유저가_없다면_테스트가_실패한다() {

        AddressRequest addressRequest = AddressRequest
                .of(1L, address.getRoadName(),
                        address.getZipCode(),
                        address.getName(),
                        address.getDetailed());

        AddressDto addressDto = AddressDto.of(1L, addressRequest.getId(), addressRequest.getRoadName(), addressRequest.getZipCode(), addressRequest.getName(), addressRequest.getDetailed());

        addressService.create(1L,addressDto);

        AddressDto addressDtos = AddressDto.of(1000000L, addressRequest.getId(), addressRequest.getRoadName(), addressRequest.getZipCode(), addressRequest.getName(), addressRequest.getDetailed());

        assertThatThrownBy(() -> addressService.create(Long.MAX_VALUE, addressDtos))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("user ID를 조회할수 없어 주소를 생성할수 없습니다.");
    }

    @Test
    void 주소_삭제_성공() {
        Address address = Address.of(user, "12345667899", "123-345", "2name", "delete Detailed");
        address = addressRepository.save(address);

        addressService.delete(address.getId());

        Mockito.verify(addressService).delete(address.getId());
    }

    @Test
    void 주소_삭제_테스트를_실패한다() {
        assertThatThrownBy(() -> addressService.delete(Long.MAX_VALUE))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 주소_조회_성공() {

        Address address1 = Address.of(user, "11111111111111", "22222", "2번쨰", "A");
        Address address2 = Address.of(user, "22222222222222", "33333", "3번쨰", "B");
        addressRepository.save(address1);
        addressRepository.save(address2);

        //queryDSL - 특정 회원의 주소 조회 조건
        User user2 = User.of("select ", "kgh2222222@gmail.com", Role.USER,"url");
        Address address3 = Address.of(user2, "22222222222222", "33333", "4번쨰", "B");
        userRepository.save(user2);
        addressRepository.save(address3);

        List<Address> list = addressService.select(1L);

        org.assertj.core.api.Assertions.assertThat(list.stream().count()).isIn(2L);

        Assertions.assertAll(
                () -> assertThat(list).isNotNull(),
                () -> assertThat(list.get(0).getDetailed()).isEqualTo(address1.getDetailed()),
                () -> assertThat(list.get(0).getName()).isEqualTo(address1.getName()),
                () -> assertThat(list.get(0).getRoadName()).isEqualTo(address1.getRoadName()),
                () -> assertThat(list.get(0).getZipCode()).isEqualTo(address1.getZipCode())
        );
        Assertions.assertAll(
                () -> assertThat(list).isNotNull(),
                () -> assertThat(list.get(1).getDetailed()).isEqualTo(address2.getDetailed()),
                () -> assertThat(list.get(1).getName()).isEqualTo(address2.getName()),
                () -> assertThat(list.get(1).getRoadName()).isEqualTo(address2.getRoadName()),
                () -> assertThat(list.get(1).getZipCode()).isEqualTo(address2.getZipCode())
        );
    }

    @Test
    void 주소_조회_회원ID가없어_실패() {
        Address address1 = Address.of(user, "11111111111111", "22222", "2번쨰", "A");
        Address address2 = Address.of(user, "22222222222222", "33333", "3번쨰", "B");
        addressRepository.save(address1);
        addressRepository.save(address2);

        assertThatThrownBy(() -> addressService.select(Long.MAX_VALUE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("조회하려는 User ID 값이 없어 주소를 조회할수 없습니다.");
    }

    @Test
    void 등록된_주소를_찾을수없어_실패() {
        Address address1 = Address.of(user, "11111111111111", "22222", "2번쨰", "A");
        Address address2 = Address.of(user, "22222222222222", "33333", "3번쨰", "B");
        addressRepository.save(address1);
        addressRepository.save(address2);

        assertThatThrownBy(() -> addressService.select(Long.MAX_VALUE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("조회하려는 User ID 값이 없어 주소를 조회할수 없습니다.");
    }

    @Test
    void 주소_수정_성공() {
        address = addressRepository.save(address);
        AddressRequest addressRequest = AddressRequest.of(address.getId(), "경기도 성남시 상대원1동", "12345", "updateName", "new 요청");

        AddressDto addressDto = AddressDto.of(address.getUser().getId(), addressRequest.getId(), addressRequest.getRoadName(), addressRequest.getZipCode(), addressRequest.getName(), addressRequest.getDetailed());

        addressService.update(address.getId(), addressDto);

        Mockito.verify(addressService)
                .update(address.getId(), addressDto);
    }

    @Test
    void 주소_수정할_주소_ID가_존재하지않아_수정_실패() {

        address = addressRepository.save(address);
        AddressRequest addressRequest = AddressRequest.of(address.getId(), "경기도 성남시 상대원1동", "12345", "updateName", "new 요청");

        AddressDto addressDto = AddressDto.of(address.getUser().getId(), addressRequest.getId(), addressRequest.getRoadName(), addressRequest.getZipCode(), addressRequest.getName(), addressRequest.getDetailed());


        assertThatThrownBy(() -> addressService.update(Long.MAX_VALUE, addressDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("변경할 Address ID 값을 조회할수 없어 변경을 할수 없습니다.");
    }

}
