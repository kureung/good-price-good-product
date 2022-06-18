package kr.co.gpgp.web.api.order;

import kr.co.gpgp.domain.address.Address;
import kr.co.gpgp.domain.user.User;
import kr.co.gpgp.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddressAdapter {

    private final UserService userService;

    public Address toEntity(Long userId,
                            String roadName,
                            String zipCode,
                            String addressName,
                            String detailedAddress
    ) {
        User user = userService.findOne(userId);
        return Address.of(user, roadName, zipCode, addressName, detailedAddress);
    }

}
