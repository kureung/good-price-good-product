package kr.co.gpgp.domain.courier;

import kr.co.gpgp.domain.address.Address;
import kr.co.gpgp.domain.delivery.Delivery;
import kr.co.gpgp.domain.requirement.Requirement;
import kr.co.gpgp.domain.user.Role;
import kr.co.gpgp.domain.user.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class CourierTest {


    @Test
    void 배송원_생성(){
        User user = User.of("user","kgh22522252@gmail.com", Role.USER);

        Courier courier = Courier.of(user, CourierArea.SEOUL);

        Assertions.assertThat(courier.getCourierArea()).isEqualTo(CourierArea.SEOUL);
    }

    @Test
    void 배송원_지역_변경(){
        User user = User.of("user","kgh22522252@gmail.com", Role.USER);

        Courier courier = Courier.of(user, CourierArea.SEOUL);

        courier.updateCourierArea(CourierArea.GYEONGGI_DO);

        Assertions.assertThat(courier.getCourierArea()).isEqualTo(CourierArea.GYEONGGI_DO);

    }

    @Test
    void 배송원_택배_할당(){
        User courierUser = User.of("courier","kgh22522252@gmail.com", Role.USER);
        Courier courier = Courier.of(courierUser, CourierArea.SEOUL);

        User user =  User.of("user","kgh225222522@gmail.com", Role.USER);
        Requirement requirement = Requirement.of(user,"test");
        Address address = Address.of(user,"서울특별시 강남구 가나동 123","12345","우리집","1층");
        Delivery delivery = Delivery.of(requirement,address);

//        courier.addDelivery(delivery);
//
//        Assertions.assertThat(courier.getDeliverys().get(0).getAddress().getName()).isEqualTo("우리집");

    }

    @Test
    void 배송원_여러택배_할당(){
        User courierUser = User.of("courier","kgh22522252@gmail.com", Role.USER);
        Courier courier = Courier.of(courierUser, CourierArea.SEOUL);

        User user =  User.of("user","kgh225222522@gmail.com", Role.USER);

        Requirement requirement = Requirement.of(user,"test");
        Address address = Address.of(user,"서울특별시 강남구 가나동 123","12345","우리집1","1층");
        Delivery delivery = Delivery.of(requirement,address);
//        courier.addDelivery(delivery);
//
//        Requirement requirement2 = Requirement.of(user,"test");
//        Address address2 = Address.of(user,"서울특별시 강남구 가나동 123","12345","우리집2","1층");
//        Delivery delivery2 = Delivery.of(requirement2,address2);
//        courier.addDelivery(delivery2);
//
//        Requirement requirement3 = Requirement.of(user,"test");
//        Address address3 = Address.of(user,"서울특별시 강남구 가나동 123","12345","우리집3","1층");
//        Delivery delivery3 = Delivery.of(requirement3,address3);
//        courier.addDelivery(delivery3);
//
//        Assertions.assertThat(courier.getDeliverys().get(0).getAddress().getName()).isEqualTo("우리집1");
//        Assertions.assertThat(courier.getDeliverys().get(1).getAddress().getName()).isEqualTo("우리집2");
//        Assertions.assertThat(courier.getDeliverys().get(2).getAddress().getName()).isEqualTo("우리집3");

    }

}
