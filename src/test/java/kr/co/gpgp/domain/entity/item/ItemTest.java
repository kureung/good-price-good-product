package kr.co.gpgp.domain.entity.item;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

public class ItemTest {

    @ParameterizedTest
    @CsvSource(value = {
        "50, 20, 30",
        "50, 10, 40",
        "2, 1, 1",
        "1, 1, 0"
    })
    void 상품주문시_재고수량이_낮아진다(int stockQuantity, int quantity, int result) {
        ItemInfo info = ItemInfo.of("name", 10, "123", LocalDate.now(), "www.naver.com");
        Item item = Item.of(1000, stockQuantity, info);

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
        ItemInfo info = ItemInfo.of("name", 10, "123", LocalDate.now(), "www.naver.com");
        Item item = Item.of(1000, stockQuantity, info);

        assertThatThrownBy(() -> item.minusStock(quantity))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("재고가 부족합니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {
        "50, 20, 70",
        "50, 10, 60",
        "2, 1, 3",
        "1, 1, 2"
    })
    void 재고_추가시_재고수량이_높아진다(int stockQuantity, int quantity, int result) {
        ItemInfo info = ItemInfo.of("name", 10, "123", LocalDate.now(), "www.naver.com");
        Item item = Item.of(1000, stockQuantity, info);

        item.plusStock(quantity);

        assertThat(item.getStockQuantity()).isEqualTo(result);
    }

    @ParameterizedTest
    @CsvSource(value = {
        "999_999_999, 2",
        "999_999_999, 3",
    })
    void 재고_추가시_재고수량이_1억개를_넘어가면_예외가_발생한다(int stockQuantity, int quantity) {

        ItemInfo info = ItemInfo.of("name", 10, "123", LocalDate.now(), "www.naver.com");
        Item item = Item.of(1000, stockQuantity, info);

        assertThatThrownBy(() ->
            item.plusStock(quantity))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("재고는 1억개를 넘을 수 없습니다.");
    }

    @ParameterizedTest
    @MethodSource("updateItem")
    void name(Item item, Item newItem) {
        item.update(newItem.getPrice(),newItem.getStockQuantity(),newItem.getInfo());
        assertThat(item)
            .usingRecursiveComparison()
            .isEqualTo(newItem);
    }

    private static Stream<Arguments> updateItem() {
        return Stream.of(
            Arguments.of(
                Item.of(
                    1100,
                    11,
                    ItemInfo.of(
                        "item1",
                        11,
                        "321321",
                        LocalDate.now().minusDays(1),
                        "www.google.co.kr")
                ),

                Item.of(
                    1000,
                    10,
                    ItemInfo.of(
                        "item2",
                        12,
                        "123123",
                        LocalDate.now(),
                        "www.naver.com")
                )
            ),

            Arguments.of(
                Item.of(
                    1000,
                    10,
                    ItemInfo.of(
                        "item2",
                        12,
                        "123123",
                        LocalDate.now(),
                        "www.naver.com")
                ),

                Item.of(
                    1100,
                    11,
                    ItemInfo.of(
                        "item1",
                        11,
                        "321321",
                        LocalDate.now().minusDays(1),
                        "www.google.co.kr")
                )
            )
        );
    }
}