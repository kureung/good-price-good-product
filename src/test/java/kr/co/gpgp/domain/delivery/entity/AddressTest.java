package kr.co.gpgp.domain.delivery.entity;


import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

public class AddressTest {


    //    private static String SUCCESS_USERPOSTALCODE_OLD = "123-456";
    private static String SUCCESS_USERPOSTALCODE_NEW = "12345";
    private static String SUCCESS_ROADNAME = "성남시 경기도 상대원 2동 1234번지 5층 ";
    private static String SUCCESS_NAME = "우리집";
    private static String SUCCESS_REQUIREMENT = "도착후 전화 주세요";
    private static String SUCCESS_DETAILED = "1층";


    @ParameterizedTest
    @ValueSource(strings = {"123456", "123-12", "12-123", "abc-abc", "abcde", "ㄱㄴㄷㅇㅈ"})
    void 우편번호_구_또는_신규_패턴_불일치로_예외가_발생한다(String arg) {
        assertThatThrownBy(() ->
            Address.builder()
                .zipCode(arg)
                .roadName(SUCCESS_ROADNAME)
                .requirement(SUCCESS_REQUIREMENT)
                .name(SUCCESS_NAME)
                .detailed(SUCCESS_DETAILED)
                .build()
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("우편번호 형식이 맞지 않습니다.");

    }
    @NullAndEmptySource
    @ParameterizedTest
    @ValueSource(strings = {" "})
    void 우편번호_NULL_또는_공백_이라면_에러가_발생한다(String arg) {
        assertThatThrownBy(() ->
            Address.builder()
                .zipCode(arg)
                .roadName(SUCCESS_ROADNAME)
                .requirement(SUCCESS_REQUIREMENT)
                .name(SUCCESS_NAME)
                .detailed(SUCCESS_DETAILED)
                .build()
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("우편번호는 비어있을 수 없습니다.");
    }
    @NullAndEmptySource
    @ParameterizedTest
    @ValueSource(strings = {" "})
    void 도로명_내용이_비어있으면_예외가_발생한다(String arg) {
        assertThatThrownBy(() ->
            Address.builder()
                .zipCode(SUCCESS_USERPOSTALCODE_NEW)
                .roadName(arg)
                .requirement(SUCCESS_REQUIREMENT)
                .name(SUCCESS_NAME)
                .detailed(SUCCESS_DETAILED)
                .build()
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("도로명은 비어있을 수 없습니다.");
    }



    @Test
    void 도로명_내용길이가_범위에_벗어나_예외가_발생한다() {
        String roadname = "경기도_성남시_성남시_성남시_성남시_성남시_성남시_성남시_성남시_성남시_성남시_성남시_성남시_성남시_성남시 000-00 000동 000호";    //도로명 12-2

        assertThatThrownBy(() ->
            Address.builder()
                .zipCode(SUCCESS_USERPOSTALCODE_NEW)
                .roadName(roadname)
                .requirement(SUCCESS_REQUIREMENT)
                .name(SUCCESS_NAME)
                .detailed(SUCCESS_DETAILED)
                .build()
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("도로명 길이가 맞지 않습니다.");
    }
    @NullAndEmptySource
    @ParameterizedTest
    @ValueSource(strings = {" "})
    void 주소이름은_비어_있어_예외가_발생했습니다(String arg) {
        assertThatThrownBy(() ->
            Address.builder()
                .zipCode(SUCCESS_USERPOSTALCODE_NEW)
                .roadName(SUCCESS_ROADNAME)
                .requirement(SUCCESS_REQUIREMENT)
                .name(arg)
                .detailed(SUCCESS_DETAILED)
                .build()
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("주소 이름은 비어있을 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"1234512345_1234512345_1234","qwer_qwer_qwer_qwer_qwer_qwer"})
    void 주소이름_길이가_범위에_벗어나_예외가_발생했습니다(String arg) {
        assertThatThrownBy(() ->
            Address.builder()
                .zipCode(SUCCESS_USERPOSTALCODE_NEW)
                .roadName(SUCCESS_ROADNAME)
                .requirement(SUCCESS_REQUIREMENT)
                .name(arg)
                .detailed(SUCCESS_DETAILED)
                .build()
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("주소 이름 길이가 맞지 않습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"1234512345_1234512345_123451 ","가나다라마바사_가나다라마바사_가나다라마바사"})
    void 요청사항_길이가_벗어나_예외가_발생합니다(String arg) {
        assertThatThrownBy(() ->
            Address.builder()
                .zipCode(SUCCESS_USERPOSTALCODE_NEW)
                .roadName(SUCCESS_ROADNAME)
                .requirement(arg)
                .name(SUCCESS_NAME)
                .detailed(SUCCESS_DETAILED)
                .build()
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("요청사항은 " + Address.AddressValidator.REQUIREMENT_MAX_LEN + "자를 넘을수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"1234512345_1234512345_1234512345_1234512345_1234512345_ "})
    void 자세한설명이_범위를_벗아날떄_예외가_발생합니다(String arg) {
        assertThatThrownBy(() ->
            Address.builder()
                .zipCode(SUCCESS_USERPOSTALCODE_NEW)
                .roadName(SUCCESS_ROADNAME)
                .requirement(SUCCESS_REQUIREMENT)
                .name(SUCCESS_NAME)
                .detailed(arg)
                .build()
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("주소에_자세한_설명은17자를 넘을수 업습니다.");
    }

}
