package kr.co.gpgp.domain.address.dto;


import com.querydsl.core.annotations.QueryProjection;
import kr.co.gpgp.domain.address.entity.Address;
import kr.co.gpgp.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddressResponse {

    private Long id;
    private String roadName;        //도로명
    private String zipCode;         //우편번호
    private String name;            //주소이름(닉네임)
    private String detailed;        //상세주소


    @QueryProjection
    public AddressResponse(Long id, String roadName, String zipCode, String name, String detailed) {
        this.id = id;
        this.roadName = roadName;
        this.zipCode = zipCode;
        this.name = name;
        this.detailed = detailed;
    }


    public static AddressResponse of(Address address) {
        return new AddressResponse(address.getId(),
                address.getRoadName(),
                address.getZipCode(),
                address.getName(),
                address.getDetailed());
    }

    public Address toEntity(User user) {
        return Address.of(user, roadName, zipCode, name, detailed);
    }
}
