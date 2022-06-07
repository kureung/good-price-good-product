package kr.co.gpgp.domain.delivery.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import kr.co.gpgp.domain.address.entity.Address;
import kr.co.gpgp.domain.delivery.entity.enums.DeliveryStatusImpl;
import kr.co.gpgp.domain.delivery.exception.DeliveryStatusOverflowException;
import kr.co.gpgp.domain.requirement.entity.Requirement;
import kr.co.gpgp.domain.user.entity.Role;
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
            delivery.next();
            delivery.next();
            delivery.next();
            delivery.next();
            delivery.next();
            delivery.next();
        }).hasMessage("이미 완료된 배송입니다.");
    }

    @Test
    void 배송순서가_정상적으로_돌아가면_예외는_발생되지_않는다() {
        Delivery delivery = Delivery.of(requirement, address);

        assertThat(delivery.getStatus().get()).isEqualTo(DeliveryStatusImpl.ACCEPT.get());
        delivery.next();
        assertThat(delivery.getStatus().get()).isEqualTo(DeliveryStatusImpl.INSTRUCT.get());
        delivery.next();
        assertThat(delivery.getStatus().get()).isEqualTo(DeliveryStatusImpl.DEPARTURE.get());
        delivery.next();
        assertThat(delivery.getStatus().get()).isEqualTo(DeliveryStatusImpl.FINAL_DELIVERY.get());
        delivery.next();
        assertThat(delivery.getStatus().get()).isEqualTo(DeliveryStatusImpl.NONE_TRACKING.get());

        assertThat(delivery)
                .isNotInstanceOf(DeliveryStatusOverflowException.class);
    }

}
