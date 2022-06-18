package kr.co.gpgp.web.api.order;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Getter
@NoArgsConstructor
public class OrderRequest {

    @NotNull
    @Positive
    private Long userId;

    @Size(max = 18, min = 0, message = "요구사항 다시 확인해주세요.")
    private String requirement;

    @Size(max = 40, min = 9, message = "도로명 다시 확인해주세요.")
    @NotNull
    private String roadName;

    @Size(max = 7, min = 5, message = "우편번호 다시 확인해주세요.")
    @NotNull
    private String zipCode;

    @Size(max = 20, min = 1, message = "주소이름 다시 확인해주세요.")
    @NotNull
    private String addressName;

    @Size(max = 17, min = 0, message = "자세한주소를 다시 확인해주세요.")
    private String detailedAddress;

    @Valid
    @Size(max = 1)
    @NotNull
    private List<OrderLineRequest> orderLines;

    @Builder
    private OrderRequest(
            Long userId,
            String requirement,
            String roadName,
            String zipCode,
            String addressName,
            String detailedAddress,
            List<OrderLineRequest> orderLines) {

        this.userId = userId;
        this.requirement = requirement;
        this.roadName = roadName;
        this.zipCode = zipCode;
        this.addressName = addressName;
        this.detailedAddress = detailedAddress;
        this.orderLines = orderLines;
    }

    @Getter
    @NoArgsConstructor
    public static class OrderLineRequest {

        @NotBlank
        @Length(max = 30)
        private String itemCode;

        @Range(min = 1, max = 10_000)
        private int orderQuantity;

        @Builder
        private OrderLineRequest(String itemCode, int orderQuantity) {
            this.itemCode = itemCode;
            this.orderQuantity = orderQuantity;
        }

    }

}
