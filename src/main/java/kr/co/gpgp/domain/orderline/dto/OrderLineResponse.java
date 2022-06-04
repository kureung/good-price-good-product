package kr.co.gpgp.domain.orderline.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Data
public class OrderLineResponse {


    @NotBlank
    @Length(max = 30)
    private final String itemName;

    @NotBlank
    @Length(max = 30)
    private final String itemCode;

    @Range(min = 0, max = 1_000_000_000)
    private final int itemPrice;

    @Range(min = 0, max = 10_000)
    private final int itemQuantity;

}
