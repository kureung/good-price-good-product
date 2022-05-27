package kr.co.gpgp.domain.item.dto;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

@Getter
@NoArgsConstructor
public class ItemInfoDto {

    @NotBlank
    @Length(max = 30)
    private String name;

    @Range(min = 0, max = 10_000)
    private int weight;

    @NotBlank
    @Length(max = 30)
    private String code;

    @URL
    private String imageUrl;

    @NotBlank
    @Past
    private LocalDate releaseDate;

    private ItemInfoDto(
        String name,
        int weight,
        String code,
        String imageUrl,
        LocalDate releaseDate) {

        this.name = name;
        this.weight = weight;
        this.code = code;
        this.imageUrl = imageUrl;
        this.releaseDate = releaseDate;
    }

    public static ItemInfoDto of(
        String name,
        int weight,
        String code,
        String imageUrl,
        LocalDate releaseDate) {

        return new ItemInfoDto(name, weight, code, imageUrl, releaseDate);
    }

}
