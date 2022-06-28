package kr.co.gpgp.repository.user;

import kr.co.gpgp.domain.user.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SellerRepositoryImpl implements SellerRepository {

    private final SellerJpaRepository jpaRepository;

}
