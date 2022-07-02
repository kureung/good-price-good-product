package kr.co.gpgp.domain.item;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class ItemSearchDto {

    private Long itemId;
    private String itemName;
    private int itemPrice;

    @QueryProjection
    public ItemSearchDto(Long itemId, String itemName, int itemPrice) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }

}
