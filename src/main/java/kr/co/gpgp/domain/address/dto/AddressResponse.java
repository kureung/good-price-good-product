package kr.co.gpgp.domain.address.dto;


import kr.co.gpgp.domain.address.entity.Address;
import kr.co.gpgp.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddressResponse {

    private String roadName;        //도로명
    private String zipCode;         //우편번호
    private String name;            //주소이름(닉네임)
    private String detailed;        //상세주소


    private AddressResponse(String roadName, String zipCode, String name, String detailed) {
        this.roadName = roadName;
        this.zipCode = zipCode;
        this.name = name;
        this.detailed = detailed;
    }

    public static AddressResponse of(AddressRequest address) {
        return new AddressResponse(address.getRoadName(),
                address.getZipCode(),
                address.getName(),
                address.getDetailed());
    }

    public Address toEntity(User user) {

        return Address.of(user, roadName, zipCode, name, detailed);
    }

}
