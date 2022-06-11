package kr.co.gpgp.domain.requirement.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import kr.co.gpgp.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 연관관계 --------------------------------------------------
// [회원] 1:N [요청사항] 단반향(Requirement)
// [배송] 1:1 [요청사항] 단반향(Delivery)

@Entity
@Getter
@NoArgsConstructor
public class Requirement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String message;

    public Requirement(String message) {
        validatMessage(message);
        this.message = message;
    }

    private static void validatMessage(String message) {
        final int REQUIREMENT_MAX_LEN = 18;

        if (message.length() > REQUIREMENT_MAX_LEN) {
            throw new IllegalArgumentException("요청사항은 " + REQUIREMENT_MAX_LEN + "자를 넘을수 없습니다.");
        }

    }

}
