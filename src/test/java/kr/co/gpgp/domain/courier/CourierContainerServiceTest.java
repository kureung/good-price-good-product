package kr.co.gpgp.domain.courier;


import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import kr.co.gpgp.domain.address.Address;
import kr.co.gpgp.domain.address.AddressRepository;
import kr.co.gpgp.domain.delivery.Delivery;
import kr.co.gpgp.domain.requirement.Requirement;
import kr.co.gpgp.domain.user.Role;
import kr.co.gpgp.domain.user.User;
import kr.co.gpgp.domain.user.UserRepository;
import kr.co.gpgp.domain.user.UserService;
import kr.co.gpgp.repository.courier.CourierContainerJpaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
public class CourierContainerServiceTest {
    //지역마다 배송원이 할당 되어 있는 배송원에게 자동으로 할당해주는 역할

    @Autowired
    private CourierContainerService courierContainerService;
    @Autowired
    private UserService userService;
    @Autowired
    private CourierRepository courierRepository;
    @Autowired
    private CourierContainerRepository courierContainerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CourierContainerJpaRepository courierContainerJpaRepository;

    @Test
    void 판매원이_택배를_배송사에게_전달한다() {

        //given
        User courierUser =
                userRepository.save(User.of("name1", "kgh225222521@gmail.com", Role.USER));
        userService.changeOfPermission(courierUser.getId(), Role.COURIER, CourierArea.SEOUL);// 배송원 할당
        Courier courier = courierRepository.findByUserId(courierUser.getId());

        User user = User.of("name1", "kgh225222521@gmail.com", Role.USER);
        userRepository.save(user);
        Requirement requirement = new Requirement("요청사항");
        Address address = Address.of(user, "서울특별시 강남구 서처구로 334", "12345", "주소", "주소주소");
        addressRepository.save(address);
        Delivery delivery = Delivery.of(requirement, address);

        //when
        courierContainerService.deliveryToCourier(delivery);

        //then
        List<CourierContainer> courierContainer = courierContainerRepository.findByCourier(courier);
        Assertions.assertThat(courierContainer.get(0).getCourier().getCourierArea()).isEqualTo(CourierArea.SEOUL);
        Assertions.assertThat(courierContainer.get(0).getCourier()).isEqualTo(courier);
        Assertions.assertThat(courierContainer.get(0).getDelivery()).isEqualTo(delivery);

    }


