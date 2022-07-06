package kr.co.gpgp.domain.courier;

import static kr.co.gpgp.domain.courier.CourierArea.GYEONGGI_DO;
import static kr.co.gpgp.domain.courier.CourierArea.INCHEON;
import static kr.co.gpgp.domain.courier.CourierArea.SEOUL;
import static kr.co.gpgp.domain.courier.CourierArea.ULSAN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import kr.co.gpgp.common.ServiceTest;
import kr.co.gpgp.domain.address.Address;
import kr.co.gpgp.domain.delivery.Delivery;
import kr.co.gpgp.domain.requirement.Requirement;
import kr.co.gpgp.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.SpyBean;

public class CourierContainerServiceTest extends ServiceTest {

    @SpyBean
    private CourierContainerService mockCourierContainerService;

    @Mock
    private Delivery deliveryMockito;

    @BeforeEach
    void setup() {
        //given
        String[][] ac = {
                {"서울특별시 강남구 무슨동 1234", "12345", "서울집", "주소주소"},
                {"서울특별시 성남시 무슨동 11234", "12345", "경기집", "1층"},
                {"서울특별시 남구 중앙로 201", "44675", "울산광역시", "신전동"},
                {"서울특별시 남구 중앙로 201", "44675", "울산광역시", "신전동"},
                {"경기도 성남시 무슨동 11234", "12345", "경기집", "1층"},
                {"울산광역시 남구 중앙로 201", "44675", "울산광역시", "신전동"},
                {"인천광역시 남구동 연수구 인원재로 115 ", "12345", "인천집", "(동춘3동 923-5)"}
        };
        CourierArea[] courierAreas = {SEOUL, SEOUL, SEOUL, SEOUL, GYEONGGI_DO, ULSAN, INCHEON};
        int len = ac.length;

        User[] userCouriers = new User[len];
        Courier[] couriers = new Courier[len];

        User[] users = new User[len];
        Requirement[] requirements = new Requirement[len];
        Address[] addresss = new Address[len];
        Delivery[] deliverys = new Delivery[len];

        for (int i = 0; i < len; i++) {
            userCouriers[i] = User.of("courier" + (i + 1), "courier" + (i + 1) + "@gmail.com");
            userRepository.save(userCouriers[i]);
            couriers[i] = Courier.of(userCouriers[i], courierAreas[i]);
            courierRepository.save(couriers[i]);

            users[i] = User.of("user" + (i + 1), "courier" + (i + 1) + "@gmail.com");
            userRepository.save(users[i]);
            requirements[i] = new Requirement(users[i].getName() + "님의 요청사항");
            addresss[i] = Address.of(users[i], ac[i][0], ac[i][1], ac[i][2], ac[i][3]);
            addressRepository.save(addresss[i]);
            deliverys[i] = Delivery.of(requirements[i], addresss[i]);
            deliveryRepository.save(deliverys[i]);
        }

        //when
        for (int i = 0; i < users.length; i++) {
            courierContainerService.sellerToCourier(deliverys[i]);
        }

        for (int i = 0; i < 30; i++) {
            courierContainerService.sellerToCourier(deliverys[0]);
        }

        deliveryMockito = deliverys[0];
    }

    @Test
    void 판매원이_택배를_배송사에게_전달한다() {
        mockCourierContainerService.sellerToCourier(deliveryMockito);
    }

    @Test
    void 여러지역에_택배를_보낼수있다() {
        List<Courier> courierSeoul = courierRepository.findByCourierArea(SEOUL);
        List<Courier> courierGyeonggiDo = courierRepository.findByCourierArea(GYEONGGI_DO);
        List<Courier> courierUlsan = courierRepository.findByCourierArea(ULSAN);
        List<Courier> courierIncheon = courierRepository.findByCourierArea(INCHEON);

        assertAll(
                () -> assertThat(courierSeoul).isNotNull(),
                () -> assertThat(courierGyeonggiDo).isNotNull(),
                () -> assertThat(courierUlsan).isNotNull(),
                () -> assertThat(courierIncheon).isNotNull()
        );

    }

    @Test
    void 한지역에_여러개의_택배를_보낼수있다() {
        List<Courier> courierSeoul = courierRepository.findByCourierArea(SEOUL);
        List<CourierContainer> courierContainer = courierContainerRepository.findByCourierList(courierSeoul.get(0));

        assertThat(courierContainer.size()).isEqualTo(10);
    }

    @Test
    void 한_지역에서의_택배를_배송원들에게_분배_할수있다() {

        List<Courier> courierList = courierRepository.findByCourierArea(SEOUL);
        List<CourierContainer> courierContainers1 = courierContainerRepository.findByCourierList(courierList.get(0));
        List<CourierContainer> courierContainers2 = courierContainerRepository.findByCourierList(courierList.get(1));
        List<CourierContainer> courierContainers3 = courierContainerRepository.findByCourierList(courierList.get(2));
        List<CourierContainer> courierContainers4 = courierContainerRepository.findByCourierList(courierList.get(3));

        assertAll(() -> assertThat(courierList).isNotNull(),
                () -> assertThat(courierContainers1.size()).isEqualTo(10),
                () -> assertThat(courierContainers2.size()).isEqualTo(10),
                () -> assertThat(courierContainers3.size()).isEqualTo(10),
                () -> assertThat(courierContainers4.size()).isEqualTo(4)
        );

    }

}
