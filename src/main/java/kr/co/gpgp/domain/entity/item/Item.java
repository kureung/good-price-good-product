package kr.co.gpgp.domain.entity.item;

import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
public class Item {

    @Id
    @GeneratedValue
    private Long id;


    private int price;
    private int stockQuantity;
    private String imageUrl;

    @Embedded
    private ItemInfo info;

    private Item(int price, int stockQuantity, String imageUrl,
        ItemInfo info) {
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.imageUrl = imageUrl;
        this.info = info;
    }

    public static Item create(int price, int stockQuantity, String imageUrl, ItemInfo info) {
        return new Item(price, stockQuantity, imageUrl, info);
    }

    public void removeStock(int quantity) {
        validationRemoveStock(quantity);
        stockQuantity -= quantity;
    }

    private void validationRemoveStock(int quantity) {
        int restStock = stockQuantity - quantity;
        if (restStock < 0) {
            throw new IllegalStateException("재고가 부족합니다.");
        }
    }

    public void addStock(int quantity) {
        validationAddStock(quantity);
        stockQuantity += quantity;
    }

    private void validationAddStock(int quantity) {
        int restStock = stockQuantity + quantity;
        if (restStock > 1_000_000_000) {
            throw new IllegalStateException("재고는 1억개를 넘을 수 없습니다.");
        }
    }

    public void update(Item newItem) {
        this.price = newItem.getPrice();
        this.stockQuantity = newItem.getStockQuantity();
        this.imageUrl = newItem.getImageUrl();
        this.info = newItem.getInfo();
    }
}
