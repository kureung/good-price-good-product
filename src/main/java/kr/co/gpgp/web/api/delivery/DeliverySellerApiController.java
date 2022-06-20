package kr.co.gpgp.web.api.delivery;

import java.util.List;
import javax.validation.Valid;
import kr.co.gpgp.domain.delivery.Delivery;
import kr.co.gpgp.domain.delivery.DeliverySellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/delivery/seller")
public class DeliverySellerApiController {

    private final DeliverySellerService deliverySellerService;

    //판매한 목록중 선택된 배송 상태를 모두 조회 할수있다.
    @GetMapping("/{status}/{userId}")
    public ResponseEntity<List<DeliveryResponse>> choiceStatus(
            @PathVariable String status,
            @PathVariable Long userId
    ) {
        List<Delivery> delivery = deliverySellerService.choiceStatus(status, userId);
        List<DeliveryResponse> deliveryResponses = DeliveryResponse.convertList(delivery);

        return ResponseEntity.ok(deliveryResponses);
    }

    //판매한 목록중
    @GetMapping("/{userId}")
    public ResponseEntity<List<DeliveryResponse>> allStatus(
            @PathVariable Long userId
    ) {
        List<Delivery> delivery = deliverySellerService.allStatus( userId);
        List<DeliveryResponse> deliveryResponses = DeliveryResponse.convertList(delivery);

        return ResponseEntity.ok(deliveryResponses);
    }


    //회원에게 판매한 상품의 배송 상태를 변경할수있다.
    @PutMapping("/{status}")
    public ResponseEntity<Void> targetUpdate(
            @PathVariable String status,
            @Valid @RequestBody DeliveryRequest deliveryRequest
    ) {
        deliverySellerService.targetUpdate(status,deliveryRequest);

        return ResponseEntity.ok().build();
    }

}
