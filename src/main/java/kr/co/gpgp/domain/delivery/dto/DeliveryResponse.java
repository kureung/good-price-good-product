package kr.co.gpgp.domain.delivery.dto;

import kr.co.gpgp.domain.address.entity.Address;
import kr.co.gpgp.domain.delivery.entity.Delivery;
import kr.co.gpgp.domain.requirement.entity.Requirement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryResponse {

    private String requirement;
    private String roadName;
    private String zipCode;
    private String addressName;
    private String detailedAddress;

    private DeliveryResponse(String requirement, String roadName, String zipCode, String addressName, String detailedAddress) {
        this.requirement = requirement;
        this.roadName = roadName;
        this.zipCode = zipCode;
        this.addressName = addressName;
        this.detailedAddress = detailedAddress;
    }

    private static DeliveryResponse of(String requirement, String roadName, String zipCode, String addressName, String detailedAddress) {
        return new DeliveryResponse(requirement, roadName, zipCode, addressName, detailedAddress);
    }


    public Delivery toEntity(Requirement requirement, Address address) {
        return Delivery.of(requirement, address);
    }

}
