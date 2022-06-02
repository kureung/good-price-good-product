package kr.co.gpgp.domain.item.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.stream.Stream;
import kr.co.gpgp.domain.item.entity.Item;
import kr.co.gpgp.domain.item.entity.ItemInfo;
import kr.co.gpgp.domain.item.repository.ItemRepository;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ItemCommandServiceTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemCommandService itemCommandService;


    @ParameterizedTest
    @MethodSource("toSaveProvideItem")
    void 상품_등록_테스트(Item item) {

        // when
        Long itemId = itemCommandService.save(item);
        Item findItem = itemRepository.findById(itemId)
            .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));

        // then
        assertThat(item).isEqualTo(findItem);
    }

    private static Stream<Arguments> toSaveProvideItem() {
        return Stream.of(
            Arguments.of(
                Item.of(
                    1000,
                    100,
                    ItemInfo.of(
                        "item1",
                        500,
                        "123",
                        LocalDate.now(),
                        "www.naver.com"))
            ),

            Arguments.of(
                Item.of(
                    2000,
                    300,
                    ItemInfo.of(
                        "item2",
                        600,
                        "456",
                        LocalDate.now().minusMonths(1),
                        "www.google.co.kr"))
            ),

            Arguments.of(
                Item.of(
                    3000,
                    200,
                    ItemInfo.of(
                        "item3",
                        700,
                        "789",
                        LocalDate.now().minusDays(2),
                        "www.daum.net"))
            )
        );
    }


    @ParameterizedTest
    @MethodSource("toUpdateProvideItem")
    void 상품_수정_테스트(Item item, Item newItem) {

        // given
        Item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId();

        // when
        itemCommandService.update(itemId, newItem);
        Item findItem = itemRepository.findById(itemId)
            .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));

        // then
        assertThat(findItem).usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(newItem);
    }

    private static Stream<Arguments> toUpdateProvideItem() {
        return Stream.of(
            Arguments.of(
                Item.of(
                    1000,
                    100,
                    ItemInfo.of(
                        "item1",
                        500,
                        "123",
                        LocalDate.now(),
                        "www.naver.com"
                    )),
                Item.of(
                    2000,
                    300,
                    ItemInfo.of(
                        "item2",
                        600,
                        "456",
                        LocalDate.now().minusMonths(1),
                        "www.google.co.kr"
                    ))
            ),

            Arguments.of(
                Item.of(
                    2000,
                    300,
                    ItemInfo.of(
                        "item2",
                        600,
                        "456",
                        LocalDate.now().minusMonths(1),
                        "www.google.co.kr"
                    )),
                Item.of(
                    1000,
                    100,
                    ItemInfo.of(
                        "item1",
                        500,
                        "123",
                        LocalDate.now(),
                        "www.naver.com"
                    ))
            ),

            Arguments.of(
                Item.of(
                    3000,
                    200,
                    ItemInfo.of(
                        "item3",
                        700,
                        "789",
                        LocalDate.now().minusDays(2),
                        "www.daum.net"
                    )),
                Item.of(
                    2000,
                    300,
                    ItemInfo.of(
                        "item2",
                        600,
                        "456",
                        LocalDate.now().minusMonths(1),
                        "www.google.co.kr"
                    ))
            )
        );
    }

}