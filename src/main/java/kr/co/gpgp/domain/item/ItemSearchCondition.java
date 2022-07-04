package kr.co.gpgp.domain.item;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ItemSearchCondition {

    private String itemNameOrAuthor;

    private ItemSearchCondition(String itemNameOrAuthor) {
        this.itemNameOrAuthor = itemNameOrAuthor;
    }

    public static ItemSearchCondition from(String itemNameOrAuthor) {
        return new ItemSearchCondition(itemNameOrAuthor);
    }

}
