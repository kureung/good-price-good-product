package kr.co.gpgp.domain.order;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;
import javax.persistence.criteria.Order;
import kr.co.gpgp.domain.delivery.entity.Delivery;
import kr.co.gpgp.domain.item.entity.Item;
import kr.co.gpgp.domain.item.entity.ItemInfo;
import kr.co.gpgp.domain.orderline.OrderLine;
import kr.co.gpgp.domain.user.entity.User;
import kr.co.gpgp.web.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class OrderTest {

    @ParameterizedTest
    @MethodSource("주문_가격_조회_테스트_용도")
    void 전체_주문_가격_조회_테스트(List<OrderLine> orderLines, int result) {

        // given
        User user = User.builder().build();
        Delivery delivery = new Delivery();

        // when
        Order order = Order.of(user, delivery, orderLines);

        // then
        assertThat(order.getToTalPrice()).isEqualTo(result);
    }

    private static Stream<Arguments> 주문_가격_조회_테스트_용도() {

        var defaultStockQuantity = 999;
        var defaultItemInfo = ItemInfo.of("item1", 10, "123", LocalDate.now(), "www.naver.com");

        return Stream.of(
            Arguments.of(
                List.of(
                    OrderLine.of(Item.of(1000, defaultStockQuantity, defaultItemInfo), 10),
                    OrderLine.of(Item.of(2000, defaultStockQuantity, defaultItemInfo), 20),
                    OrderLine.of(Item.of(3000, defaultStockQuantity, defaultItemInfo), 30)),
                1000 * 10 + 2000 * 20 + 3000 * 30),

            Arguments.of(
                List.of(
                    OrderLine.of(Item.of(4000, defaultStockQuantity, defaultItemInfo), 40),
                    OrderLine.of(Item.of(5000, defaultStockQuantity, defaultItemInfo), 50),
                    OrderLine.of(Item.of(6000, defaultStockQuantity, defaultItemInfo), 60)),
                4000 * 40 + 5000 * 50 + 6000 * 60)
        );
    }

    @ParameterizedTest
    @MethodSource("주문_가격_조회_테스트_용도")
    void 결제완료_단계에서_주문취소시_예외가_발생하지_않는다() {

        // given
        User user = User.builder().build();
        Delivery delivery = new Delivery();

        // when
        Order order = Order.of(user, delivery, orderLines);

        // then
        assertThatCode(() -> order.cancel())
            .doesNotThrowAnyException();

    }

    @Test
    void 상품준비중_단계에서_주문취소시_예외가_발생하지_않는다() {

        // given
        User user = User.builder().build();
        Delivery delivery = new Delivery();

        // when
        Order order = Order.of(user, delivery, orderLines);
        delivery.next();

        // then
        assertThatCode(() -> order.cancel())
            .doesNotThrowAnyException();
    }

    @Test
    void 배송지시_단계에서_주문취소시_예외가_발생한다() {

        // given
        User user = User.builder().build();
        Delivery delivery = new Delivery();

        // when
        Order order = Order.of(user, delivery, orderLines);
        delivery.next();
        delivery.next();

        // then
        assertThatThrownBy(() ->
            order.cancel())
            .isInstanceOf(IllegalStateException.class)
            .hasMessage(ErrorCode.UNABLE_TO_CANCEL_ORDER.getMessage());
    }

    @Test
    void 배송중_단계에서_주문취소시_예외가_발생한다() {

        // given
        User user = User.builder().build();
        Delivery delivery = new Delivery();

        // when
        Order order = Order.of(user, delivery, orderLines);
        delivery.next();
        delivery.next();
        delivery.next();

        // then
        assertThatThrownBy(() ->
            order.cancel())
            .isInstanceOf(IllegalStateException.class)
            .hasMessage(ErrorCode.UNABLE_TO_CANCEL_ORDER.getMessage());
    }

    @Test
    void 배송완료_단계에서_주문취소시_예외가_발생한다() {

        // given
        User user = User.builder().build();
        Delivery delivery = new Delivery();

        // when
        Order order = Order.of(user, delivery, orderLines);
        delivery.next();
        delivery.next();
        delivery.next();
        delivery.next();

        // then
        assertThatThrownBy(() ->
            order.cancel())
            .isInstanceOf(IllegalStateException.class)
            .hasMessage(ErrorCode.UNABLE_TO_CANCEL_ORDER.getMessage());
    }
}
