package kr.co.gpgp.domain.item;

import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@Getter
public class ItemInfo {

    private String name;

    private String author;

    @Builder
    private ItemInfo(String name, String author) {
        this.name = name;
        this.author = author;
    }

}
