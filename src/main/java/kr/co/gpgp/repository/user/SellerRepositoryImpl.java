package kr.co.gpgp.repository.user;

import java.util.Optional;
import kr.co.gpgp.domain.user.Seller;
import kr.co.gpgp.domain.user.SellerRepository;
import kr.co.gpgp.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SellerRepositoryImpl implements SellerRepository {

    private final SellerJpaRepository jpaRepository;

    @Override
    public Optional<Seller> findByUser(User user) {
        return jpaRepository.findByUser(user);
    }

}
