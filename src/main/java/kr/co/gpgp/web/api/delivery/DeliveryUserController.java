package kr.co.gpgp.web.api.delivery;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;
import kr.co.gpgp.domain.address.Address;
import kr.co.gpgp.domain.address.dto.AddressRequest;
import kr.co.gpgp.domain.delivery.Delivery;
import kr.co.gpgp.domain.delivery.DeliveryUserService;
import kr.co.gpgp.domain.requirement.Requirement;
import kr.co.gpgp.domain.requirement.RequirementRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/delivery/user")
public class DeliveryUserController {

    private final DeliveryUserService deliveryUserService;

    @PostMapping("/{id}")
    public ResponseEntity<DeliveryResponse> create(
            @PathVariable Long id,
            @Valid @RequestBody AddressRequest addressRequest,
            @Valid @RequestBody RequirementRequest requirementRequest
    ) throws URISyntaxException {

        Delivery delivery = Delivery.of(Requirement.of(requirementRequest),
                Address.of(addressRequest));

        deliveryUserService.save(delivery);

        DeliveryResponse deliveryResponse = DeliveryResponse.of(delivery);

        return ResponseEntity.created(new URI("/api/delivery/user/" + id)).body(deliveryResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeliveryResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody DeliveryRequest deliveryRequest
    ) {

        Delivery delivery = deliveryUserService.update(
                Delivery.of(
                        Requirement.of(deliveryRequest.getRequirement()),
                        Address.of(AddressRequest.of(deliveryRequest.getAddressName(),
                                deliveryRequest.getZipCode(),
                                deliveryRequest.getRoadName(),
                                deliveryRequest.getDetailedAddress()))
                )
        );

        DeliveryResponse deliveryResponse = DeliveryResponse.of(delivery);

        return ResponseEntity.ok(deliveryResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeliveryResponse> delete(
            @PathVariable Long id,
            @Valid @RequestBody DeliveryRequest deliveryRequest
    ) {
        Delivery delivery = deliveryRequest.toEntity();

        deliveryUserService.delete(delivery);

        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}/all")
    public ResponseEntity<List<DeliveryResponse>> selectAll(
            @PathVariable Long id
    ) {

        List<Delivery> delivery = deliveryUserService.selectAll(id);

        List<DeliveryResponse> deliveryResponse = DeliveryResponse.convertList(delivery);

        return ResponseEntity.ok(deliveryResponse);
    }

    @GetMapping("/{id}/{deliveryId}")
    public ResponseEntity<DeliveryResponse> select(
            @PathVariable Long id,
            @PathVariable Long deliveryId
    ) {
        DeliveryResponse deliveryResponse =
                DeliveryResponse.of(deliveryUserService.select(id, deliveryId));

        return ResponseEntity.ok(deliveryResponse);
    }

}
