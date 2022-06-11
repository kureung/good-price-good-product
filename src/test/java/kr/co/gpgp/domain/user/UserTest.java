package kr.co.gpgp.domain.user;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import kr.co.gpgp.domain.user.Role;
import kr.co.gpgp.domain.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

public class UserTest {

    private final static String SUCCESS_NAME = "kang";
    private final static String SUCCESS_EMAIL = "kang22522252@gmail.com";

    @Test
    void 회원_이름이_유효하지_않아_예외가_발생했다() {
        assertThatThrownBy(() -> {
            User.of("123412561435_123412561435123412561435123412561435123412561435", SUCCESS_EMAIL, Role.USER);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름이_유효하지_않습니다.");
    }

    @NullAndEmptySource
    @ParameterizedTest
    @ValueSource(strings = {" "})
    void 회원_이름이_NULL_이_들어가_예외가_발생했습니다(String arg) {
        assertThatThrownBy(() -> {
            User.of(arg, SUCCESS_EMAIL, Role.USER);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 비어있을 수 없습니다.");
    }

    @NullAndEmptySource
    @ParameterizedTest
    @ValueSource(strings = {" "})
    void 이메일이_비어있어_예외가_발생했습니다(String arg) {
        assertThatThrownBy(() -> {
            User.of(SUCCESS_NAME, arg, Role.USER);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일은 비어있을 수 없습니다");
    }

    @ParameterizedTest
    @ValueSource(strings = {"kgh2252naver.com", "kgh2252@nas.com", "kgh2252@@@@@", "123@123.com"})
    void 이메일_도메인이_알수없어_예외가_발생했습니다(String arg) {
        assertThatThrownBy(() -> {
            User.of(SUCCESS_NAME, arg, Role.USER);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("알 수 없는 도메인");
    }

    @ParameterizedTest
    @ValueSource(strings = {"123_123_123_123_123_123_123_123_123_123@naver.com", "123_123_123_123_123_123_123_123_123_123@na"})
    void 이메일_길이를_벗어나_예외가_발생했습니다(String arg) {
        assertThatThrownBy(() -> {
            User.of(SUCCESS_NAME, arg, Role.USER);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일이 제한 길이를 벗어났습니다.");
    }

}

