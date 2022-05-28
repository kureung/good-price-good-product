package kr.co.gpgp.domain.delivery.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Requirement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    public Requirement(String message) {
        validatMessage(message);
        this.message = message;
    }

    private static void validatMessage(String message) {
        final int REQUIREMENT_MAX_LEN = 18;

        if (message.length() >= REQUIREMENT_MAX_LEN) {
            throw new IllegalArgumentException("요청사항은 " + REQUIREMENT_MAX_LEN + "자를 넘을수 없습니다.");
        }

    }

}
