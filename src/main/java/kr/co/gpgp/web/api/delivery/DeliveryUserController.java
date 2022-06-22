package kr.co.gpgp.web.api.delivery;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import kr.co.gpgp.auth.dto.SessionUser;
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
@RequestMapping("/delivery/user")
public class DeliveryUserController {

    private final DeliveryUserService deliveryUserService;


    @GetMapping("/")
    public String get(
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession();
        SessionUser user = (SessionUser) session.getAttribute("user");

        List<Delivery> delivery = deliveryUserService.selectAll(user.getId());
        List<DeliveryResponse> deliveryResponse = DeliveryResponse.convertList(delivery);

        return "deliveryUser";
    }


    @PostMapping("/")
    public ResponseEntity<DeliveryResponse> create(
            HttpServletRequest request,
            @Valid @RequestBody AddressRequest addressRequest,
            @Valid @RequestBody RequirementRequest requirementRequest
    ) throws URISyntaxException {
        HttpSession session = request.getSession();
        SessionUser user = (SessionUser) session.getAttribute("user");

        AddressDto addressDto = AddressDto.of(user.getId(), addressRequest.getId(), addressRequest.getRoadName(), addressRequest.getZipCode(), addressRequest.getName(), addressRequest.getDetailed());
        RequirementDto requirementdto = RequirementRequest.toRequirementDto(requirementRequest);
        Delivery delivery = deliveryUserService.save(user.getId(), addressDto, requirementdto);
        DeliveryResponse deliveryResponse = DeliveryResponse.of(delivery);
        return ResponseEntity.created(new URI("/api/delivery/user/" + user.getId())).body(deliveryResponse);
    }

    @PutMapping("/")
    public ResponseEntity<DeliveryResponse> update(
            HttpServletRequest request,
            @Valid @RequestBody AddressRequest addressRequest,
            @Valid @RequestBody RequirementRequest requirementRequest
    ) {
        HttpSession session = request.getSession();
        SessionUser user = (SessionUser) session.getAttribute("user");

        AddressDto addressDto = AddressDto.of(user.getId(), addressRequest.getId(), addressRequest.getRoadName(), addressRequest.getZipCode(), addressRequest.getName(), addressRequest.getDetailed());
        RequirementDto requirementdto = RequirementRequest.toRequirementDto(requirementRequest);
        Delivery delivery = deliveryUserService.update(user.getId(), addressDto, requirementdto);

        DeliveryResponse deliveryResponse = DeliveryResponse.of(delivery);

        return ResponseEntity.ok(deliveryResponse);
    }

    @DeleteMapping("/{deliveryId}")
    public ResponseEntity<DeliveryResponse> delete(
            HttpServletRequest request,
            @PathVariable Long deliveryId
    ) {
        HttpSession session = request.getSession();
        SessionUser user = (SessionUser) session.getAttribute("user");

        deliveryUserService.delete(user.getId(), deliveryId);

        return ResponseEntity.noContent().build();
    }


    @GetMapping("/all")
    public ResponseEntity<List<DeliveryResponse>> selectAll(
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession();
        SessionUser user = (SessionUser) session.getAttribute("user");

        List<Delivery> delivery = deliveryUserService.selectAll(user.getId());
        List<DeliveryResponse> deliveryResponse = DeliveryResponse.convertList(delivery);

        return ResponseEntity.ok(deliveryResponse);
    }

    @GetMapping("/{deliveryId}")
    public ResponseEntity<DeliveryResponse> select(
            HttpServletRequest request,
            @PathVariable Long deliveryId
    ) {

        HttpSession session = request.getSession();
        SessionUser user = (SessionUser) session.getAttribute("user");

        DeliveryResponse deliveryResponse =
                DeliveryResponse.of(deliveryUserService.select(user.getId(), deliveryId));

        return ResponseEntity.ok(deliveryResponse);
    }
}
