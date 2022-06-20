package kr.co.gpgp.domain.order;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.util.List;
import java.util.UUID;
import kr.co.gpgp.domain.address.Address;
import kr.co.gpgp.domain.delivery.Delivery;
import kr.co.gpgp.domain.item.Item;
import kr.co.gpgp.domain.orderline.OrderLine;
import kr.co.gpgp.domain.requirement.Requirement;
import kr.co.gpgp.domain.user.Role;
import kr.co.gpgp.domain.user.User;
import kr.co.gpgp.web.exception.ErrorCode;
import org.junit.jupiter.api.Test;

public class OrderTest {

    @Test
    void 전체_주문_가격_조회_테스트() {
        // given
        Item item = Item.builder()
                .stockQuantity(11).build();
        List<OrderLine> orderLines = List.of(OrderLine.of(item, 10));

        // when
        User user = User.of("name", "abc@naver.com", Role.USER);
        Requirement requirement = new Requirement("요청사항");
        Address address = Address.of(user, "roadName123456", "12345", "name", "detailAddress");
        Delivery delivery = Delivery.of(requirement, address);

        String orderCode = UUID.randomUUID().toString();
        Order order = Order.of(user, delivery, orderLines, orderCode);


        // then
        assertInstanceOf(Integer.class, order.getTotalPrice());
    }

    @Test
    void 결제완료_단계에서_주문취소시_예외가_발생하지_않는다() {
        // given
        Item item = Item.builder()
                .stockQuantity(11).build();
        List<OrderLine> orderLines = List.of(OrderLine.of(item, 10));

        // when
        User user = User.of("name", "abc@naver.com", Role.USER);
        Requirement requirement = new Requirement("요청사항");
        Address address = Address.of(user, "roadName123456", "12345", "name", "detailAddress");
        Delivery delivery = Delivery.of(requirement, address);

        String orderCode = UUID.randomUUID().toString();
        Order order = Order.of(user, delivery, orderLines, orderCode);

        // then
        assertDoesNotThrow(order::cancel);
    }

    @Test
    void 상품준비중_단계에서_주문취소시_예외가_발생하지_않는다() {
        // given
        Item item = Item.builder()
                .stockQuantity(11).build();
        List<OrderLine> orderLines = List.of(OrderLine.of(item, 10));

        // when
        User user = User.of("name", "abc@naver.com", Role.USER);
        Requirement requirement = new Requirement("요청사항");
        Address address = Address.of(user, "roadName123456", "12345", "name", "detailAddress");
        Delivery delivery = Delivery.of(requirement, address);

        String orderCode = UUID.randomUUID().toString();
        Order order = Order.of(user, delivery, orderLines, orderCode);
        delivery.nextStepInstruct();

        // then
        assertThatCode(order::cancel).doesNotThrowAnyException();
    }

    @Test
    void 배송지시_단계에서_주문취소시_예외가_발생한다() {
        // given
        Item item = Item.builder()
                .stockQuantity(11).build();
        List<OrderLine> orderLines = List.of(OrderLine.of(item, 10));

        // when
        User user = User.of("name", "abc@naver.com", Role.USER);
        Requirement requirement = new Requirement("요청사항");
        Address address = Address.of(user, "roadName123456", "12345", "name", "detailAddress");
        Delivery delivery = Delivery.of(requirement, address);

        String orderCode = UUID.randomUUID().toString();
        Order order = Order.of(user, delivery, orderLines, orderCode);
        delivery.nextStepInstruct();
        delivery.nextStepDeparture();

        // then
        assertThatThrownBy(order::cancel).isInstanceOf(IllegalStateException.class).hasMessage(ErrorCode.UNABLE_TO_CANCEL_ORDER.getMessage());
    }

    @Test
    void 배송중_단계에서_주문취소시_예외가_발생한다() {
        // given
        Item item = Item.builder()
                .stockQuantity(11).build();
        List<OrderLine> orderLines = List.of(OrderLine.of(item, 10));

        // when
        User user = User.of("name", "abc@naver.com", Role.USER);
        Requirement requirement = new Requirement("요청사항");
        Address address = Address.of(user, "roadName123456", "12345", "name", "detailAddress");
        Delivery delivery = Delivery.of(requirement, address);

        String orderCode = UUID.randomUUID().toString();
        Order order = Order.of(user, delivery, orderLines, orderCode);
        delivery.nextStepInstruct();
        delivery.nextStepDeparture();
        delivery.nextStepInTransit();

        // then
        assertThatThrownBy(order::cancel).isInstanceOf(IllegalStateException.class).hasMessage(ErrorCode.UNABLE_TO_CANCEL_ORDER.getMessage());
    }

    @Test
    void 배송완료_단계에서_주문취소시_예외가_발생한다() {
        // given
        Item item = Item.builder()
                .stockQuantity(11).build();
        List<OrderLine> orderLines = List.of(OrderLine.of(item, 10));

        // when
        User user = User.of("name", "abc@naver.com", Role.USER);
        Requirement requirement = new Requirement("요청사항");
        Address address = Address.of(user, "roadName123456", "12345", "name", "detailAddress");
        Delivery delivery = Delivery.of(requirement, address);

        String orderCode = UUID.randomUUID().toString();
        Order order = Order.of(user, delivery, orderLines, orderCode);
        delivery.nextStepInstruct();
        delivery.nextStepDeparture();
        delivery.nextStepInTransit();
        delivery.nextStepFinalDelivery();

        // then
        assertThatThrownBy(order::cancel).isInstanceOf(IllegalStateException.class).hasMessage(ErrorCode.UNABLE_TO_CANCEL_ORDER.getMessage());
    }

}
