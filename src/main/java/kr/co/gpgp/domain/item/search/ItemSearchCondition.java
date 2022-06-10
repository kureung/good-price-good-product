package kr.co.gpgp.domain.item.search;

import lombok.Data;

@Data
public class ItemSearchCondition {
    private String itemName;
    private Integer ageGoe;
    private Integer ageLoe;
}
