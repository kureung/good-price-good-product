package kr.co.gpgp.web.api.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewItemCreateForm {

    private String author = "";
    private int price;
    private int stockQuantity;
    private String name = "";

}
