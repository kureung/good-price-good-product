package kr.co.gpgp.domain.item.entity;

import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDate;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@Getter
public class ItemInfo {

    private String name;
    private int weight;
    private String code;
    private LocalDate releaseDate;
    private String imageUrl;

    private ItemInfo(
        String name,
        int weight,
        String code,
        LocalDate releaseDate,
        String imageUrl) {

        this.name = name;
        this.weight = weight;
        this.code = code;
        this.releaseDate = releaseDate;
        this.imageUrl = imageUrl;
    }

    public static ItemInfo of(
        String name,
        int weight,
        String code,
        LocalDate releaseDate,
        String imageUrl) {

        return new ItemInfo(name, weight, code, releaseDate, imageUrl);
    }
}