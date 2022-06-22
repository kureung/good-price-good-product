package kr.co.gpgp.web.api.address;

import com.querydsl.core.annotations.QueryProjection;
import java.util.List;
import java.util.stream.Collectors;
import kr.co.gpgp.domain.address.Address;
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

    public static List<AddressResponse> of(List<Address> list) {
        return list.stream()
                .map(AddressResponse::of)
                .collect(Collectors.toList());
    }


}
