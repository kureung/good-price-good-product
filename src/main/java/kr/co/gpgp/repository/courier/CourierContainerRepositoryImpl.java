package kr.co.gpgp.repository.courier;

import java.util.List;
import kr.co.gpgp.domain.courier.Courier;
import kr.co.gpgp.domain.courier.CourierContainer;
import kr.co.gpgp.domain.courier.CourierContainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CourierContainerRepositoryImpl implements CourierContainerRepository {

    private final CourierContainerJpaRepository jpaRepository;
    @Override
    public void save(CourierContainer courierContainer) {
        jpaRepository.save(courierContainer);
    }

    @Override
    public List<CourierContainer> findByCourier(Courier courier) {
        return jpaRepository.findByCourier(courier);
    }


}
