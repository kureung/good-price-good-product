package kr.co.gpgp.domain.courier;

import kr.co.gpgp.domain.address.Address;
import kr.co.gpgp.domain.delivery.Delivery;
import kr.co.gpgp.domain.requirement.Requirement;
import kr.co.gpgp.domain.user.Role;
import kr.co.gpgp.domain.user.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class CourierContainerTest {


    @Test
    void 택배를_등록하다(){
        //given
        User user = User.of("user","kgh22522252@gmail.com", Role.USER);
        User courierUser = User.of("courier","test@gmail.com",Role.USER);
        Courier courier = Courier.of(courierUser,CourierArea.SEOUL);

        Requirement requirement = Requirement.of(user,"userTest");
        Address address = Address.of(user,"서울특별시 강남구 어디동 12-3","12345","누구집","");
        Delivery delivery = Delivery.of(requirement,address);

        //when
        CourierContainer courierContainer= CourierContainer.of(courier,delivery);

        //then
        Assertions.assertThat(courierContainer.getCourier().getUser().getName()).isEqualTo("courier");
        Assertions.assertThat(courierContainer.getDelivery().getAddress().getName()).isEqualTo("누구집");
    }

}
