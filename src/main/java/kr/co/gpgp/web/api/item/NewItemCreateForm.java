package kr.co.gpgp.web.api.item;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
public class NewItemCreateForm {

    private String author = "";
    private int price;
    private int stockQuantity;
    private String name = "";

}
