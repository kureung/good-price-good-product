package kr.co.gpgp.domain.delivery.entity;

import static org.assertj.core.api.Assertions.*;

import kr.co.gpgp.domain.delivery.exception.DeliveryStatusOverflowException;
import org.junit.jupiter.api.Test;

public class DeliveryTest {

    @Test
    void 이미_완료된_배송에서_다음상태로변경하면_예외가_발생한다() {
        assertThatThrownBy(() -> {
                Delivery delivery = new Delivery();
                delivery.next();
                delivery.next();
                delivery.next();
                delivery.next();
                delivery.next();
                delivery.next();
            }
        ).hasMessage("이미 완료된 배송입니다.");

    }
    @Test
    void 배송준비단계에서_다음상태로변경하면_예외는_발생하지_않는다() {
        Delivery delivery = new Delivery();
        delivery.next();
        delivery.next();
        assertThat(delivery)
            .isNotInstanceOf(DeliveryStatusOverflowException.class);

    }

}
