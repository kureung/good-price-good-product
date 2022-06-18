package kr.co.gpgp.domain.requirement;

import static lombok.AccessLevel.PRIVATE;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import kr.co.gpgp.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;


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


    private Requirement(Long id,String message) {
        validatMessage(message);
        this.message = message;
        this.id = id;
    }


    private static void validatMessage(String message) {
        final int REQUIREMENT_MAX_LEN = 18;

        if (message.length() > REQUIREMENT_MAX_LEN) {
            throw new IllegalArgumentException("요청사항은 " + REQUIREMENT_MAX_LEN + "자를 넘을수 없습니다.");
        }

    }

    @Getter
    @NoArgsConstructor(access = PRIVATE)
    public static class RequirementDto {

        private Long id;
        private String message;

        private RequirementDto(Long id, String message) {
            this.id = id;
            this.message = message;
        }

        public static RequirementDto of(RequirementRequest requirementRequest) {
            return new RequirementDto(requirementRequest.getId(),requirementRequest.getMessage());
        }
        public static Requirement toEntity(RequirementDto requirementDto){
            return new Requirement(requirementDto.getId(), requirementDto.getMessage());
        }
    }

    public static Requirement of(RequirementRequest requirementRequest) {
        return new Requirement(requirementRequest.getMessage());
    }

    public static Requirement of(String message) {
        return new Requirement(message);
    }
}
