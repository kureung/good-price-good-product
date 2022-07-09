package kr.co.gpgp.domain.courier;

import kr.co.gpgp.common.ServiceTest;
import kr.co.gpgp.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CourierServiceTest extends ServiceTest {

    private User user;
    private Courier courier;

    @BeforeEach
    void setup() {
        user = User.of("courier", "test@gmail.com");
        userRepository.save(user);
        courier = Courier.of(user, CourierArea.SEOUL);
        courierRepository.save(courier);
    }

    @Test
    void 배송원은_배달을_출발한다() {
        courierService.deliverying(user.getId());
    }

    @Test
    void 배송원은_배달을_완료한다() {
        courierService.completion(user.getId());
    }

    @Test
    void 배송원은_배달을_기다린다() {
        courierService.waiting(user.getId());
    }

}
