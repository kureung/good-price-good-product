package kr.co.gpgp.web.api.item;

import kr.co.gpgp.domain.item.Item;
import kr.co.gpgp.domain.item.ItemInfo;
import kr.co.gpgp.domain.user.Seller;
import org.springframework.stereotype.Component;

@Component
public class ItemAdapter {

    public static Item toEntity(NewItemCreateForm form, Seller seller) {
        ItemInfo info = ItemInfo.builder()
                .author(form.getAuthor())
                .name(form.getName())
                .build();

        return Item.builder()
                .info(info)
                .stockQuantity(form.getStockQuantity())
                .price(form.getPrice())
                .seller(seller)
                .build();
    }

}
