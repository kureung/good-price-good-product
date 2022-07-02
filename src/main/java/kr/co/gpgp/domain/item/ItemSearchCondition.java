package kr.co.gpgp.domain.item;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemSearchCondition {

    private String itemName;
    private String author;

    @Builder
    private ItemSearchCondition(String itemName, String author) {
        this.itemName = itemName;
        this.author = author;
    }

}
