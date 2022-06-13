package kr.co.gpgp.domain.delivery;


import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import kr.co.gpgp.domain.address.Address;
import kr.co.gpgp.domain.requirement.Requirement;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class DeliveryStatusTest {

    private Address address;
    private Requirement requirement;
    private Delivery delivery = Delivery.of(requirement, address);


    @Test
    void 초기상태가_ACCEPT_가_아니라면예외가_발생한다() {
        Assertions.assertThat(delivery.getStatus()).isNotEqualTo(DeliveryStatus.INSTRUCT);
        Assertions.assertThat(delivery.getStatus()).isNotEqualTo(DeliveryStatus.DEPARTURE);
        Assertions.assertThat(delivery.getStatus()).isNotEqualTo(DeliveryStatus.IN_TRANSIT);
        Assertions.assertThat(delivery.getStatus()).isNotEqualTo(DeliveryStatus.FINAL_DELIVERY);
        Assertions.assertThat(delivery.getStatus()).isNotEqualTo(DeliveryStatus.PURCHASE_CONFIRMATION);
        Assertions.assertThat(delivery.getStatus()).isNotEqualTo(DeliveryStatus.WITHDRAW_ORDER);
    }

    @Test
    void 초기상태가_ACCEPT_가_맞으면_예외는_발생되지_않는다() {
        Assertions.assertThat(delivery.getStatus()).isEqualTo(DeliveryStatus.ACCEPT);
    }

    @Test
    void 초기상태에서_마지막상태까지_갈수있으면_성공() {

        Assertions.assertThat(delivery.getStatus()).isEqualTo(DeliveryStatus.ACCEPT);
        delivery.nextStepInstruct();
        delivery.nextStepDeparture();
        delivery.nextStepInTransit();
        delivery.nextStepFinalDelivery();
        delivery.nextStepPurchaseConfirmation();
        Assertions.assertThat(delivery.getStatus()).isEqualTo(DeliveryStatus.PURCHASE_CONFIRMATION);

    }

    @Test
    void 현재상태와_다르게_상태변경을_하면_예외가_발생한다() {
        delivery.nextStepInstruct();
        delivery.nextStepDeparture();
        delivery.nextStepInTransit();
        assertThatThrownBy(() ->delivery.nextStepInstruct())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("변경하려는 이전 상태가 아니라 다음 상태로 갈 수 없습니다.");

    }

    @Test
    void 상태별로_상태메시지를_가져올수_있다면_성공() {
        assertThatThrownBy(
                () -> assertAll(
                        () -> Assertions.assertThat(delivery.getStatusMessage()).isEqualTo(DeliveryStatus.ACCEPT.getMessage()),
                        () -> assertThatThrownBy(() -> delivery.nextStepInstruct()),
                        () -> Assertions.assertThat(delivery.getStatusMessage()).isEqualTo(DeliveryStatus.INSTRUCT.getMessage()),
                        () -> assertThatThrownBy(() -> delivery.nextStepDeparture()),
                        () -> Assertions.assertThat(delivery.getStatusMessage()).isEqualTo(DeliveryStatus.DEPARTURE.getMessage()),
                        () -> assertThatThrownBy(() -> delivery.nextStepInTransit()),
                        () -> Assertions.assertThat(delivery.getStatusMessage()).isEqualTo(DeliveryStatus.DEPARTURE.getMessage()),
                        () -> assertThatThrownBy(() -> delivery.nextStepFinalDelivery()),
                        () -> Assertions.assertThat(delivery.getStatusMessage()).isEqualTo(DeliveryStatus.IN_TRANSIT.getMessage()),
                        () -> assertThatThrownBy(() -> delivery.nextStepPurchaseConfirmation()),
                        () -> Assertions.assertThat(delivery.getStatusMessage()).isEqualTo(DeliveryStatus.FINAL_DELIVERY.getMessage())
                )
        ).isNotInstanceOf(IllegalArgumentException.class)
                .isNotNull();
    }

    @Test
    void 배송취소상태로_만들수있다면_성공() {
        delivery.cancle();

    }

}
