package kr.co.gpgp.domain.orderline.dto;

import static lombok.AccessLevel.PRIVATE;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
@Builder
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
