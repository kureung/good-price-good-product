package kr.co.gpgp.domain.item.dto;

import javax.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Getter
@NoArgsConstructor
public class ItemDtoRequest {

    @Range(min = 0, max = 1_000_000_000)
    private int price;

    @Range(min = 0, max = 10_000)
    private int stockQuantity;

    @Valid
    private ItemInfoDto itemInfoDto;

    private ItemDtoRequest(int price, int stockQuantity, ItemInfoDto itemInfoDto) {
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.itemInfoDto = itemInfoDto;
    }

    public static ItemDtoRequest of(int price, int stockQuantity, ItemInfoDto itemInfoDto) {
        return new ItemDtoRequest(price, stockQuantity, itemInfoDto);
    }

}
