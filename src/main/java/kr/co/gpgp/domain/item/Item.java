package kr.co.gpgp.domain.item;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import java.util.Objects;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import kr.co.gpgp.domain.common.BaseEntity;
import kr.co.gpgp.web.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Item extends BaseEntity {

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

    public void plusStock(int quantity) {
        validationAddStock(quantity);
        stockQuantity += quantity;
    }


    public void update(int price, int stockQuantity, ItemInfo info) {
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.info = info;
    }

    public String getName() {
        return info.getName();
    }

    public String getAuthor() {
        return info.getAuthor();
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

    private void validationRemoveStock(int quantity) {
        int restStock = stockQuantity - quantity;
        if (restStock < 0) {
            throw new IllegalStateException(ErrorCode.STOCK_OUT_OF_RANGE.getMessage());
        }
    }

    private void validationAddStock(int quantity) {
        int restStock = stockQuantity + quantity;
        if (restStock > 1_000_000_000) {
            throw new IllegalStateException(ErrorCode.STOCK_OUT_OF_RANGE.getMessage());
        }
    }
}
