package kr.co.gpgp.web.api.item;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NewItemCreateForm {

    private final String author;
    private final int price;
    private final int stockQuantity;
    private final String name;

}
