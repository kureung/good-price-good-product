package kr.co.gpgp.domain.delivery;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.util.List;
import kr.co.gpgp.common.ServiceTest;
import kr.co.gpgp.domain.address.Address;
import kr.co.gpgp.domain.requirement.Requirement;
import kr.co.gpgp.domain.user.Role;
import kr.co.gpgp.domain.user.User;
import kr.co.gpgp.domain.user.UserNotFoundException;
import kr.co.gpgp.web.api.delivery.DeliveryResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeliveryUserServiceTest extends ServiceTest {

    private User user;
    private Address address;
    private Requirement requirement;
    private Delivery delivery;

    @BeforeEach
    void setup() {

        user = User.of("AAA", "AAA@gmail.com", Role.USER);
        address = Address.of(user, "AAA_AAA_AAA", "12345", "AAAName", "AAA1");
        requirement = new Requirement("AAA");
        delivery = Delivery.of(requirement, address);

        user = userRepository.save(user);
        address = addressRepository.save(address);
        deliveryRepository.save(delivery);

    }

    @Test
    void 모든배송_조회_성공() {
        //given
        Address address2 = Address.of(user, "AAA2_AAA2_AAA2", "12345", "AAA@Name", "AAA2");
        Requirement requirement2 = new Requirement("AAA2");
        Delivery delivery2 = Delivery.of(requirement2, address2);
        addressRepository.save(address2);
        deliveryRepository.save(delivery2);

        User unknownUser = User.of("BBB", "BBB@gmail.com", Role.USER);
        Address unknownAddress = Address.of(unknownUser, "BBB_BBB_BBB", "12345", "BBBName", "BBB1");
        Delivery unknownDelivery = Delivery.of(new Requirement("BBB"), unknownAddress);

        userRepository.save(unknownUser);
        addressRepository.save(unknownAddress);
        deliveryRepository.save(unknownDelivery);

        //when
        List<Delivery> list = deliveryUserService.selectAll(user.getId());

        //then
        assertThat(list.stream().count()).isEqualTo(2);
        Assertions.assertAll(
                () -> assertThat(list).isNotNull(),
                () -> assertThat(list.get(0).getRequirement().getMessage()).isEqualTo(requirement.getMessage()),
                () -> assertThat(list.get(0).getAddressRoadName()).isEqualTo(address.getRoadName()),
                () -> assertThat(list.get(0).getAddressZipCode()).isEqualTo(address.getZipCode()),
                () -> assertThat(list.get(0).getAddressName()).isEqualTo(address.getName()),
                () -> assertThat(list.get(0).getAddressDetailed()).isEqualTo(address.getDetailed())
        );
        Assertions.assertAll(
                () -> assertThat(list).isNotNull(),
                () -> assertThat(list.get(1).getRequirement().getMessage()).isEqualTo(requirement2.getMessage()),
                () -> assertThat(list.get(1).getAddressRoadName()).isEqualTo(address2.getRoadName()),
                () -> assertThat(list.get(1).getAddressZipCode()).isEqualTo(address2.getZipCode()),
                () -> assertThat(list.get(1).getAddressName()).isEqualTo(address2.getName()),
                () -> assertThat(list.get(1).getAddressDetailed()).isEqualTo(address2.getDetailed())
        );

    }

    @Test
    void ID가_존재하지않아_모든배송_조회_실패() {
        org.assertj.core.api.Assertions
                .assertThatThrownBy(() -> deliveryUserService.selectAll(Long.MAX_VALUE))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("회원을 찾을수 없습니다.");
    }

    @Test
    void 선택_배송_조회_성공() {

        User pickUser = User.of("BBB", "BBB@gmail.com", Role.USER);
        Address pickAddress = Address.of(pickUser, "BBB_BBB_BBB", "12345", "BBBName", "BBB1");
        Requirement pickRequirement = new Requirement("BBB");
        Delivery pickDelivery = Delivery.of(pickRequirement, pickAddress);

        userRepository.save(pickUser);
        addressRepository.save(pickAddress);
        deliveryRepository.save(pickDelivery);

        Delivery delivery = deliveryUserService.select(pickUser.getId(), pickDelivery.getId());

        Assertions.assertAll(
                () -> assertThat(delivery).isNotNull(),
                () -> assertThat(delivery.getRequirement().getMessage()).isEqualTo(pickRequirement.getMessage()),
                () -> assertThat(delivery.getAddressName()).isEqualTo(pickAddress.getName()),
                () -> assertThat(delivery.getAddressDetailed()).isEqualTo(pickAddress.getDetailed()),
                () -> assertThat(delivery.getAddressRoadName()).isEqualTo(pickAddress.getRoadName()),
                () -> assertThat(delivery.getAddressZipCode()).isEqualTo(pickAddress.getZipCode())
        );
    }

    @Test
    void 유저ID가_존재하지않아_선택_배송_조회_실패() {

        assertThatThrownBy(() -> deliveryUserService.select(Long.MAX_VALUE, null))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("회원을 찾을수 없습니다.");
    }

    @Test
    void 유저가_선택한_배송이_유저값이_아니라면_예외가_발생한다() {
        User user = User.of("BBB", "BBB@gmail.com", Role.USER);
        Address address = Address.of(user, "AAA2_AAA2_AAA2", "12345", "AAA@Name", "AAA2");
        Requirement requirement = new Requirement("AAA2");
        Delivery delivery = Delivery.of(requirement, address);
        addressRepository.save(address);
        deliveryRepository.save(delivery);

        assertThatThrownBy(() -> deliveryUserService.select(this.user.getId(), delivery.getId()))
                .isInstanceOf(DeliveryNotFoundException.class)
                .hasMessage("배송을 찾을수 없습니다.");
    }

    @Test
    void 유저는배송을_구매확정상태로_바꿀수있다() {
        delivery.nextStepInstruct();
        delivery.nextStepDeparture();
        delivery.nextStepInTransit();
        delivery.nextStepFinalDelivery();
        delivery.nextStepPurchaseConfirmation();
    }

    @Test
    void Response_convertList_확인() {
        User user = User.of("BBB", "BBB@gmail.com", Role.USER);
        Address address1 = Address.of(user, "BBB_BBB_BBB", "12345", "BBBName", "BBB1");
        Delivery delivery1 = Delivery.of(new Requirement("BBB"), address1);

        userRepository.save(user);
        addressRepository.save(address1);
        deliveryRepository.save(delivery1);

        Address address2 = Address.of(user, "AAA2_AAA2_AAA2", "12345", "AAA@Name", "AAA2");
        Requirement requirement2 = new Requirement("AAA2");
        Delivery delivery3 = Delivery.of(requirement2, address2);
        addressRepository.save(address2);
        deliveryRepository.save(delivery3);
        Long userId = userRepository.findByEmail("BBB@gmail.com").get().getId();

        List<Delivery> list = deliveryUserService.selectAll(userId);
        List<DeliveryResponse> res = DeliveryResponse.convertList(list);

        System.out.println(res.get(0).getRequirement());

        System.out.println(res.get(1).getAddressName());

    }

}
