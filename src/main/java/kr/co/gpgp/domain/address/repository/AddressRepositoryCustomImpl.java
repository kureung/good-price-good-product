package kr.co.gpgp.domain.address.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import javax.persistence.EntityManager;
import kr.co.gpgp.domain.address.dto.AddressResponse;
import kr.co.gpgp.domain.address.entity.Address;
import kr.co.gpgp.domain.address.entity.QAddress;
import org.springframework.beans.factory.annotation.Autowired;

public class AddressRepositoryCustomImpl implements AddressRepositoryCustom {

    @Autowired
    EntityManager entityManager;

    @Override
    public List<AddressResponse> findByUserId(Long userId) {
        JPAQuery<Address> query = new JPAQuery<>(entityManager);
        QAddress qAddress = new QAddress("a");
        List<AddressResponse> list = query
                .select(Projections.constructor(AddressResponse.class,
                        qAddress.id,
                        qAddress.roadName,
                        qAddress.zipCode,
                        qAddress.name,
                        qAddress.detailed))
                .from(qAddress)
                .where(qAddress.user.id.eq(userId))
                .fetch();
        return list;
    }

}
