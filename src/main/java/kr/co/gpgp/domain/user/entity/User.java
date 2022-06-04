package kr.co.gpgp.domain.user.entity;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private Seller seller;
    @Enumerated(EnumType.STRING)
    private Role role; //oauth2 login
    private String name;
    private String email;


    private User( String name, String email,Role role) {

        UserValidator.verifyName(name);
        UserValidator.verifyEmail(email);

        this.name = name;
        this.email = email;
        this.role = role;
    }
    public static User of(String name,String email, Role role){
        return new User(name,email,role);
    }

    public User updateEmail(String email){
        UserValidator.verifyEmail(email);
        this.email=email;

        return this;
    }

    public void updateName(String name){
        UserValidator.verifyName(name);
        this.name= name;
    }


    //@Builder override 재정의 하고픔
    private static class UserValidator {

        private static final int EMAIL_MAX_LEN = 38;
        private static final int EMAIL_MIN_LEN = 8;
        private static final int NAME_MAX_LEN = 18;
        private static final int NAME_MIN_LEN = 1;

        private static final Set<String> ALLOW_DOMAINS = Set.of(
            "naver.com",
            "gmail.com",
            "kakao.com"
        );


        private static void verifyName(String name) {

            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("이름은 비어있을 수 없습니다.");
            }

            if (!numberBetween(NAME_MIN_LEN, NAME_MAX_LEN, name.length())) {
                throw new IllegalArgumentException("이름이_유효하지_않습니다.");
            }
        }

        private static void verifyEmail(String email) {
            if (email == null || email.isBlank()) {
                throw new IllegalArgumentException("이메일은 비어있을 수 없습니다");
            }
            if (!numberBetween(EMAIL_MIN_LEN, EMAIL_MAX_LEN, email.length())) {
                throw new IllegalArgumentException("이메일이 제한 길이를 벗어났습니다.");
            }
            if (!email.contains("@")) {
                throw new IllegalArgumentException("알 수 없는 도메인");
            }
            try {
                String domain = email.split("@")[1];
                if (!ALLOW_DOMAINS.contains(domain)) {
                    throw new IllegalArgumentException("알 수 없는 도메인");
                }
            } catch (ArrayIndexOutOfBoundsException e) {

                throw new IllegalArgumentException("알 수 없는 도메인");

            }
        }


        private static boolean numberBetween(int min, int max, int num) {
            return (min <= num && max >= num);
        }
    }

    public String getRoleKey(){
        return this.role.getKey();
    }

}
