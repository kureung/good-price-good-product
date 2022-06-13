package kr.co.gpgp.domain.delivery;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import kr.co.gpgp.domain.address.Address;
import kr.co.gpgp.domain.requirement.Requirement;
import kr.co.gpgp.domain.user.Role;
import kr.co.gpgp.domain.user.User;
import org.junit.jupiter.api.Test;

public class DeliveryTest {

    Requirement requirement = new Requirement("요청사항");
    User user = User.of("회원", "kgh2252@naver.com", Role.USER);
    Address address = Address.of(user, "1234567890", "12345", "12345", "12345");

    @Test
    void 이미_완료된_배송에서_다음상태로변경하면_예외가_발생한다() {

        assertThatThrownBy(() -> {
            Delivery delivery = Delivery.of(requirement, address);
            delivery.nextStepDeparture();
            delivery.nextStepInstruct();
            delivery.nextStepInTransit();
            delivery.nextStepFinalDelivery();
        }).hasMessage("변경하려는 이전 상태가 아니라 다음 상태로 갈 수 없습니다.");
    }

    @Test
    void 배송순서가_정상적으로_돌아가면_예외는_발생되지_않는다() {
        Delivery delivery = Delivery.of(requirement, address);
        assertThat(delivery.getStatus()).isEqualTo(DeliveryStatus.ACCEPT);

        delivery.nextStepInstruct();
        assertThat(delivery.getStatus()).isEqualTo(DeliveryStatus.INSTRUCT);
        delivery.nextStepDeparture();
        assertThat(delivery.getStatus()).isEqualTo(DeliveryStatus.DEPARTURE);
        delivery.nextStepInTransit();
        assertThat(delivery.getStatus()).isEqualTo(DeliveryStatus.IN_TRANSIT);
        delivery.nextStepFinalDelivery();
        assertThat(delivery.getStatus()).isEqualTo(DeliveryStatus.FINAL_DELIVERY);

        assertThat(delivery)
                .isNotInstanceOf(DeliveryStatusOverflowException.class);
    }

}
