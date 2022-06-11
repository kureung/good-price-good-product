package kr.co.gpgp.domain.delivery.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.util.Optional;
import kr.co.gpgp.domain.address.Address;
import kr.co.gpgp.domain.delivery.Delivery;
import kr.co.gpgp.domain.requirement.entity.Requirement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryResponse {

    private Long id;
    private String requirement;
    private String roadName;
    private String zipCode;
    private String addressName;
    private String detailedAddress;

    @QueryProjection
    public DeliveryResponse(Long id, String requirement, String roadName, String zipCode, String addressName, String detailedAddress) {
        this.id = id;
        this.requirement = requirement;
        this.roadName = roadName;
        this.zipCode = zipCode;
        this.addressName = addressName;
        this.detailedAddress = detailedAddress;
    }

    public static DeliveryResponse of(Long id, String requirement, String roadName, String zipCode, String addressName, String detailedAddress) {
        return new DeliveryResponse(id, requirement, roadName, zipCode, addressName, detailedAddress);
    }

    public static DeliveryResponse of(Optional<Delivery> optional) {
        return new DeliveryResponse(optional.get().getId(), optional.get().getRequirementMessage(), optional.get().getAddressRoadName(), optional.get().getAddressZipCode(), optional.get().getAddressName(), optional.get().getAddressDetailed());
    }

    public Delivery toEntity(Requirement requirement, Address address) {
        return Delivery.of(requirement, address);
    }

}
