package kr.co.gpgp.domain.entity.item;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.stream.Stream;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
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
        ItemInfo info = ItemInfo.create("name", 10, "123", LocalDate.now());
        Item item = Item.create(1000, stockQuantity, "www.naver.com", info);

        item.removeStock(quantity);

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
        ItemInfo info = ItemInfo.create("name", 10, "123", LocalDate.now());
        Item item = Item.create(1000, stockQuantity, "www.naver.com", info);

        assertThatThrownBy(() -> item.removeStock(quantity))
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
        ItemInfo info = ItemInfo.create("name", 10, "123", LocalDate.now());
        Item item = Item.create(1000, stockQuantity, "www.naver.com", info);

        item.addStock(quantity);

        assertThat(item.getStockQuantity()).isEqualTo(result);
    }

    @ParameterizedTest
    @CsvSource(value = {
        "999_999_999, 2",
        "999_999_999, 3",
    })
    void 재고_추가시_재고수량이_1억개를_넘어가면_예외가_발생한다(int stockQuantity, int quantity) {

        ItemInfo info = ItemInfo.create("name", 10, "123", LocalDate.now());
        Item item = Item.create(1000, stockQuantity, "www.naver.com", info);

        assertThatThrownBy(() ->
            item.addStock(quantity))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("재고는 1억개를 넘을 수 없습니다.");
    }

    @ParameterizedTest
    @MethodSource("updateItem")
    void name(Item item, Item newItem) {
        item.update(newItem);
        assertThat(item)
            .usingRecursiveComparison()
            .isEqualTo(newItem);
    }

    private static Stream<Arguments> updateItem() {
        ItemInfo info = ItemInfo.create("name", 10, "123123", LocalDate.now());
        Item item = Item.create(1000, 10, "https://www.naver.com/", info);

        return Stream.of(
            Arguments.of(
                Item.create(
                    1100,
                    11,
                    "https://www.google.co.kr/",
                    ItemInfo.create("item1", 11, "321321", LocalDate.now().minusDays(1))
                ),

                Item.create(
                    1000,
                    10,
                    "https://www.naver.com/",
                    ItemInfo.create("item2", 12, "123123", LocalDate.now())
                )
            ),

            Arguments.of(
                Item.create(
                    1000,
                    10,
                    "https://www.naver.com/",
                    ItemInfo.create("item2", 12, "123123", LocalDate.now())
                ),

                Item.create(
                    1100,
                    11,
                    "https://www.google.co.kr/",
                    ItemInfo.create("item1", 11, "321321", LocalDate.now().minusDays(1))
                )
            )
        );
    }
}