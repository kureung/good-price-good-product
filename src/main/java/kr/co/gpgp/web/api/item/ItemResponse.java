package kr.co.gpgp.web.api.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.gpgp.domain.item.Item;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemResponse {

    @JsonIgnore
    private Long id;

    private int price;

    private int stockQuantity;

    private String name;

    private String author;

    @QueryProjection
    @Builder
    private ItemResponse(Long id, int price, int stockQuantity, String name, String author) {
        this.id = id;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.name = name;
        this.author = author;
    }

    public static ItemResponse toDto(Item item) {
        return ItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .author(item.getAuthor())
                .stockQuantity(item.getStockQuantity())
                .build();
    }

}
