package kr.co.gpgp.domain.item.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ItemTest {

    @ParameterizedTest
    @CsvSource(value = {
            "50, 20, 30",
            "50, 10, 40",
            "2, 1, 1",
            "1, 1, 0"
    })
    void 상품주문시_재고수량이_낮아진다(int stockQuantity, int quantity, int result) {
        Item item = Item.builder()
                .stockQuantity(stockQuantity)
                .build();

        item.minusStock(quantity);

        assertThat(item.getStockQuantity()).isEqualTo(result);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "20, 50",
            "10, 50",
            "1, 2",
            "0, 1"
    })
    void 상품주문시_재고가_부족하면_예외가_발생한다(int stockQuantity, int quantity) {
        Item item = Item.builder()
                .stockQuantity(stockQuantity)
                .build();

        assertThatThrownBy(() -> item.minusStock(quantity))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("재고의 범위를 벗어났습니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {
            "50, 20, 70",
            "50, 10, 60",
            "2, 1, 3",
            "1, 1, 2"
    })
    void 재고_추가시_재고수량이_높아진다(int stockQuantity, int quantity, int result) {
        Item item = Item.builder()
                .stockQuantity(stockQuantity)
                .build();

        item.plusStock(quantity);

        assertThat(item.getStockQuantity()).isEqualTo(result);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "999_999_999, 2",
            "999_999_999, 3",
    })
    void 재고_추가시_재고수량이_1억개를_넘어가면_예외가_발생한다(int stockQuantity, int quantity) {
        Item item = Item.builder()
                .stockQuantity(stockQuantity)
                .build();

        assertThatThrownBy(() ->
                item.plusStock(quantity))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("재고의 범위를 벗어났습니다.");
    }

    @Test
    void 상품_수정_테스트() {

        Item item = Item.builder()
                .build();

        Item newItem = Item.builder()
                .build();

        item.update(newItem.getPrice(), newItem.getStockQuantity(), newItem.getInfo());
        assertThat(item)
                .usingRecursiveComparison()
                .isEqualTo(newItem);
    }

}
