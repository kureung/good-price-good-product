package kr.co.gpgp.domain.orderline;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import kr.co.gpgp.domain.item.Item;
import kr.co.gpgp.domain.orderline.OrderLine;
import kr.co.gpgp.web.exception.ErrorCode;
import org.junit.jupiter.api.Test;

public class OrderLineTest {

    @Test
    void 주문_상품_생성시_재고가_부족하면_예외가_발생한다() {
        Item item = Item.builder()
                .stockQuantity(30)
                .build();
        int count = 40;
        assertThatIllegalStateException()
                .isThrownBy(() -> OrderLine.of(item, count))
                .withMessage(ErrorCode.STOCK_OUT_OF_RANGE.getMessage());
    }

    @Test
    void 주문_상품_생성시_재고가_충분하면_에외가_발생하지_않는다() {
        Item item = Item.builder()
                .stockQuantity(30)
                .build();
        int count = 30;

        assertDoesNotThrow(() -> OrderLine.of(item, count));
    }

    @Test
    void 주문이_취소_되면_주문_수량만큼_재고가_증가한다() {
        // given
        Item item = Item.builder()
                .stockQuantity(30)
                .build();
        int count = 30;
        int quantityBeforePurchase = item.getStockQuantity();

        OrderLine orderLine = OrderLine.of(item, count);

        // when
        orderLine.cancel();

        // then
        assertThat(item.getStockQuantity())
                .isEqualTo(quantityBeforePurchase);
    }

}
