package kr.co.gpgp.web.api.item;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewItemCreateForm {

    private String author = "";
    private int price;
    private int stockQuantity;
    private String name = "";

}
