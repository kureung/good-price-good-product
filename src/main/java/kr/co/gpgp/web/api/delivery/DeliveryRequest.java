package kr.co.gpgp.web.api.delivery;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import kr.co.gpgp.domain.address.Address;
import kr.co.gpgp.domain.address.dto.AddressRequest;
import kr.co.gpgp.domain.delivery.Delivery;
import kr.co.gpgp.domain.requirement.Requirement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryRequest {

    @NotNull(message = "배송 ID 값은 필수 입니다.")
    private Long id;

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


    private DeliveryRequest(Long id, String requirement, String roadName, String zipCode, String addressName, String detailedAddress) {
        this.id = id;
        this.requirement = requirement;
        this.roadName = roadName;
        this.zipCode = zipCode;
        this.addressName = addressName;
        this.detailedAddress = detailedAddress;
    }

    public static DeliveryRequest of(Long id, String requirement, String roadName, String zipCode, String addressName, String detailedAddress) {
        return new DeliveryRequest(id, requirement, roadName, zipCode, addressName, detailedAddress);
    }

    public Delivery toEntity() {
        return Delivery.of(new Requirement(requirement),
                Address.of(
                        AddressRequest.of(
                                getAddressName(),
                                getZipCode(),
                                getRoadName(),
                                getDetailedAddress()
                        )
                )
        );
    }

}
