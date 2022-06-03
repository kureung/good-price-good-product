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
class ItemFindServiceTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemFindService itemFindService;

    @ParameterizedTest
    @MethodSource("toFindOneProvideItem")
    void 상품_조회_테스트(Item item) {

        // given
        Item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId();

        // when
        Item findItem = itemFindService.findOne(itemId);

        // then
        assertThat(item).usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(findItem);
    }

    private static Stream<Arguments> toFindOneProvideItem() {
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
                    )
                )
            )
        );
    }
}