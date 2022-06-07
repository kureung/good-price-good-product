package kr.co.gpgp.domain.address.repository;

import java.util.List;
import kr.co.gpgp.domain.address.dto.AddressResponse;
import org.springframework.data.jpa.repository.Query;

public interface AddressRepositoryCustom {

    List<AddressResponse> findByUserId(Long userId);

}
