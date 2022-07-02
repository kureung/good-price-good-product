package kr.co.gpgp.domain.item;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import kr.co.gpgp.repository.item.ItemJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ItemFindServiceTest {

    @Autowired
    private ItemJpaRepository itemJpaRepository;

    @Autowired
    private ItemFindService sut;

    @Test
    void 상품_조회_테스트() {
        // given
        ItemInfo info = ItemInfo.builder()
                .name("name")
                .build();
        Item item = Item.builder()
                .info(info)
                .build();
        Item savedItem = itemJpaRepository.save(item);
        Long itemId = savedItem.getId();

        // when, then
        Item findItem = assertDoesNotThrow(() -> sut.findOne(itemId));
        assertNotNull(findItem);
        assertInstanceOf(Item.class, findItem);
    }

    @Test
    void 상품_검색_테스트() {
        // given
        for (int i = 0; i < 100; i++) {
            ItemInfo info = ItemInfo.builder()
                    .name("item" + i)
                    .build();

            Item item = Item.builder()
                    .info(info)
                    .price(i)
                    .stockQuantity(100)
                    .build();

            itemJpaRepository.save(item);
        }

        ItemSearchCondition condition = ItemSearchCondition.builder()
                .itemName("i")
                .build();

        // when
        int PageSize = 10;
        PageRequest pageRequest = PageRequest.of(0, PageSize);
        Page<ItemSearchDto> itemResponses = sut.search(condition, pageRequest);
        List<ItemSearchDto> content = itemResponses.getContent();

        // then
        assertThat(content.size()).isEqualTo(PageSize);

    }

}
