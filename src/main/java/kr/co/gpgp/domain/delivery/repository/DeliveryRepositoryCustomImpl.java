package kr.co.gpgp.domain.delivery.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import javax.persistence.EntityManager;
import kr.co.gpgp.domain.address.entity.Address;
import kr.co.gpgp.domain.delivery.dto.DeliveryResponse;
import kr.co.gpgp.domain.delivery.entity.QDelivery;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeliveryRepositoryCustomImpl implements DeliveryRepositoryCustom {

    private final EntityManager entityManager;


    @Override
    public List<DeliveryResponse> findByUserId(Long userId) {
        JPAQuery<Address> query = new JPAQuery<>(entityManager);
        QDelivery qDelivery = new QDelivery("d");
        List<DeliveryResponse> list = query
                .select(Projections.constructor(
                        DeliveryResponse.class,
                        qDelivery.id,
                        qDelivery.requirement.message,
                        qDelivery.address.roadName,
                        qDelivery.address.zipCode,
                        qDelivery.address.name,
                        qDelivery.address.detailed))
                .from(qDelivery)
                .where(qDelivery.address.user.id.eq(userId))
                .fetch();

        return list;
    }

}
