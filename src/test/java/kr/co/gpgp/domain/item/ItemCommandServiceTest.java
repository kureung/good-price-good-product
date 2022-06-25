package kr.co.gpgp.domain.item;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import kr.co.gpgp.repository.item.ItemJpaRepository;
import kr.co.gpgp.web.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ItemCommandServiceTest {

    @Autowired
    private ItemJpaRepository itemJpaRepository;

    @Autowired
    private ItemCommandService sut;

    @Test
    void 상품_등록_테스트() {
        ItemInfo info = ItemInfo.builder()
                .build();
        Item item = Item.builder()
                .info(info)
                .build();
        assertNotNull(assertDoesNotThrow(() -> sut.save(item)));
    }

    @Test
    void 상품_중복_등록시_예외가_발생한다() {
        ItemInfo info = ItemInfo.builder()
                .name("name")
                .build();
        Item item1 = Item.builder()
                .info(info)
                .build();

        sut.save(item1);

        Item item2 = Item.builder()
                .info(info)
                .build();

        assertThatThrownBy(() -> sut.save(item2))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(ErrorCode.ITEM_DUPLICATE_CHECK_ERROR.getMessage());
    }

    @Test
    void 상품_수정_테스트() {
        ItemInfo info = ItemInfo.builder()
                .build();
        Item item = Item.builder()
                .info(info)
                .build();
        Item newItem = Item.builder()
                .info(info)
                .build();

        // given
        Item savedItem = itemJpaRepository.save(item);
        Long itemId = savedItem.getId();

        // when
        Long updatedItemId = sut.update(itemId, newItem);

        // then
        assertNotNull(itemJpaRepository.findById(updatedItemId));
    }

}