    @Test
    void 여러지역에_택배를_보낼수있다() {
        //given
        //배송원 생성
        User userCourier1 = User.of("courier1", "kgh225222521@gmail.com", Role.USER);
        User userCourier2 = User.of("courier2", "kgh225222522@gmail.com", Role.USER);
        User userCourier3 = User.of("courier3", "kgh225222523@gmail.com", Role.USER);
        User userCourier4 = User.of("courier4", "kgh225222524@gmail.com", Role.USER);
        userRepository.save(userCourier1);
        userRepository.save(userCourier2);
        userRepository.save(userCourier3);
        userRepository.save(userCourier4);

        Courier courier1 = Courier.of(userCourier1, CourierArea.SEOUL);
        Courier courier2 = Courier.of(userCourier2, CourierArea.GYEONGGI_DO);
        Courier courier3 = Courier.of(userCourier3, CourierArea.ULSAN);
        Courier courier4 = Courier.of(userCourier4, CourierArea.INCHEON);
        courierRepository.save(courier1);
        courierRepository.save(courier2);
        courierRepository.save(courier3);
        courierRepository.save(courier4);

        //회원 생성
        User user1 = User.of("user1", "1234@gmail.com", Role.USER);
        User user2 = User.of("user2", "1234@gmail.com", Role.USER);
        User user3 = User.of("user3", "1234@gmail.com", Role.USER);
        User user4 = User.of("user4", "1234@gmail.com", Role.USER);
        userRepository.save(user1);
        Requirement requirement1 = new Requirement("집앞 ");
        Address address1 = Address.of(user1, "서울특별시 강남구 무슨동 1234", "12345", "서울집", "주소주소");
        addressRepository.save(address1);
        Delivery delivery1 = Delivery.of(requirement1, address1);
        userRepository.save(user2);
        Requirement requirement2 = new Requirement("경비실");
        Address address2 = Address.of(user2, "경기도 성남시 무슨동 11234", "12345", "경기집", "1층");
        addressRepository.save(address2);
        Delivery delivery2 = Delivery.of(requirement2, address2);
        userRepository.save(user3);
        Requirement requirement3 = new Requirement("집앞 담벼락 아래 굴");
        Address address3 = Address.of(user3, "울산광역시 남구 중앙로 201", "44675", "울산광역시", "신전동");
        addressRepository.save(address3);
        Delivery delivery3 = Delivery.of(requirement3, address3);
        userRepository.save(user4);
        Requirement requirement4 = new Requirement("올때 메로나");
        Address address4 = Address.of(user4, "인천광역시 남구동 연수구 인원재로 115 ", "12345", "인천집", "(동춘3동 923-5)");
        addressRepository.save(address4);
        Delivery delivery4 = Delivery.of(requirement4, address4);

        //when
        courierContainerService.deliveryToCourier(delivery1);
        courierContainerService.deliveryToCourier(delivery2);
        courierContainerService.deliveryToCourier(delivery3);
        courierContainerService.deliveryToCourier(delivery4);

        //then
        List<CourierContainer> courierContainer1 = courierContainerRepository.findByCourier(courier1);
        List<CourierContainer> courierContainer2 = courierContainerRepository.findByCourier(courier2);
        List<CourierContainer> courierContainer3 = courierContainerRepository.findByCourier(courier3);
        List<CourierContainer> courierContainer4 = courierContainerRepository.findByCourier(courier4);

        assertAll(
                () -> Assertions.assertThat(courierContainer1).isNotNull(),
                () -> Assertions.assertThat(courierContainer2).isNotNull(),
                () -> Assertions.assertThat(courierContainer3).isNotNull(),
                () -> Assertions.assertThat(courierContainer4).isNotNull(),
                () -> Assertions.assertThat(courierContainer1.get(0).getCourier().getCourierArea()).isEqualTo(CourierArea.SEOUL),
                () -> Assertions.assertThat(courierContainer2.get(0).getCourier().getCourierArea()).isEqualTo(CourierArea.GYEONGGI_DO),
                () -> Assertions.assertThat(courierContainer3.get(0).getCourier().getCourierArea()).isEqualTo(CourierArea.ULSAN),
                () -> Assertions.assertThat(courierContainer4.get(0).getCourier().getCourierArea()).isEqualTo(CourierArea.INCHEON),
                () -> Assertions.assertThat(courierContainer1.get(0).getDelivery()).isEqualTo(delivery1),
                () -> Assertions.assertThat(courierContainer2.get(0).getDelivery()).isEqualTo(delivery2),
                () -> Assertions.assertThat(courierContainer3.get(0).getDelivery()).isEqualTo(delivery3),
                () -> Assertions.assertThat(courierContainer4.get(0).getDelivery()).isEqualTo(delivery4)
        );

    }

    @Test
    void 한지역에_여러개의_택배를_보낼수있다() {
        //배송원인원수*10
        User userCourier1 = User.of("courier1", "kgh225222521@gmail.com", Role.USER);
        User userCourier2 = User.of("courier2", "kgh225222522@gmail.com", Role.USER);
        User userCourier3 = User.of("courier3", "kgh225222523@gmail.com", Role.USER);
        User userCourier4 = User.of("courier4", "kgh225222524@gmail.com", Role.USER);
        userRepository.save(userCourier1);
        userRepository.save(userCourier2);
        userRepository.save(userCourier3);
        userRepository.save(userCourier4);

        Courier courier1 = Courier.of(userCourier1, CourierArea.SEOUL);
        courierRepository.save(courier1);

        //회원 생성
        User user1 = User.of("user1", "1234@gmail.com", Role.USER);
        User user2 = User.of("user2", "1234@gmail.com", Role.USER);
        User user3 = User.of("user3", "1234@gmail.com", Role.USER);
        User user4 = User.of("user4", "1234@gmail.com", Role.USER);
        userRepository.save(user1);
        Requirement requirement1 = new Requirement("집앞 ");
        Address address1 = Address.of(user1, "서울특별시 강남구 무슨동 1234", "12345", "서울집", "주소주소");
        addressRepository.save(address1);
        Delivery delivery1 = Delivery.of(requirement1, address1);
        userRepository.save(user2);
        Requirement requirement2 = new Requirement("경비실");
        Address address2 = Address.of(user2, "서울특별시 성남시 무슨동 11234", "12345", "경기집", "1층");
        addressRepository.save(address2);
        Delivery delivery2 = Delivery.of(requirement2, address2);
        userRepository.save(user3);
        Requirement requirement3 = new Requirement("집앞 담벼락 아래 굴");
        Address address3 = Address.of(user3, "서울특별시 남구 중앙로 201", "44675", "울산광역시", "신전동");
        addressRepository.save(address3);
        Delivery delivery3 = Delivery.of(requirement3, address3);
        userRepository.save(user4);
        Requirement requirement4 = new Requirement("올때 메로나");
        Address address4 = Address.of(user4, "서울특별시 남구동 연수구 인원재로 115 ", "12345", "인천집", "(동춘3동 923-5)");
        addressRepository.save(address4);
        Delivery delivery4 = Delivery.of(requirement4, address4);

        //when
        courierContainerService.deliveryToCourier(delivery1);
        courierContainerService.deliveryToCourier(delivery2);
        courierContainerService.deliveryToCourier(delivery3);
        courierContainerService.deliveryToCourier(delivery4);

        //then
        List<CourierContainer> courierContainer = courierContainerRepository.findByCourier(courier1);

        assertAll(
                () -> Assertions.assertThat(courierContainer).isNotNull(),
                () -> Assertions.assertThat(courierContainer.size()).isEqualTo(4)
        );

    }

