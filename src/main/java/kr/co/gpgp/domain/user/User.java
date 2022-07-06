package kr.co.gpgp.domain.user;

import static lombok.AccessLevel.PROTECTED;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;
    private String name;
    private String email;
    private String profileImage;

    private User(String name, String email, Role role, String profileImage) {

        UserValidator.verifyName(name);
        UserValidator.verifyEmail(email);

        this.name = name;
        this.email = email;
        this.role = role;
        this.profileImage = profileImage;
    }

    private User(String name, String email, Role role) {

        UserValidator.verifyName(name);
        UserValidator.verifyEmail(email);

        this.name = name;
        this.email = email;
        this.role = role;
    }

    public static User of(String name, String email, Role role) {
        return new User(name, email, role);
    }

    public static User of(String name, String email) {
        return new User(name, email, Role.USER);
    }

    public static User of(String name, String email, Role role, String profileImage) {
        return new User(name, email, role, profileImage);
    }

    public User updateEmail(String email) {
        UserValidator.verifyEmail(email);
        this.email = email;

        return this;
    }

    public User updateRole(Role role) {
        this.role = role;
        return this;
    }

    public User updateName(String name) {
        UserValidator.verifyName(name);
        this.name = name;

        return this;
    }

    public boolean isUserRole() {
        if (role==Role.USER) return true;
        return false;
    }

    @Transient
    private static final int EMAIL_MAX_LEN = 38;
    @Transient
    private static final int EMAIL_MIN_LEN = 8;
    @Transient
    private static final int NAME_MAX_LEN = 18;
    @Transient
    private static final int NAME_MIN_LEN = 1;
    @Transient
    private static final Set<String> ALLOW_DOMAINS = Set.of(
            "naver.com",
            "gmail.com",
            "kakao.com"
    );

    //@Builder override 재정의 하고픔
    private static class UserValidator {

        private static void verifyName(String name) {

            if (name==null || name.isBlank()) {
                throw new IllegalArgumentException("이름은 비어있을 수 없습니다.");
            }

            if (!numberBetween(NAME_MIN_LEN, NAME_MAX_LEN, name.length())) {
                throw new IllegalArgumentException("이름이_유효하지_않습니다.");
            }
        }

        private static void verifyEmail(String email) {
            if (email==null || email.isBlank()) {
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

    public String getRoleKey() {
        return this.role.getKey();
    }

}
