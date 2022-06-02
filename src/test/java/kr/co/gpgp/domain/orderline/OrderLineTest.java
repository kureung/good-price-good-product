package kr.co.gpgp.domain.orderline;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.stream.Stream;
import kr.co.gpgp.domain.item.entity.Item;
import kr.co.gpgp.domain.item.entity.ItemInfo;
import kr.co.gpgp.web.exception.ErrorCode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class OrderLineTest {

    @ParameterizedTest
    @MethodSource("부족한_재고의_아이템_제공")
    void 주문_상품_생성시_재고가_부족하면_예외가_발생한다(Item item, int count) {
        assertThatThrownBy(() ->
            OrderLine.of(item, count))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage(ErrorCode.STOCK_OUT_OF_RANGE.getMessage());
    }

    private static Stream<Arguments> 부족한_재고의_아이템_제공() {
        int defaultPrice = 1000;
        ItemInfo defaultInfo = ItemInfo.of("item1", 100, "123", LocalDate.now(), "www.naver.com");

        return Stream.of(
            Arguments.of(
                Item.of(defaultPrice, 10, defaultInfo),
                11
            ),

            Arguments.of(
                Item.of(defaultPrice, 20, defaultInfo),
                40
            ),

            Arguments.of(
                Item.of(defaultPrice, 100, defaultInfo),
                121
            )
        );
    }

    @ParameterizedTest
    @MethodSource("충분한_재고의_아이템_제공")
    void 주문_상품_생성시_재고가_충분하면_에외가_발생하지_않는다(Item item, int count) {
        assertThatCode(() -> OrderLine.of(item, count))
            .doesNotThrowAnyException();
    }

    private static Stream<Arguments> 충분한_재고의_아이템_제공() {
        int defaultPrice = 1000;
        ItemInfo defaultInfo = ItemInfo.of("item1", 100, "123", LocalDate.now(), "www.naver.com");

        return Stream.of(
            Arguments.of(
                Item.of(defaultPrice, 10, defaultInfo),
                9
            ),

            Arguments.of(
                Item.of(defaultPrice, 20, defaultInfo),
                20
            ),

            Arguments.of(
                Item.of(defaultPrice, 100, defaultInfo),
                1
            )
        );
    }

    @ParameterizedTest
    @MethodSource("충분한_제고_설정_아이템")
    void 주문이_취소_되면_주문_수량만큼_재고가_증가한다(Item item, int count) {

        // given
        int quantityBeforePurchase = item.getStockQuantity();
        OrderLine orderLine = OrderLine.of(item, count);

        // when
        orderLine.cancel();

        // then
        assertThat(item.getStockQuantity()).isEqualTo(quantityBeforePurchase);
    }

    private static Stream<Arguments> 충분한_제고_설정_아이템() {
        int defaultPrice = 1000;
        ItemInfo defaultInfo = ItemInfo.of("item1", 100, "123", LocalDate.now(), "www.naver.com");

        return Stream.of(
            Arguments.of(
                Item.of(defaultPrice, 100, defaultInfo),
                9
            ),

            Arguments.of(
                Item.of(defaultPrice, 100, defaultInfo),
                20
            ),

            Arguments.of(
                Item.of(defaultPrice, 100, defaultInfo),
                1
            )
        );
    }

    @ParameterizedTest
    @MethodSource("주문상품_전체_가격_조회를_위한_인자_제공")
    void 주문상품_전체_가격_조회_테스트(OrderLine orderLine, int result) {
        assertThat(orderLine.getTotalPrice()).isEqualTo(result);
    }

    private static Stream<Arguments> 주문상품_전체_가격_조회를_위한_인자_제공() {

        ItemInfo defaultInfo = ItemInfo.of("item1", 100, "123", LocalDate.now(), "www.naver.com");

        return Stream.of(
            Arguments.of(
                OrderLine.of(Item.of(100, 100, defaultInfo), 50),
                100 * 50
            ),

            Arguments.of(
                OrderLine.of(Item.of(200, 100, defaultInfo), 40),
                200 * 40
            ),

            Arguments.of(
                OrderLine.of(Item.of(10000, 100, defaultInfo), 1),
                10000
            )
        );
    }
}
