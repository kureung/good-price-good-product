package kr.co.gpgp.web.api.delivery;

import java.util.List;
import kr.co.gpgp.domain.address.Address;
import kr.co.gpgp.domain.address.AddressService;
import kr.co.gpgp.domain.delivery.Delivery;
import kr.co.gpgp.domain.delivery.DeliveryUserService;
import kr.co.gpgp.domain.requirement.Requirement;
import kr.co.gpgp.domain.requirement.RequirementService;
import kr.co.gpgp.web.api.address.AddressResponse;
import kr.co.gpgp.web.api.requirement.RequirementResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryDtoFacade {

    private final DeliveryUserService deliveryUserService;
    private final AddressService addressService;
    private final RequirementService requirementService;

    public List<DeliveryResponse> convertResponse(List<Delivery> delivery) {

        List<Long> addressIdList = deliveryUserService.toAddressIdList(delivery);
        List<Address> addresses = addressService.selectSectionId(addressIdList);
        List<AddressResponse> addressResponses = AddressResponse.of(addresses);

        List<Long> requirementIdList = deliveryUserService.toRequirementIdList(delivery);
        List<Requirement> requirements = requirementService.selectSectionId(requirementIdList);
        List<RequirementResponse> requirementResponses = RequirementResponse.of(requirements);

        return DeliveryResponse.convertList(delivery, addressResponses, requirementResponses);
    }

}
