package kr.co.gpgp.domain.orderline.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Data
public class OrderLineRequest {

    @NotBlank
    @Length(max = 30)
    private final String itemCode;

    @Range(min = 0, max = 10_000)
    private final int itemQuantity;

}
