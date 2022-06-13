package kr.co.gpgp.web.api.item;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
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

    @Range(min = 0, max = 10_000)
    private int weight;

    @NotBlank
    @Length(max = 30)
    private String code;

    private String imageUrl;

    @PastOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    @Builder
    private ItemRequest(int price, int stockQuantity, String name, int weight, String code, String imageUrl, LocalDate releaseDate) {
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.name = name;
        this.weight = weight;
        this.code = code;
        this.imageUrl = imageUrl;
        this.releaseDate = releaseDate;
    }

}
