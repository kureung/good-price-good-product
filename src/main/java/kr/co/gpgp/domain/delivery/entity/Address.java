package kr.co.gpgp.domain.delivery.entity;

import java.util.regex.Pattern;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roadName;        //도로명
    private String zipCode;         //우편번호
    private String name;            //이름

    private String requirement;     //요청사항 null ok
    private String detailed;        //상세주소


    @Builder
    public Address(String roadName, String detailed, String zipCode, String name,
        String requirement
    ) {
        AddressValidator.verifyZipCodes(zipCode);
        AddressValidator.verifyRoadName(roadName);
        AddressValidator.verifyName(name);
        AddressValidator.verifyRequirement(requirement);
        AddressValidator.verifyDetailed(detailed);

        this.roadName = roadName;
        this.zipCode = zipCode;
        this.name = name;
        this.requirement = requirement;
        this.detailed = detailed;
    }

    protected static class AddressValidator {

        private static final int NAME_MAX_LEN = 20;
        private static final int NAME_MIN_LEN = 1;
        private static final int ROADNAME_MAX_LEN = 40;
        private static final int ROADNAME_MIN_LEN = 9;
        protected static final int REQUIREMENT_MAX_LEN = 18;

        private static final Pattern OLD_ZIP_CODE = Pattern.compile("^\\d{3}-\\d{3}$");
        private static final Pattern NEW_ZIP_CODE = Pattern.compile("^\\d{5}$");


        private static void verifyZipCodes(String zipCode) {
            if (zipCode == null || zipCode.isBlank()) {
                throw new IllegalArgumentException("우편번호는 비어있을 수 없습니다.");
            }
            if (!(OLD_ZIP_CODE.matcher(zipCode).matches() ||
                NEW_ZIP_CODE.matcher(zipCode).matches())
            ) {
                throw new IllegalArgumentException("우편번호 형식이 맞지 않습니다.");
            }
        }

        private static void verifyRoadName(String roadName) {
            if (roadName == null || roadName.isBlank()) {
                throw new IllegalArgumentException("도로명은 비어있을 수 없습니다.");
            }
            if (!(numberBetween(ROADNAME_MIN_LEN, ROADNAME_MAX_LEN, roadName.length()))) {
                throw new IllegalArgumentException("도로명 길이가 맞지 않습니다.");
            }
        }

        private static void verifyName(String name) {
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("주소 이름은 비어있을 수 없습니다.");
            }
            if (!numberBetween(NAME_MIN_LEN, NAME_MAX_LEN, name.length())) {
                throw new IllegalArgumentException("주소 이름 길이가 맞지 않습니다.");
            }
        }

        private static void verifyRequirement(String requirement) {
            if (!numberBetween(0, REQUIREMENT_MAX_LEN, requirement.length())) {
                throw new IllegalArgumentException("요청사항은 " + REQUIREMENT_MAX_LEN + "자를 넘을수 없습니다.");
            }
        }


        private static void verifyDetailed(String detailed) {
            if (detailed.length() > 17) {
                throw new IllegalArgumentException("주소에_자세한_설명은17자를 넘을수 업습니다.");
            }
        }


        private static boolean numberBetween(int min, int max, int number) {
            return number >= min && number <= max;
        }

    }

}
