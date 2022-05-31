package kr.co.gpgp.domain.user.entity;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

public class UserTest {

    private final static String SUCCESS_NAME = "kang";
    private final static String SUCCESS_EMAIL = "kang22522252@gmail.com";
    private final static String SUCCESS_PW = "passwrod";

    private User user;

    @Test
    void 회원_이름이_유효하지_않아_예외가_발생했다(){
        assertThatThrownBy(() -> {
            User.builder()
                .name("abcde_abcde_abcde_a")
                .email(SUCCESS_EMAIL)
//                .pw(SUCCESS_PW)
                .build();
        }).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이름이_유효하지_않습니다.");
    }

    @NullAndEmptySource
    @ParameterizedTest
    @ValueSource(strings = {" "})
    void 회원_이름이_NULL_이_들어가_예외가_발생했습니다(String arg){
        assertThatThrownBy(() -> {
            User.builder()
                .name(arg)
                .email(SUCCESS_EMAIL)
//                .pw(SUCCESS_PW)
                .build();
        }).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이름은 비어있을 수 없습니다.");
    }

    @NullAndEmptySource
    @ParameterizedTest
    @ValueSource(strings = {" "})
    void 이메일이_비어있어_예외가_발생했습니다(String arg){
        assertThatThrownBy(() -> {
            User.builder()
                .name(SUCCESS_NAME)
                .email(arg)
             //   .pw(SUCCESS_PW)
                .build();
        }).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이메일은 비어있을 수 없습니다");
    }

    @ParameterizedTest
    @ValueSource(strings = {"kgh2252naver.com","kgh2252@nas.com","kgh2252@@@@@","123@123.com"})
    void 이메일_도메인이_알수없어_예외가_발생했습니다(String arg){
        assertThatThrownBy(() -> {
            User.builder()
                .name(SUCCESS_NAME)
                .email(arg)
                //.pw(SUCCESS_PW)
                .build();
        }).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("알 수 없는 도메인");
    }
    @ParameterizedTest
    @ValueSource(strings = {"123_123_123_123_123_123_123_123_123_123@naver.com","123_123_123_123_123_123_123_123_123_123@na"})
    void 이메일_길이를_벗어나_예외가_발생했습니다(String arg){
        assertThatThrownBy(() -> {
            User.builder()
                .name(SUCCESS_NAME)
                .email(arg)
                //.pw(SUCCESS_PW)
                .build();
        }).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이메일이 제한 길이를 벗어났습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345","12345_12345_12345_12345_12345_12345_12345_"})
    void 패스워드가_유효하지_않아_예외가_발생했습니다(String arg){
        assertThatThrownBy(() -> {
            User.builder()
                .name(SUCCESS_NAME)
                .email(SUCCESS_EMAIL)
                //.pw(arg)
                .build();
        }).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("비번의 길이가 맞지 않습니다.");
    }

    @NullAndEmptySource
    @ParameterizedTest
    @ValueSource(strings = {" "})
    void 패스워드가_비어있으면_예외가_발생합니다(String arg){
        assertThatThrownBy(() -> {
            User.builder()
                .name(SUCCESS_NAME)
                .email(SUCCESS_EMAIL)
                //.pw(arg)
                .build();
        }).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("비번은 비어있을 수 없습니다.");
    }
}
