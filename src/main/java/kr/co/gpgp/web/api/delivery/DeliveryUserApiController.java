package kr.co.gpgp.web.api.delivery;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;
import kr.co.gpgp.domain.address.AddressDto;
import kr.co.gpgp.domain.delivery.Delivery;
import kr.co.gpgp.domain.delivery.DeliveryUserService;
import kr.co.gpgp.domain.requirement.Requirement.RequirementDto;
import kr.co.gpgp.domain.requirement.RequirementRequest;
import kr.co.gpgp.web.api.address.AddressRequest;
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
public class DeliveryUserApiController {

    private final DeliveryUserService deliveryUserService;


    @PostMapping("/{id}")
    public ResponseEntity<DeliveryResponse> create(
            @PathVariable Long id,
            @Valid @RequestBody AddressRequest addressRequest,
            @Valid @RequestBody RequirementRequest requirementRequest
    ) throws URISyntaxException {

        AddressDto addressDto = AddressDto.of(id, addressRequest.getId(), addressRequest.getRoadName(), addressRequest.getZipCode(), addressRequest.getName(), addressRequest.getDetailed());

        RequirementDto requirementdto = RequirementRequest.toRequirementDto(requirementRequest);

        Delivery delivery = deliveryUserService.save(id, addressDto, requirementdto);

        DeliveryResponse deliveryResponse = DeliveryResponse.of(delivery);

        return ResponseEntity.created(new URI("/api/delivery/user/" + id)).body(deliveryResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeliveryResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody AddressRequest addressRequest,
            @Valid @RequestBody RequirementRequest requirementRequest
    ) {

        AddressDto addressDto = AddressDto.of(id, addressRequest.getId(), addressRequest.getRoadName(), addressRequest.getZipCode(), addressRequest.getName(), addressRequest.getDetailed());


        RequirementDto requirementdto = RequirementRequest.toRequirementDto(requirementRequest);

        Delivery delivery = deliveryUserService.update(id, addressDto, requirementdto);

        DeliveryResponse deliveryResponse = DeliveryResponse.of(delivery);

        return ResponseEntity.ok(deliveryResponse);
    }

    @DeleteMapping("/{userId}/{deliveryId}")
    public ResponseEntity<DeliveryResponse> delete(
            @PathVariable Long userId,
            @PathVariable Long deliveryId
    ) {
        deliveryUserService.delete(userId, deliveryId);

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
