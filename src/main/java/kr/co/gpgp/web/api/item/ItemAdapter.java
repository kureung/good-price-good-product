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

    public static NewItemCreateForm toForm(Item item) {
        return new NewItemCreateForm(item.getAuthor(),
                item.getPrice(),
                item.getStockQuantity(),
                item.getName());
    }

}
