package kr.co.gpgp.domain.address.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import kr.co.gpgp.domain.address.Address;
import kr.co.gpgp.domain.address.Address.AddressDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddressRequest {

    @NotNull(message = "ID 값을 다시 확인해주세요.")
    private Long id;
    @NotNull(message = "도로명을 다시 확인해주세요.")
    @Size(max = 40, min = 9)
    private String roadName;

    @NotNull(message = "우편번호을 다시 확인해주세요.")
    @Size(max = 7, min = 5)
    private String zipCode;

    @NotNull(message = "주소이름을 다시 확인해주세요.")
    @Size(max = 20, min = 1)
    private String name;

    @Size(max = 17, message = "상세주소을 다시 확인해주세요.")
    private String detailed;

    private AddressRequest(Long id,String roadName, String zipCode, String name, String detailed) {
        this.id=id;
        this.roadName = roadName;
        this.zipCode = zipCode;
        this.name = name;
        this.detailed = detailed;
    }

    public static AddressRequest of(Long id,String roadName, String zipCode, String name, String detailed) {
        return new AddressRequest(id,roadName, zipCode, name, detailed);
    }

    public static AddressRequest of(Address address) {
        return new AddressRequest(address.getId(), address.getRoadName(), address.getZipCode(), address.getName(), address.getDetailed());
    }


    public static AddressDto toAddressDto(AddressRequest addressRequest) {
        return AddressDto.of(addressRequest.getRoadName(),addressRequest.getZipCode(),addressRequest.getName(),addressRequest.getDetailed());
    }


}