    @Test
    void 한_지역에서의_택배를_배송원들에게_분배_할수있다() {
        User userCourier1 = User.of("courier1", "kgh225222521@gmail.com", Role.USER);
        User userCourier2 = User.of("courier2", "kgh225222522@gmail.com", Role.USER);
        User userCourier3 = User.of("courier3", "kgh225222523@gmail.com", Role.USER);
        User userCourier4 = User.of("courier4", "kgh225222524@gmail.com", Role.USER);
        userRepository.save(userCourier1);
        userRepository.save(userCourier2);
        userRepository.save(userCourier3);
        userRepository.save(userCourier4);

        Courier courier1 = Courier.of(userCourier1, CourierArea.SEOUL);
        Courier courier2 = Courier.of(userCourier2, CourierArea.SEOUL);
        Courier courier3 = Courier.of(userCourier3, CourierArea.SEOUL);
        Courier courier4 = Courier.of(userCourier4, CourierArea.SEOUL);
        courierRepository.save(courier1);
        courierRepository.save(courier2);
        courierRepository.save(courier3);
        courierRepository.save(courier4);

        //회원 생성
        User user1 = User.of("user1", "1234@gmail.com", Role.USER);
        User user2 = User.of("user2", "1234@gmail.com", Role.USER);
        User user3 = User.of("user3", "1234@gmail.com", Role.USER);
        User user4 = User.of("user4", "1234@gmail.com", Role.USER);
        userRepository.save(user1);
        Requirement requirement1 = new Requirement("집앞 ");
        Address address1 = Address.of(user1, "서울특별시 강남구 무슨동 1234", "12345", "서울집", "주소주소");
        addressRepository.save(address1);
        Delivery delivery1 = Delivery.of(requirement1, address1);
        userRepository.save(user2);
        Requirement requirement2 = new Requirement("경비실");
        Address address2 = Address.of(user2, "서울특별시 성남시 무슨동 11234", "12345", "경기집", "1층");
        addressRepository.save(address2);
        Delivery delivery2 = Delivery.of(requirement2, address2);
        userRepository.save(user3);
        Requirement requirement3 = new Requirement("집앞 담벼락 아래 굴");
        Address address3 = Address.of(user3, "서울특별시 남구 중앙로 201", "44675", "울산광역시", "신전동");
        addressRepository.save(address3);
        Delivery delivery3 = Delivery.of(requirement3, address3);
        userRepository.save(user4);
        Requirement requirement4 = new Requirement("올때 메로나");
        Address address4 = Address.of(user4, "서울특별시 남구동 연수구 인원재로 115 ", "12345", "인천집", "(동춘3동 923-5)");
        addressRepository.save(address4);
        Delivery delivery4 = Delivery.of(requirement4, address4);

        //when
        for(int i=0;i<9;i++) {
            courierContainerService.deliveryToCourier(delivery1);
            courierContainerService.deliveryToCourier(delivery2);
            courierContainerService.deliveryToCourier(delivery3);
            courierContainerService.deliveryToCourier(delivery4);
        }

        //given
        List<Courier> couriers = courierRepository.findByCourierArea(CourierArea.SEOUL);
        List<CourierContainer> courierContainers1 = courierContainerRepository.findByCourier(couriers.get(0));
        List<CourierContainer> courierContainers2 = courierContainerRepository.findByCourier(couriers.get(1));
        List<CourierContainer> courierContainers3 = courierContainerRepository.findByCourier(couriers.get(2));
        List<CourierContainer> courierContainers4 = courierContainerRepository.findByCourier(couriers.get(3));

        assertAll(
                () -> Assertions.assertThat(courierContainers1.size()).isEqualTo(10),
                () -> Assertions.assertThat(courierContainers2.size()).isEqualTo(10),
                () -> Assertions.assertThat(courierContainers3.size()).isEqualTo(10),
                () -> Assertions.assertThat(courierContainers4.size()).isEqualTo(6)
        );



    }

}
