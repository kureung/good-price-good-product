package kr.co.gpgp.web.api.order;

import com.nimbusds.jose.shaded.json.annotate.JsonIgnore;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OrderResponse {

    @JsonIgnore
    private Long id;

    private String requirement;

    private String roadName;

    private String zipCode;

    private String orderCode;
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
            String orderCode,
            List<OrderLineResponse> orderLines) {

        this.id = id;
        this.requirement = requirement;
        this.roadName = roadName;
        this.zipCode = zipCode;
        this.addressName = addressName;
        this.detailedAddress = detailedAddress;
        this.orderCode = orderCode;
        this.orderLines = orderLines;
    }

    @Getter
    public static class OrderLineResponse {

        private final String itemName;

        private final int itemPrice;

        private final int orderQuantity;

        @Builder
        private OrderLineResponse(String itemName, int itemPrice, int orderQuantity) {
            this.itemName = itemName;
            this.itemPrice = itemPrice;
            this.orderQuantity = orderQuantity;
        }

    }

}
