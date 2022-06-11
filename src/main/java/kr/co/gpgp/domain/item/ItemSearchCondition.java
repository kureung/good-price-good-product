package kr.co.gpgp.domain.item;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemSearchCondition {

    private String itemName;
    private Integer priceGoe;
    private Integer priceLoe;

    @Builder
    private ItemSearchCondition(String itemName, Integer priceGoe, Integer priceLoe) {
        this.itemName = itemName;
        this.priceGoe = priceGoe;
        this.priceLoe = priceLoe;
    }

}
