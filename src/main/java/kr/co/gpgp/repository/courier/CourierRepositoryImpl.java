package kr.co.gpgp.repository.courier;

import java.util.List;
import kr.co.gpgp.domain.courier.Courier;
import kr.co.gpgp.domain.courier.CourierArea;
import kr.co.gpgp.domain.courier.CourierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CourierRepositoryImpl implements CourierRepository {

    private final CourierJpaRepository jpaRepository;

    @Override
    public void save(Courier courier) {
        jpaRepository.save(courier);
    }

    @Override
    public Courier findByUserId(Long id) {
        return jpaRepository.findByUserId(id);
    }

    @Override
    public List<Courier> findByCourierArea(CourierArea courierArea) {
        return jpaRepository.findByCourierArea(courierArea);
    }

}
