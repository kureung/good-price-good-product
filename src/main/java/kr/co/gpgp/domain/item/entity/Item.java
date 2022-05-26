package kr.co.gpgp.domain.item.entity;

import static javax.persistence.GenerationType.IDENTITY;
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
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private int price;
    private int stockQuantity;

    @Embedded
    private ItemInfo info;

    private Item(int price, int stockQuantity, ItemInfo info) {
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.info = info;
    }

    public static Item of(int price, int stockQuantity, ItemInfo info) {
        return new Item(price, stockQuantity, info);
    }

    public void minusStock(int quantity) {
        validationRemoveStock(quantity);
        stockQuantity -= quantity;
    }

    private void validationRemoveStock(int quantity) {
        int restStock = stockQuantity - quantity;
        if (restStock < 0) {
            throw new IllegalStateException("재고가 부족합니다.");
        }
    }

    public void plusStock(int quantity) {
        validationAddStock(quantity);
        stockQuantity += quantity;
    }

    private void validationAddStock(int quantity) {
        int restStock = stockQuantity + quantity;
        if (restStock > 1_000_000_000) {
            throw new IllegalStateException("재고는 1억개를 넘을 수 없습니다.");
        }
    }

    public void update(int price, int stockQuantity, ItemInfo info) {
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.info = info;
    }
}
