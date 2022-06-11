package kr.co.gpgp.domain.user;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import kr.co.gpgp.domain.user.Seller;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class SellerTest {

    @ParameterizedTest
    @ValueSource(ints = {-1, Integer.MIN_VALUE})
    void 총금액이_음수일떄_예외가_발생한다(int arg) {
        assertThatThrownBy(() -> {
            Seller.builder()
                    .totalPrice(arg)
                    .field(now())
                    .build();
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("총 금액은 음수가 나올수 없습니다.");
    }

    @Test
    void 정산날짜가_NULL_이_나올때_예외가_발생한다() {
        assertThatThrownBy(() -> {
            Seller.builder()
                    .totalPrice(123)
                    .field(null)
                    .build();
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("정산 금액은 null 을 받을수 없습니다.");
    }

}
