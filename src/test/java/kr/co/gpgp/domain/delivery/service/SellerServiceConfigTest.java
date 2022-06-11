package kr.co.gpgp.domain.delivery.service;

import kr.co.gpgp.domain.address.Address;
import kr.co.gpgp.domain.delivery.Delivery;
import kr.co.gpgp.domain.requirement.entity.Requirement;
import kr.co.gpgp.domain.user.Role;
import kr.co.gpgp.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SellerServiceConfigTest extends ServiceConfigTest {

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
    void 판매자는_결제완료_상태를_변경할수있다() {

    }

    @Test
    void 판매자는_상품준비중_상태를_변경할수있다() {

    }

    @Test
    void 판매자는_결제완료_주문취소시_상태를_변경할수있다() {

    }

    @Test
    void 판매자는_상품준비_주문취소시_상태를_변경할수있다() {

    }

    @Test
    void 판매자는_결제완료_상태를_변경할수있다_실패의경우() {

    }

    @Test
    void 판매자는_상품준비중_상태를_변경할수있다_실패의경우() {

    }

    @Test
    void 판매자는_결제완료_주문취소시_상태를_변경할수있다_실패의경우() {

    }

    @Test
    void 판매자는_상품준비_주문취소시_상태를_변경할수있다_실패의경우() {

    }

}
