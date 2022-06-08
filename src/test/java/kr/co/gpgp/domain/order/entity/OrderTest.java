package kr.co.gpgp.domain.order.entity;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.util.List;
import kr.co.gpgp.domain.address.entity.Address;
import kr.co.gpgp.domain.delivery.entity.Delivery;
import kr.co.gpgp.domain.item.entity.Item;
import kr.co.gpgp.domain.orderline.entity.OrderLine;
import kr.co.gpgp.domain.requirement.entity.Requirement;
import kr.co.gpgp.domain.user.entity.Role;
import kr.co.gpgp.domain.user.entity.User;
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

        Order order = Order.of(user, delivery, orderLines);

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

        Order order = Order.of(user, delivery, orderLines);

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

        Order order = Order.of(user, delivery, orderLines);
        delivery.next();

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

        Order order = Order.of(user, delivery, orderLines);
        delivery.next();
        delivery.next();

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

        Order order = Order.of(user, delivery, orderLines);
        delivery.next();
        delivery.next();
        delivery.next();

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

        Order order = Order.of(user, delivery, orderLines);
        delivery.next();
        delivery.next();
        delivery.next();
        delivery.next();

        // then
        assertThatThrownBy(order::cancel).isInstanceOf(IllegalStateException.class).hasMessage(ErrorCode.UNABLE_TO_CANCEL_ORDER.getMessage());
    }
}
