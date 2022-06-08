package kr.co.gpgp.domain.item.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import kr.co.gpgp.domain.item.entity.Item;
import kr.co.gpgp.domain.item.entity.ItemInfo;
import kr.co.gpgp.domain.item.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ItemCommandServiceTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemCommandService sut;

    @Test
    void 상품_등록_테스트() {
        ItemInfo info = ItemInfo.builder()
                .weight(10)
                .build();
        Item item = Item.builder()
                .info(info)
                .build();
        assertNotNull(assertDoesNotThrow(() -> sut.save(item)));
    }

    @Test
    void 상품_수정_테스트() {
        ItemInfo info = ItemInfo.builder()
                .weight(10)
                .build();
        Item item = Item.builder()
                .info(info)
                .build();
        Item newItem = Item.builder()
                .info(info)
                .build();

        // given
        Item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId();

        // when
        Long updatedItemId = sut.update(itemId, newItem);

        // then
        assertNotNull(itemRepository.findById(updatedItemId));
    }
}
