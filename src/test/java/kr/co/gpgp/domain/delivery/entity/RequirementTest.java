package kr.co.gpgp.domain.delivery.entity;

import static org.assertj.core.api.Assertions.*;

import kr.co.gpgp.domain.requirement.entity.Requirement;
import org.junit.jupiter.api.Test;

public class RequirementTest {

    @Test
    void 요청사항_메시지가_18자를_넘어_예외가_발생했습니다() {
        assertThatThrownBy(() -> new Requirement("Hellellpellpellpellpellpellpellpellpellpellpellpellpellpp"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("요청사항은 18자를 넘을수 없습니다.");
    }

}
