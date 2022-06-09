package kr.co.gpgp.domain.order.dto;

import static lombok.AccessLevel.PRIVATE;

import com.nimbusds.jose.shaded.json.annotate.JsonIgnore;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@NoArgsConstructor
@Getter
public class OrderResponse {

    @JsonIgnore
    private Long id;

    private String requirement;

    private String roadName;

    private String zipCode;

    private String addressName;

    private String detailedAddress;

    private List<OrderLineResponse> orderLines;

    @Builder
    private OrderResponse(
            Long id,
            String requirement,
            String roadName,
            String zipCode,
            String addressName,
            String detailedAddress,
            List<OrderLineResponse> orderLines) {

        this.id = id;
        this.requirement = requirement;
        this.roadName = roadName;
        this.zipCode = zipCode;
        this.addressName = addressName;
        this.detailedAddress = detailedAddress;
        this.orderLines = orderLines;
    }

    @Getter
    @RequiredArgsConstructor(access = PRIVATE)
    @Builder
    public static class OrderLineResponse {

        @NotBlank
        @Length(max = 30)
        private final String itemName;

        @NotBlank
        @Length(max = 30)
        private final String itemCode;

        @Range(min = 0, max = 1_000_000_000)
        private final int itemPrice;

        @Range(min = 0, max = 10_000)
        private final int orderQuantity;

    }
}
