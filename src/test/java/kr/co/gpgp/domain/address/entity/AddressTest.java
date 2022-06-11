package kr.co.gpgp.domain.address.entity;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import kr.co.gpgp.domain.user.entity.Role;
import kr.co.gpgp.domain.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

public class AddressTest {

    private static String SUCCESS_ROADNAME = "성남시 경기도 상대원 2동 1234번지 5층 ";
    private static String SUCCESS_ZIPCODE = "12345";
    private static String SUCCESS_NAME = "우리집";
    private static String SUCCESS_DETAILED = "1층";
    private static User SUCCESS_USER = User.of("kang", "kgh22522252@gmail.com", Role.USER);

    @ParameterizedTest
    @ValueSource(strings = {"123456", "123-12", "12-123", "abc-abc", "abcde", "ㄱㄴㄷㅇㅈ"})
    void 우편번호_구_또는_신규_패턴_불일치로_예외가_발생한다(String arg) {
        assertThatThrownBy(() ->
                Address.of(SUCCESS_USER,
                        SUCCESS_ROADNAME,
                        arg,
                        SUCCESS_NAME,
                        SUCCESS_DETAILED)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("우편번호 형식이 맞지 않습니다.");

    }

    @NullAndEmptySource
    @ParameterizedTest
    @ValueSource(strings = {" "})
    void 우편번호_NULL_또는_공백_이라면_에러가_발생한다(String arg) {
        assertThatThrownBy(() ->
                Address.of(SUCCESS_USER,
                        SUCCESS_ROADNAME,
                        arg,
                        SUCCESS_NAME,
                        SUCCESS_DETAILED)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("우편번호는 비어있을 수 없습니다.");
    }

    @NullAndEmptySource
    @ParameterizedTest
    @ValueSource(strings = {" "})
    void 도로명_내용이_비어있으면_예외가_발생한다(String arg) {
        assertThatThrownBy(() ->
                Address.of(SUCCESS_USER,
                        arg,
                        SUCCESS_ZIPCODE,
                        SUCCESS_NAME,
                        SUCCESS_DETAILED)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("도로명은 비어있을 수 없습니다.");
    }

    @Test
    void 도로명_내용길이가_범위에_벗어나_예외가_발생한다() {
        String roadname = "경기도_성남시_성남시_성남시_성남시_성남시_성남시_성남시_성남시_성남시_성남시_성남시_성남시_성남시_성남시 000-00 000동 000호";    //도로명 12-2

        assertThatThrownBy(() ->
                Address.of(SUCCESS_USER,
                        roadname,
                        SUCCESS_ZIPCODE,
                        SUCCESS_NAME,
                        SUCCESS_DETAILED)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("도로명 길이가 맞지 않습니다.");
    }

    @NullAndEmptySource
    @ParameterizedTest
    @ValueSource(strings = {" "})
    void 주소이름은_비어_있어_예외가_발생했습니다(String arg) {
        assertThatThrownBy(() ->
                Address.of(SUCCESS_USER,
                        SUCCESS_ROADNAME,
                        SUCCESS_ZIPCODE,
                        arg,
                        SUCCESS_DETAILED)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주소 이름은 비어있을 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"1234512345_1234512345_1234", "qwer_qwer_qwer_qwer_qwer_qwer"})
    void 주소이름_길이가_범위에_벗어나_예외가_발생했습니다(String arg) {
        assertThatThrownBy(() ->
                Address.of(SUCCESS_USER,
                        SUCCESS_ROADNAME,
                        SUCCESS_ZIPCODE,
                        arg,
                        SUCCESS_DETAILED)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주소 이름 길이가 맞지 않습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"1234512345_1234512345_1234512345_1234512345_1234512345_ "})
    void 자세한설명이_범위를_벗아날떄_예외가_발생합니다(String arg) {
        assertThatThrownBy(() ->
                Address.of(SUCCESS_USER,
                        SUCCESS_ROADNAME,
                        SUCCESS_ZIPCODE,
                        SUCCESS_NAME,
                        arg)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주소에_자세한_설명은17자를 넘을수 업습니다.");
    }

}
