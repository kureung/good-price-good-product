package kr.co.gpgp.web.api.item;

import kr.co.gpgp.domain.item.Item;
import kr.co.gpgp.domain.item.ItemInfo;
import org.springframework.stereotype.Component;

@Component
public class ItemAdapter {

    public static Item toEntity(NewItemCreateForm form) {
        ItemInfo info = ItemInfo.builder()
                .author(form.getAuthor())
                .name(form.getName())
                .build();

        return Item.builder()
                .info(info)
                .stockQuantity(form.getStockQuantity())
                .price(form.getPrice())
                .build();
    }

}
