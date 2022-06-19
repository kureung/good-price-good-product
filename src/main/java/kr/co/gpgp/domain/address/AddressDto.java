package kr.co.gpgp.domain.address;

import static lombok.AccessLevel.PRIVATE;

import kr.co.gpgp.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PRIVATE)
public class AddressDto {

    private Long id;
    private Long userId;
    private String roadName;
    private String zipCode;
    private String name;
    private String detailed;

    private AddressDto(Long userId, Long id, String roadName, String zipCode, String name, String detailed) {
        this.userId = userId;
        this.id = id;
        this.roadName = roadName;
        this.zipCode = zipCode;
        this.name = name;
        this.detailed = detailed;
    }

    public static AddressDto of(Long userId, Long id, String roadName, String zipCode, String name, String detailed) {
        return new AddressDto(userId, id, roadName, zipCode, name, detailed);
    }

    public Address toEntity(User user) {
        return Address.of(user, roadName, zipCode, name, detailed);
    }

}
