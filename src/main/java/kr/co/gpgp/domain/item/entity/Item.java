package kr.co.gpgp.domain.item.entity;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import kr.co.gpgp.web.exception.ErrorCode;
import lombok.Builder;
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

    @Builder
    private Item(int price, int stockQuantity, ItemInfo info) {
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.info = info;
    }

    public void minusStock(int quantity) {
        validationRemoveStock(quantity);
        stockQuantity -= quantity;
    }

    private void validationRemoveStock(int quantity) {
        int restStock = stockQuantity - quantity;
        if (restStock < 0) {
            throw new IllegalStateException(ErrorCode.STOCK_OUT_OF_RANGE.getMessage());
        }
    }

    public void plusStock(int quantity) {
        validationAddStock(quantity);
        stockQuantity += quantity;
    }

    private void validationAddStock(int quantity) {
        int restStock = stockQuantity + quantity;
        if (restStock > 1_000_000_000) {
            throw new IllegalStateException(ErrorCode.STOCK_OUT_OF_RANGE.getMessage());
        }
    }

    public void update(int price, int stockQuantity, ItemInfo info) {
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.info = info;
    }

    public String getName() {
        return info.getName();
    }

    public String getCode() {
        return info.getCode();
    }

    public int getWeight() {
        return info.getWeight();
    }

    public LocalDate getReleaseDate() {
        return info.getReleaseDate();
    }

    public String getImageUrl() {
        return info.getImageUrl();
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;
        if (o==null || getClass()!=o.getClass()) return false;
        Item item = (Item) o;
        return getPrice()==item.getPrice() && getStockQuantity()==item.getStockQuantity() && Objects.equals(getId(), item.getId()) && Objects.equals(getInfo(), item.getInfo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPrice(), getStockQuantity(), getInfo());
    }
}
