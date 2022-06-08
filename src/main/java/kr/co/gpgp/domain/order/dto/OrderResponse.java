package kr.co.gpgp.domain.order.dto;

import com.nimbusds.jose.shaded.json.annotate.JsonIgnore;
import java.util.List;
import kr.co.gpgp.domain.orderline.dto.OrderLineResponse;
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
}
