package kr.co.gpgp.domain.delivery.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import kr.co.gpgp.domain.address.entity.Address;
import kr.co.gpgp.domain.delivery.entity.enums.DeliveryStatus;
import kr.co.gpgp.domain.delivery.exception.DeliveryStatusOverflowException;
import kr.co.gpgp.domain.requirement.entity.Requirement;
import kr.co.gpgp.domain.user.entity.Role;
import kr.co.gpgp.domain.user.entity.Role.*;
import kr.co.gpgp.domain.user.entity.User;
import org.junit.jupiter.api.Test;


public class DeliveryTest {

    Requirement requirement = new Requirement("요청사항");
    User user = User.of("asdf", "kgh2252@naver.com", Role.USER);
    Address address = Address.of(user, "1234567890", "12345", "12345", "12345");

    @Test
    void 이미_완료된_배송에서_다음상태로변경하면_예외가_발생한다() {

        assertThatThrownBy(() -> {
            Delivery delivery = Delivery.of(requirement, address);
            delivery.next(Role.SELLER);
            delivery.next(Role.SELLER);
            delivery.next(Role.COURIER);
            delivery.next(Role.COURIER);
            delivery.next(Role.COURIER);
            delivery.next(Role.COURIER);
        }).hasMessage("이미 완료된 배송입니다.");
    }

    @Test
    void 배송순서가_정상적으로_돌아가면_예외는_발생되지_않는다() {
        Delivery delivery = Delivery.of(requirement, address);

        assertThat(delivery.getStatus()).isEqualTo(DeliveryStatus.ACCEPT);
        delivery.next(Role.SELLER);
        assertThat(delivery.getStatus()).isEqualTo(DeliveryStatus.INSTRUCT);
        delivery.next(Role.SELLER);
        assertThat(delivery.getStatus()).isEqualTo(DeliveryStatus.DEPARTURE);

        delivery.next(Role.COURIER);
        assertThat(delivery.getStatus()).isEqualTo(DeliveryStatus.FINAL_DELIVERY);
        delivery.next(Role.COURIER);
        assertThat(delivery.getStatus()).isEqualTo(DeliveryStatus.NONE_TRACKING);

        assertThat(delivery)
                .isNotInstanceOf(DeliveryStatusOverflowException.class);
    }

}
