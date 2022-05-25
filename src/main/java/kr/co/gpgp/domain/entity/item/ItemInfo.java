package kr.co.gpgp.domain.entity.item;

import java.time.LocalDate;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Getter
public class ItemInfo {

    private String name;
    private int weight;
    private String code;
    private LocalDate releaseDate;

    private ItemInfo(String name, int weight, String code, LocalDate releaseDate) {
        this.name = name;
        this.weight = weight;
        this.code = code;
        this.releaseDate = releaseDate;
    }

    public static ItemInfo create(String name, int weight, String code, LocalDate releaseDate) {
        return new ItemInfo(name, weight, code, releaseDate);
    }
}