package kr.co.gpgp.web.api.item;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import kr.co.gpgp.domain.item.Item;
import kr.co.gpgp.domain.item.ItemInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Getter
@NoArgsConstructor
public class ItemRequest {

    @Range(min = 0, max = 1_000_000_000)
    private int price;

    @Range(min = 0, max = 10_000)
    private int stockQuantity;

    @NotBlank
    @Length(max = 30)
    private String name;
    @NotBlank
    private String author;

    @Builder
    private ItemRequest(int price, int stockQuantity, String name, int weight, String code, String author, LocalDate releaseDate) {
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.name = name;
        this.author = author;
    }

    public Item toEntity() {
        ItemInfo info = ItemInfo.builder()
                .author(author)
                .name(name)
                .build();

        return Item.builder()
                .price(price)
                .info(info)
                .stockQuantity(stockQuantity)
                .build();
    }

}
