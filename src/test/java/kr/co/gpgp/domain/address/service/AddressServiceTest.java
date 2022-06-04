package kr.co.gpgp.domain.address.service;

import java.util.Optional;
import kr.co.gpgp.domain.address.entity.Address;
import kr.co.gpgp.domain.address.repository.AddressRepository;
import kr.co.gpgp.domain.user.entity.Role;
import kr.co.gpgp.domain.user.entity.User;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class AddressServiceTest {

    @Autowired
    private AddressService addressService;
    @Autowired
    private AddressRepository addressRepository;


    private static User SUCCESS_USER =
            User.of("kang", "kgh22522252@gmail.com", Role.USER);
    private static String SUCCESS_ROADNAME = "경기도_광주시_회덕동_회덕길111";
    private static String SUCCESS_ZIPCODE = "12345";
    private static String SUCCESS_NAME = "나메이크성";
    private static String SUCCESS_DETAILED = "111동111호";
    @Test
    public void 주소생성후_변경후_삭제를_할수있다면_예외는_발생되지_않습니다() {
        Address address = addressService.create(SUCCESS_USER,
                SUCCESS_ROADNAME,
                SUCCESS_ZIPCODE,
                SUCCESS_NAME,
                SUCCESS_DETAILED);

        address.updateRoadName("경기도 광주시 탄번동 00-00");

        addressService.delete(address.getId());
    }

    @Test
    public void 주소생성후_삭제를_할수있다면_예외는_발생되지_않습니다() {
        Address address = addressService.create(SUCCESS_USER,
                SUCCESS_ROADNAME,
                SUCCESS_ZIPCODE,
                SUCCESS_NAME,
                SUCCESS_DETAILED);

        addressService.delete(address.getId());
    }

    @Test
    public void 주소조회후_변경후_삭제를_할수있다면_예되는_발생되지_않습니다() {
        Address address = addressService.create(SUCCESS_USER,
                SUCCESS_ROADNAME,
                SUCCESS_ZIPCODE,
                SUCCESS_NAME,
                SUCCESS_DETAILED);

        Optional<Address> getAddress = addressRepository.findById(address.getId());

        addressService.delete(getAddress.get().getId());
    }


    @Test
    public void 주소생성이_정상적으로_처리되었으면_예외는_발생되지_않는다() {
        assertThat(addressService.create(SUCCESS_USER,
                SUCCESS_ROADNAME,
                SUCCESS_ZIPCODE,
                SUCCESS_NAME,
                SUCCESS_DETAILED))
                .isNotInstanceOf(IllegalArgumentException.class)
                .isNotNull();
    }

    @Test
    public void 주소생성중_zipCode가_유효하지_않은_값이_들어올떄_예외가_발생됨니다() {

        assertThatThrownBy(() -> addressService.create(SUCCESS_USER,
                SUCCESS_ROADNAME,
                "123",
                SUCCESS_NAME,
                SUCCESS_DETAILED))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("우편번호 형식이 맞지 않습니다.");

    }

    @Test
    public void 주소생성중_name이_유효하지_않은_값이_들어올떄_예외가_발생됨니다() {

        assertThatThrownBy(() -> addressService.create(SUCCESS_USER,
                SUCCESS_ROADNAME,
                SUCCESS_ZIPCODE,
                "",
                SUCCESS_DETAILED))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주소 이름은 비어있을 수 없습니다.");

    }

    @Test
    public void 주소생성중_roadName이_유효하지_않은_값이_들어올떄_예외가_발생됨니다() {

        assertThatThrownBy(() -> addressService.create(SUCCESS_USER,
                "도로명최소9글자",
                SUCCESS_ZIPCODE,
                SUCCESS_NAME,
                SUCCESS_DETAILED))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("도로명 길이가 맞지 않습니다.");

    }

    @Test
    public void 주소생성중_detailed가_유효하지_않은_값이_들어올떄_예외가_발생됨니다() {

        assertThatThrownBy(() -> addressService.create(SUCCESS_USER,
                SUCCESS_ROADNAME,
                SUCCESS_ZIPCODE,
                SUCCESS_NAME,
                "12345_12345_12345_12345"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주소에_자세한_설명은17자를 넘을수 업습니다.");

    }

    @Test
    public void 주소삭제는_조회되는_ID가_없다면_예외가_발생_됩니다() {
        Address address = addressService.create(SUCCESS_USER,
                SUCCESS_ROADNAME,
                SUCCESS_ZIPCODE,
                SUCCESS_NAME,
                SUCCESS_DETAILED);

        assertThatThrownBy(() -> addressService.delete(Long.MAX_VALUE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[AddressService] 주소 ID 가 없어 주소를 삭제할수 없습니다.");
    }

    @Test
    public void 주소삭제가_정상적으로_처리되어_예외가_발생되지_않습니다() {
        Address address = addressService.create(SUCCESS_USER,
                SUCCESS_ROADNAME,
                SUCCESS_ZIPCODE,
                SUCCESS_NAME,
                SUCCESS_DETAILED);

        assertThat(address)
                .isNotInstanceOf(IllegalArgumentException.class);
    }


    @Test
    public void roadName_을_수정할때_유효하지_않은_값이_들어갈때_예외가_발생한다() {
        Address address = Address.of(SUCCESS_USER,
                SUCCESS_ROADNAME,
                SUCCESS_ZIPCODE,
                SUCCESS_NAME,
                SUCCESS_DETAILED);

        assertThatThrownBy(() ->
                addressService.updateRoadName(address,
                        "9글자이상"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("도로명 길이가 맞지 않습니다.");
    }

    @Test
    public void zipCode_을_수정할때_유효하지_않은_값이_들어갈때_예외가_발생한다() {
        Address address = Address.of(SUCCESS_USER,
                SUCCESS_ROADNAME,
                SUCCESS_ZIPCODE,
                SUCCESS_NAME,
                SUCCESS_DETAILED);

        assertThatThrownBy(() ->
                addressService.updateZipCode(address,
                        "1239123"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("우편번호 형식이 맞지 않습니다.");
    }

    @Test
    public void name_을_수정할때_유효하지_않은_값이_들어갈때_예외가_발생한다() {
        Address address = Address.of(SUCCESS_USER,
                SUCCESS_ROADNAME,
                SUCCESS_ZIPCODE,
                SUCCESS_NAME,
                SUCCESS_DETAILED);

        assertThatThrownBy(() ->
                addressService.updateName(address,
                        ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주소 이름은 비어있을 수 없습니다.");
    }

    @Test
    public void datailed_을_수정할때_유효하지_않은_값이_들어갈때_예외가_발생한다() {
        Address address = Address.of(SUCCESS_USER,
                SUCCESS_ROADNAME,
                SUCCESS_ZIPCODE,
                SUCCESS_NAME,
                SUCCESS_DETAILED);

        assertThatThrownBy(() ->
                addressService.updateDetailed(address,
                        "17글자이상_17글자이상_17글자이상"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주소에_자세한_설명은17자를 넘을수 업습니다.");
    }

}
