package kr.co.gpgp.domain.item.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import kr.co.gpgp.domain.item.dto.ItemResponse;
import kr.co.gpgp.domain.item.entity.Item;
import kr.co.gpgp.domain.item.entity.ItemInfo;
import kr.co.gpgp.domain.item.search.ItemSearchCondition;
import kr.co.gpgp.repository.item.ItemJpaRepository;
import kr.co.gpgp.repository.item.ItemRepositoryCustom;
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
    private ItemRepositoryCustom sut;

    @Autowired
    private ItemJpaRepository itemJpaRepository;

    @Test
    void 상품_검색_기능_테스트() {
        // given
        for (int i = 0; i < 100; i++) {
            ItemInfo info = ItemInfo.builder()
                    .weight(i)
                    .name("item" + i)
                    .code(i + "")
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
        Page<ItemResponse> itemResponses = sut.searchItem(condition, pageRequest);
        List<ItemResponse> content = itemResponses.getContent();

        // then
        assertThat(content.size()).isEqualTo(PageSize);
    }

}