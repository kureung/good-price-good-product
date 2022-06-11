package kr.co.gpgp.domain.delivery.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import kr.co.gpgp.domain.address.entity.Address;
import kr.co.gpgp.domain.delivery.entity.Delivery;
import kr.co.gpgp.domain.requirement.entity.Requirement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryRequest {

    @Size(max = 18, min = 0, message = "요구사항 다시 확인해주세요.")
    private String requirement;
    @Size(max = 40, min = 9, message = "도로명 다시 확인해주세요.")
    @NotNull
    private String roadName;
    @Size(max = 7, min = 5, message = "우편번호 다시 확인해주세요.")
    @NotNull
    private String zipCode;
    @Size(max = 20, min = 1, message = "주소이름 다시 확인해주세요.")
    @NotNull
    private String addressName;
    @Size(max = 17, min = 0, message = "자세한주소를 다시 확인해주세요.")
    private String detailedAddress;

    private DeliveryRequest(String requirement, String roadName, String zipCode, String addressName, String detailedAddress) {
        this.requirement = requirement;
        this.roadName = roadName;
        this.zipCode = zipCode;
        this.addressName = addressName;
        this.detailedAddress = detailedAddress;
    }

    private static DeliveryRequest of(String requirement, String roadName, String zipCode, String addressName, String detailedAddress) {
        return new DeliveryRequest(requirement, roadName, zipCode, addressName, detailedAddress);
    }

    public Delivery toEntity(Requirement requirement, Address address) {
        return Delivery.of(requirement, address);
    }

}
