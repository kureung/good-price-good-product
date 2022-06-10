package kr.co.gpgp.domain.item.search;

import lombok.Data;

@Data
public class ItemSearchCondition {
    private String itemName;
    private Integer priceGoe;
    private Integer priceLoe;
}
