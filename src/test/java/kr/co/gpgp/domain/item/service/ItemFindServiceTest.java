package kr.co.gpgp.domain.item.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import kr.co.gpgp.domain.item.entity.Item;
import kr.co.gpgp.domain.item.entity.ItemInfo;
import kr.co.gpgp.repository.item.ItemJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
                .weight(10)
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

}
