package kr.co.gpgp.repository.address;

import java.util.List;
import kr.co.gpgp.domain.address.dto.AddressResponse;

public interface AddressRepositoryCustom {

    List<AddressResponse> findByUserId(Long userId);

}