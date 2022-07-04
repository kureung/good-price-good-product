package kr.co.gpgp.repository.item;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import kr.co.gpgp.domain.item.Item;
import kr.co.gpgp.domain.item.ItemInfo;
import kr.co.gpgp.domain.item.ItemSearchCondition;
import kr.co.gpgp.domain.item.ItemSearchDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ItemJpaRepositoryCustomTests {

    @Autowired
    private ItemRepositoryCustomImpl sut;

    @Autowired
    private ItemJpaRepository itemJpaRepository;

    @Test
    void 상품_검색_기능_테스트() {
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

        ItemSearchCondition condition = ItemSearchCondition.from("1");

        // when
        int PageSize = 10;
        PageRequest pageRequest = PageRequest.of(0, PageSize);
        Page<ItemSearchDto> itemResponses = sut.searchItem(condition, pageRequest);
        List<ItemSearchDto> content = itemResponses.getContent();

        // then
        assertThat(content.size()).isEqualTo(PageSize);
    }

}