package kr.co.gpgp.domain.item.service.dto;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import kr.co.gpgp.domain.item.dto.ItemRequest;
import kr.co.gpgp.domain.item.dto.ItemResponse;
import kr.co.gpgp.domain.item.entity.Item;
import kr.co.gpgp.domain.item.entity.ItemInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ItemDtoServiceTest {

    @Autowired
    private ItemDtoService sut;

    @Test
    void item을_itemDtoResponse로_변환_테스트() {
        ItemInfo info = ItemInfo.builder()
                .weight(10)
                .name("item1")
                .build();

        Item item = Item.builder()
                .price(1000)
                .info(info)
                .stockQuantity(10)
                .build();

        ItemResponse response = assertDoesNotThrow(() -> sut.toDto(item));
        assertNotNull(response);
        assertInstanceOf(ItemResponse.class, response);
    }

    @Test
    void itemDtoRequest를_item으로_변환_테스트() {
        ItemRequest request = ItemRequest.builder().build();
        Item item = assertDoesNotThrow(() -> sut.toEntity(request));
        assertNotNull(item);
        assertInstanceOf(Item.class, item);
    }

}
